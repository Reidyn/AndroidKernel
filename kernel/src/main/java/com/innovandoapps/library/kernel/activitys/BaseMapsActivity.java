package com.innovandoapps.library.kernel.activitys;
/*
 * Copyright (C) 2018 InovandoApps
 * Desarrollador: Ikarus
 */
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
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
import com.innovandoapps.library.kernel.activitys.listener.OnGetMyLocationListener;
import com.innovandoapps.library.kernel.activitys.listener.OnMyLocationRefreshListener;

public abstract class BaseMapsActivity extends BaseActivity implements OnMapReadyCallback {

    private final int TIME_REFRESH = 10000;

    protected GoogleMap mapa;
    protected FusedLocationProviderClient locationClient;
    protected Location myLocation;

    private LocationCallback callback;
    private LocationRequest locationRequest = new LocationRequest().setInterval(TIME_REFRESH)
                                                                   .setFastestInterval((TIME_REFRESH/2))
                                                                   .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    public OnGetMyLocationListener onGetMyLocationListener;
    public OnMyLocationRefreshListener onMyLocationRefreshListener;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(getIdMaps());

        @SuppressLint("ResourceType") View zoomControls = mapFragment.getView().findViewById(0x1);
        if (zoomControls != null && zoomControls.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) zoomControls.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }

        mapFragment.getMapAsync(this);

        locationClient = LocationServices.getFusedLocationProviderClient(this);
        locationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
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

    public Marker marcarPosicion(Double latitud, Double longitud, String titulo,float markColor){
        LatLng posicion = new LatLng(latitud, longitud);
        Marker marker = mapa.addMarker(new MarkerOptions()
                .position(posicion)
                .title(titulo)
                .icon(BitmapDescriptorFactory.defaultMarker(markColor)));
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
                BaseMapsActivity.this.onMyLocationRefreshListener.OnMyLocationRefresh(myLocation);
            }
        };
        try {
            locationClient.requestLocationUpdates(locationRequest,callback, Looper.myLooper());
        }catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if(callback != null){
            locationClient.removeLocationUpdates(callback);
        }
        super.onDestroy();
    }
}
