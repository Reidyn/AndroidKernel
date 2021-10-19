package com.innovandoapps.library.kernel.activitys;

import android.location.Location;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.innovandoapps.library.kernel.interfaces.ILocation;

public abstract class BaseActivityWithGPS extends BaseActivity implements ILocation {

    protected LocationCallback callback;
    protected Location location;

    @Override
    public LocationCallback implementLocationCallBack() {
        callback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
               if(locationResult.getLastLocation() != null){
                   location = locationResult.getLastLocation();
               }
            }
        };

        return callback;
    }
}
