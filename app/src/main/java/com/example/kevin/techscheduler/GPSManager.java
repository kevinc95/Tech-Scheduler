package com.example.kevin.techscheduler;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Kevin on 6/28/2017.
 */

public class GPSManager implements LocationListener {

    MapsActivity mapsActivity;
    LocationManager locationManager;
    String LOCATIONPROVIDER=LocationManager.GPS_PROVIDER;
    public GPSManager(MapsActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
        locationManager=(LocationManager)mapsActivity.getSystemService(Context.LOCATION_SERVICE);
    }
    public void register(){
        if ((ActivityCompat.checkSelfPermission(mapsActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(mapsActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED)){
            locationManager.requestLocationUpdates(LOCATIONPROVIDER,5000,0,this);
            //System.out.println("register updategps");
            mapsActivity.updateGPSLocation(locationManager.getLastKnownLocation(LOCATIONPROVIDER));
        }
    }
    public void unregister(){
        if ((ActivityCompat.checkSelfPermission(mapsActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(mapsActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED)){
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mapsActivity.updateGPSLocation(location);
        //System.out.println("change updategps");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
