package com.example.kevin.techscheduler;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.example.kevin.techscheduler.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap googleMap;

    GPSManager gpsManager;
    int requestCode = 0;

    ArrayList<String[]> listOfHourlyForecasts;
    ArrayList<String[]> schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        listOfHourlyForecasts = (ArrayList<String[]>) intent.getSerializableExtra(MainActivity.ALL_HOURLY_FORECASTS);
        schedule = (ArrayList<String[]>) intent.getSerializableExtra(ScheduleMyDay.SCHEDULE);

        gpsManager = new GPSManager(this);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    protected void onStart() {
        super.onStart();
        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        gpsManager.register();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gpsManager.unregister();
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this tutorial, we add polylines and polygons to represent routes and areas on the map.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        Geocoder geoCoder = new Geocoder(this, Locale.US);
        Bitmap resizeIcon = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.clock1), 150, 150, false);

        for (int i = schedule.size() - 1; i >= 0; i--) {

            String location = schedule.get(i)[0];

            try {
                List<Address> addresses = geoCoder.getFromLocationName(location + ", Blacksburg VA", 1);
                if (addresses.size() > 0)
                {
                    Double lat = (double) (addresses.get(0).getLatitude());
                    Double lon = (double) (addresses.get(0).getLongitude());

                    final LatLng user = new LatLng(lat, lon);
        /*used marker for show the location */

                    Marker place = googleMap.addMarker(new MarkerOptions()
                            .position(user)
                            .title(location + ", Blacksburg VA")
                            .icon(BitmapDescriptorFactory
                                    .fromBitmap(resizeIcon)));
                    // Move the camera instantly to Blacksburg with a zoom of 15.
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 15));

                    // Zoom in, animating the camera.
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int j = 1; j < schedule.size(); j++) {

            String[] previousPlace = schedule.get(j - 1);
            String[] currentPlace = schedule.get(j);

            try {
                List<Address> previousAddress = geoCoder.getFromLocationName(previousPlace[0] + ", Blacksburg VA", 1);
                List<Address> currentAddress = geoCoder.getFromLocationName(currentPlace[0] + ", Blacksburg VA", 1);

                if (previousAddress.size() > 0 && currentAddress.size() > 0) {
                    Double prevLat = (double) previousAddress.get(0).getLatitude();
                    Double prevLon = (double) previousAddress.get(0).getLongitude();

                    Double currLat = (double) currentAddress.get(0).getLatitude();
                    Double currLon = (double) currentAddress.get(0).getLongitude();

                    final LatLng prevUser = new LatLng(prevLat, prevLon);
                    final LatLng currUser = new LatLng(currLat, currLon);

                    String endTimeHour = previousPlace[3];
                    String condition = null;

                    for (int k = 0; k < listOfHourlyForecasts.size(); k++) {
                        String[] forecast = listOfHourlyForecasts.get(k);
                        if (forecast[3].equals(endTimeHour)) {
                            condition = forecast[7];
                            break;
                        }
                    }

                    ConditionPathColorPicker conditionPathColorPicker = new ConditionPathColorPicker(condition);
                    GoogleMapsPath googleMapsPath = new GoogleMapsPath(
                            this, googleMap, prevUser, currUser, conditionPathColorPicker.getPathColor());
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public void updateGPSLocation(Location lastKnownLocation) {

        if (googleMap != null) {
            Double lat = lastKnownLocation.getLatitude();
            Double lng = lastKnownLocation.getLongitude();

            LatLng currentSpot = new LatLng(lat, lng);

            Bitmap spotIcon = BitmapFactory.decodeResource(getResources(), R.drawable.user);
            Bitmap resizedSpotIcon = Bitmap.createScaledBitmap(spotIcon, 300, 300, false);

            Marker spot = googleMap.addMarker(new MarkerOptions()
                    .position(currentSpot)
                    .title("You are here")
                    .icon(BitmapDescriptorFactory.fromBitmap(resizedSpotIcon)));

            // Move the camera instantly to hamburg with a zoom of 15.
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentSpot, 15));

            // Zoom in, animating the camera.
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
            gpsManager.unregister();
        }

    }
}
