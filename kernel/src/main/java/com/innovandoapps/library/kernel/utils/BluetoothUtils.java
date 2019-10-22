package com.innovandoapps.library.kernel.utils;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import com.innovandoapps.library.kernel.R;
import com.innovandoapps.library.kernel.dialogs.DialogAlertSimple;
import com.innovandoapps.library.kernel.dialogs.listener.OnPositiveClickListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BluetoothUtils {

    public static boolean checkHaveBluetooth(Context context, FragmentManager fragmentManager){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            DialogAlertSimple dialogAlertSimple = new DialogAlertSimple(context.getString(R.string.alert_title_blue),
                    context.getString(R.string.alert_msj_blue));
            dialogAlertSimple.setOnPositiveClickListener(new OnPositiveClickListener() {
                @Override
                public void OnPositiveClick(DialogInterface dialog, String tag) {
                    dialog.dismiss();
                }
            });
            dialogAlertSimple.show(fragmentManager,"");
            return false;
        }
        return true;
    }

    @SuppressLint("MissingPermission")
    public static boolean checkEnableBluetooth(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.isEnabled();
    }

    @SuppressLint("MissingPermission")
    public static boolean turnOnBluetooth(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.enable();
    }

    @SuppressLint("MissingPermission")
    public static Map<String, String> getListBluetoothDevices(){
        Map<String, String> map_bluetooth_devices = new HashMap<String, String>();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                map_bluetooth_devices.put(bluetoothAdapter.getAddress(),bluetoothAdapter.getName());
            }
        }
        return map_bluetooth_devices;
    }
}
