package com.example.smartdashcam;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class PathActivity extends AppCompatActivity {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    private String messageResId;
    public static final int DEFAULT_UPDATE_INTERVAL = 20;
    public static final int FAST_UPDATE_INTERVAL = 5;

    //reference to the UI elements below
    TextView tv_lat, tv_lon, tv_altitude, tv_accuracy, tv_speed, tv_updates, tv_address, mtv_wayPointCounts;

    Button mbtn_newWayPoint, mbtn_showWayPointList, mbtn_showGoogleMap;
    SwitchCompat mSwitch_locationupdates, mSwitch_gps;

    // variable to remember if we are tracking location or not.
    boolean updateOn = false;

    // current location
    Location currentLocation;

    // list of saved
    List<Location> savedLocations;


    /*Location request which is a class that has a lot of properties that would
    influence the way how the FusedLocationProviderClient works */
    //Declare a new instance of this class
    LocationRequest locationRequest;

    // Declaration of Location CallBack
    LocationCallback locationCallBack;


    //The whole Google's API for location services. Most of the apps functions uses this class.

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //give the UI variable a value

        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_altitude = findViewById(R.id.tv_altitude);
        tv_accuracy = findViewById(R.id.tv_accuracy);
        tv_speed = findViewById(R.id.tv_speed);
        tv_updates = findViewById(R.id.tv_updates);
        tv_address = findViewById(R.id.tv_address);
        mSwitch_gps = findViewById(R.id.sw_gps);
        mSwitch_locationupdates = findViewById(R.id.sw_locationsupdates);
        mbtn_newWayPoint = findViewById(R.id.mbtn_newWayPoint);
        mbtn_showWayPointList = findViewById(R.id.mbtn_showWayPointList);
        mtv_wayPointCounts = findViewById(R.id.mtv_numOfCrumbs);
        mbtn_showGoogleMap = findViewById(R.id.mbtn_showMap);


        // Initiation for  all the properties of the LocationRequest Function
        locationRequest = new LocationRequest();

        // How often does the default location normally updates
        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);

        // How often does the location check occur when set to the most frequent update
        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);

        /* Allow user to have the option to choose whether high accuracy but uses more power
        or more power efficient but lower accuracy (Tower or GPS)*/

        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        //Location CallBac. This event that will be triggered when the update interval is met. For our case is 5 seconds and 20seconds.
        locationCallBack = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                // new value that can save the location. Save and pass on to the UI
                updateUIValues(locationResult.getLastLocation());
            }
        };

        // onClickListener for the buttons
        mbtn_newWayPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the gps location

                // add the new location to the global list
                MapApplication mapApplication = (MapApplication) getApplicationContext();
                savedLocations = mapApplication.getMyLocations();
                savedLocations.add(currentLocation);
            }
        });

        // button listener for SavedList
        mbtn_showWayPointList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PathActivity.this, ShowSavedLocationList.class);
                startActivity(i);
            }
        });


        // button listener for Google Map
        mbtn_showGoogleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PathActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });


        //Check to see if GPS is checked, if so turn it on by set it priority high accuracy
        mSwitch_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mSwitch_gps.isChecked()) {
                    // most accurate - use GPS
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    // sensors tells which service is currently being used
                    messageResId = "GPS has been enabled";
                    Toast.makeText(PathActivity.this, messageResId, Toast.LENGTH_SHORT).show();

                }

                // Use Tower + Wifi instead
                else {
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    messageResId = "Tower Signal or Wifi has been enabled";
                    Toast.makeText(PathActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* add a new function , when the onClick is clicked, ask the question is this check switch checked?
         If so then turn it on, if not then turn it off*/
        mSwitch_locationupdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSwitch_locationupdates.isChecked()) {
                    // if its check then turn on the location tracking
                    // create helper functions
                    startLocationUpdates();
                } else {
                    stopLocationUpdates();
                }

            }
        });

        updateGPS();
    }           //end onCreate method

    private void startLocationUpdates() {
        tv_address.setText("Location is being tracked");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // we got permissions, put the values of location into the UI components
                    updateUIValues(location);
                    currentLocation = location;
                }

            }
        });
    }


    private void stopLocationUpdates() {
        tv_updates.setText("Location tracking has Stop");
        tv_lat.setText("Not tracking location");
        tv_lon.setText("Not tracking location");
        tv_speed.setText("Not tracking location");
        tv_address.setText("Not tracking location");
        tv_altitude.setText("Not tracking location");
        tv_accuracy.setText("Not tracking location");

        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
    }


    // will have a request code that has been assigned at the top
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case PERMISSIONS_FINE_LOCATION:
                /* check the case for permission, find location. If the request is from the permission then update the GPS*/
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS();
                } else {
                    Toast.makeText(this, "This app needs permission to be enabled to work properly!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }


    private void updateGPS() {
        // get permission from the user to track GPS
        // get the Current location from the fused client
        /* update the UI so that it can be displayed on the screen
            set all properties in their associated text view items*/

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(PathActivity.this);
        // here is the permission condition
        if (ActivityCompat.checkSelfPermission(PathActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // user agree to provide the permission
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        // we got permission. Put the values of location. XXX into the UI components.
                        updateUIValues(location);
                        currentLocation = location;
                    }

                }
            });
        } else {  // user does not agree to provide permission


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
    }

    private void updateUIValues(@NonNull Location location) {
        //update all of the text view objects with a new location
        // set Text does not automatically convert integer to string, therefore we need to parse it with 'String.valueOf'
        tv_lat.setText(String.valueOf(location.getLatitude()));
        tv_lon.setText(String.valueOf(location.getLongitude()));
        tv_accuracy.setText(String.valueOf(location.getAccuracy()));

        if (location.hasAltitude()) {

            tv_altitude.setText(String.valueOf(location.getAltitude()));

        } else {
            tv_altitude.setText("Unavailable");

        }

        if (location.hasSpeed()) {
            tv_speed.setText(String.valueOf(location.getSpeed()));

        } else {
            tv_speed.setText("Unavailable");
        }

        // provided by google services, but isn't guaranteed its gonna work so need to use try and catch
        Geocoder geocoder = new Geocoder(PathActivity.this);

        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            tv_address.setText(addresses.get(0).getAddressLine(0));
        }
        catch(Exception e) {
            tv_address.setText("Unable to obtain street address");
        }

        MapApplication mapApplication =  (MapApplication)getApplicationContext();
        savedLocations = mapApplication.getMyLocations();

        // show the number of items in the list of waypoints saved.
        if (savedLocations != null) {
            mtv_wayPointCounts.setText(Integer.toString(savedLocations.size()));
        }
    }
}
