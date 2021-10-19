package com.innovandoapps.library.kernel.interfaces;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import androidx.annotation.RequiresApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public interface ILocation {

    LocationRequest mLocationRequest = LocationRequest.create();

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)
    default void inicializarLocation(Context context,long interval,long fastInterval,int priority){
        mLocationRequest.setInterval(interval);
        mLocationRequest.setFastestInterval(fastInterval);
        mLocationRequest.setPriority(priority);
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, implementLocationCallBack(), Looper.myLooper());
    }

    LocationCallback implementLocationCallBack();

    default void removeRequestLocation(Context context,LocationCallback locationCallback){
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mFusedLocationClient.removeLocationUpdates(locationCallback);
    }
}
