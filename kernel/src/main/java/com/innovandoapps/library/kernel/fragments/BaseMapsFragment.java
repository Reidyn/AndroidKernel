package com.innovandoapps.library.kernel.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.innovandoapps.library.kernel.fragments.listener.OnGetMyLocationListener;
import com.innovandoapps.library.kernel.fragments.listener.OnMyLocationRefreshListener;

public abstract  class BaseMapsFragment extends BaseFragment implements OnMapReadyCallback{

    private final int TIME_REFRESH = 10000;

    ///Variables
    protected ProgressDialog barProgressDialog;
    protected Handler handler = new Handler();
    protected boolean activo;

    protected GoogleMap mapa;
    protected FusedLocationProviderClient locationClient;
    protected Location myLocation;

    private LocationCallback callback;
    private LocationRequest locationRequest = new LocationRequest().setInterval(TIME_REFRESH)
                                                                   .setFastestInterval((TIME_REFRESH/2))
                                                                   .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    public OnGetMyLocationListener onGetMyLocationListener;
    public OnMyLocationRefreshListener onMyLocationRefreshListener;

    /***
     * Metodo que muesta un dialog progres
     * @param titulo  String titulo del dialog
     * @param mensaje String texto del mensaje
     */
    protected void showProgressDialog(String titulo,String mensaje){
        if(barProgressDialog == null){
            barProgressDialog = new ProgressDialog(getContext());
        }
        barProgressDialog.setTitle(titulo);
        barProgressDialog.setMessage(mensaje);
        barProgressDialog.setIndeterminate(true);
        barProgressDialog.setCancelable(false);
        barProgressDialog.setCanceledOnTouchOutside(false);
        barProgressDialog.show();
    }

    public void inicializarMaps(){
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(getIdMaps());
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation(){
        locationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        locationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    myLocation = location;
                    if(onGetMyLocationListener != null){
                        onGetMyLocationListener.onGetMyLocation(location);
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
    }

    protected abstract int getIdMaps();

    /**
     * Metodo que remueve el dialogProgress si este se encuentra visible
     */
    protected void offProgressDialog(){
        if(barProgressDialog != null && barProgressDialog.isShowing()){
            barProgressDialog.dismiss();
        }
    }

    /**
     * Metodo que despliega un mensaje Toast
     * @param context Contexto del Avtivity
     * @param mensaje String texto del mensaje
     */
    protected void notificacionToast(Context context, String mensaje){
        Toast toast = Toast.makeText(context,mensaje,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isActivo() {
        return activo;
    }


    public Location getMyLocation(){
        return myLocation;
    }

    public void setOnGetMyLocationListener(OnGetMyLocationListener onGetMyLocationListener){
        this.onGetMyLocationListener = onGetMyLocationListener;
    }

    public Marker marcarPosicion(Double latitud, Double longitud, String titulo, int iconResource){
        LatLng posicion = new LatLng(latitud, longitud);
        Marker marker = mapa.addMarker(new MarkerOptions()
                .position(posicion)
                .title(titulo)
                .icon(BitmapDescriptorFactory.fromResource(iconResource)));
        return marker;

    }

    public Marker marcarPosicion(Double latitud, Double longitud, String titulo){
        LatLng posicion = new LatLng(latitud, longitud);
        Marker marker = mapa.addMarker(new MarkerOptions()
                .position(posicion)
                .title(titulo)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        return marker;

    }

    public void moverMapa(LatLng position,float zoom){
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(position, zoom);
        mapa.animateCamera(cameraUpdate);
    }

    @SuppressLint("MissingPermission")
    public void setOnMyLocationRefreshListener(OnMyLocationRefreshListener onMyLocationRefreshListener){
        this.onMyLocationRefreshListener = onMyLocationRefreshListener;
        callback =  new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                myLocation = (Location) locationResult.getLastLocation();
                BaseMapsFragment.this.onMyLocationRefreshListener.OnMyLocationRefresh(myLocation);
            }
        };
        try {
            locationClient.requestLocationUpdates(locationRequest,callback, Looper.myLooper());
        }catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        if(callback != null){
            locationClient.removeLocationUpdates(callback);
        }
        activo = false;
        super.onDestroyView();
    }
}
