package com.innovandoapps.library.kernel.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.innovandoapps.library.kernel.BuildConfig;
import java.util.List;

/**
 * Created by desarrollo on 23/11/17.
 */

public class CellInfoUtils {

    /**
     * Funcion Booleana del estado de Conexion del Dispositivo
     * @param context Contexto del Activity
     * @return TRUE =>Dispositivo Conectado ; False Desconectado
     */
    @SuppressLint("MissingPermission")
    public static boolean checkNetworkConnect(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        try {
            return activeNetwork.isConnectedOrConnecting();
        } catch (NullPointerException ex) {
            return false;
        }
    }

    /**
     * Funcion booleana del Estado del GPS
     * @param context Contexto del Activity
     * @return TRUE =>Acitivado ; False Desactivado
     */
    public static boolean checkLocationGPS(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Funcion Booleana Del estado de posicion por Network
     * @param context Contexto del Activity
     * @return TRUE =>Acitivado ; False Desactivado
     */
    public static boolean checkLocationNetwork(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * Funcion booleana del estado de servicio de posicion falsa
     * @param context Contexto del Activity
     * @return TRUE =>Acitivado ; False Desactivado
     */
    public static boolean checkMockLocation(Context context){
        boolean isMockLocation = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AppOpsManager opsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try{
                isMockLocation = (opsManager.checkOp(AppOpsManager.OPSTR_MOCK_LOCATION, android.os.Process.myUid(), BuildConfig.APPLICATION_ID) == AppOpsManager.MODE_ALLOWED);
            }catch (SecurityException ex){}
        } else {
            isMockLocation = !Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0");
        }
        return isMockLocation;
    }

    /**
     * funcion Booleana de estado de la opciones de desarrollador
     * @param context Contexto del Activity
     * @return Booleano si la opcion de desarrollador esta activado
     */
    public static boolean checkDeveloperOption(Context context) {
        int adb = Settings.Secure.getInt(context.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0);
        return adb == 0;
    }

    /**
     * Retorna el porcetaje de la bateria
     * @param context Contexto del Activity
     * @return float donde 1 = 100%
     */
    public static float getPorcBaterria(Context context) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float) scale;
        return batteryPct;
    }

    /**
     * Retorna un valor boleano sobre el estado de la funciones de conectividad
     * @param context Contexto de la actividad
     * @return boolean TRUE = hay conectividad; False = no hay conectividad
     */
    @SuppressLint("MissingPermission")
    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static boolean deviceHasSimcard(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getSimState() != TelephonyManager.SIM_STATE_ABSENT) {
            return true;
        }
        return false;
    }

    /**
     * Obtiene datos del SimCard Instalado
     * @param context
     * @return array String
     *         0 => Serial Sim
     *         1 => Codigo Del Pais
     *         3 => Nombre de la operadora
     */
    @SuppressLint("MissingPermission")
    public static String[] getDataOperadora(Context context) throws SecurityException{
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if ((mTelephonyMgr.getSimState() == TelephonyManager.SIM_STATE_ABSENT) || (mTelephonyMgr.getSimState() == TelephonyManager.SIM_STATE_UNKNOWN)) {
            return null;
        } else {
            return new String[]{mTelephonyMgr.getSimSerialNumber(),
                    mTelephonyMgr.getSimCountryIso(),
                    mTelephonyMgr.getSimOperator()};
        }
    }

    /**
     * Retorna el imei del Dispositivo
     * @param context Contexto del Activity
     * @return String del imei del dispositivo
     */
    @SuppressLint("MissingPermission")
    public static String getImeiCell(Context context) throws SecurityException{
        TelephonyManager mTelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getDeviceId();
    }

    /**
     * recupera el AndroidID del dispositivo
     * @param context Contexto de la actividad
     * @return String del Android ID
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * Checkea si la fecha del sistema se define por la zona automaticamente
     * @param context Contexto de la actividad
     * @return TRUE => si esta activada; False =>No esta actualizado
     */
    public static boolean checkAutomaticTime(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AUTO_TIME,0) == 1;
        }else{
            return Settings.System.getInt(context.getContentResolver(), Settings.System.AUTO_TIME, 0) == 1;
        }
    }

    public static boolean isActivityRunning(Context context,String packagename,String nameClass){
        boolean isActivityFound = false;
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(Integer.MAX_VALUE);
        for(int i = 0;i < tasks.size();i++){
            ComponentName cn = tasks.get(i).topActivity;
            if(cn.getClassName().equals(packagename + "." + nameClass)){
                isActivityFound=true;
                break;
            }
        }
        return isActivityFound;
    }
}