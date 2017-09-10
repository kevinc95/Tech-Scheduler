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
 * Class for managing the GPS.
 */
public class GPSManager implements LocationListener {

    MapsActivity mapsActivity;
    LocationManager locationManager;
    String LOCATIONPROVIDER=LocationManager.GPS_PROVIDER;
    
    /**
     * Create an instance of GPSManager.
     */
    public GPSManager(MapsActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
        locationManager=(LocationManager)mapsActivity.getSystemService(Context.LOCATION_SERVICE);
    }
    
    /**
     * Register the GPS and update the location every 5000 milliseconds
     */
    public void register(){
        if ((ActivityCompat.checkSelfPermission(mapsActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(mapsActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED)){
            locationManager.requestLocationUpdates(LOCATIONPROVIDER,5000,0,this);
            mapsActivity.updateGPSLocation(locationManager.getLastKnownLocation(LOCATIONPROVIDER));
        }
    }
    
    /**
     * Unregister the GPS
     */
    public void unregister(){
        if ((ActivityCompat.checkSelfPermission(mapsActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(mapsActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED)){
            locationManager.removeUpdates(this);
        }
    }

    /**
     * When location is changed, the mapsActivity will update location.
     */
    @Override
    public void onLocationChanged(Location location) {
        mapsActivity.updateGPSLocation(location);
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
