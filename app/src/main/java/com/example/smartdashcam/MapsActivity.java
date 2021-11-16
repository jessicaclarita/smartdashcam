package com.example.smartdashcam;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.example.smartdashcam.MapApplication;
import com.example.smartdashcam.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    List<Location> savedLocations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        MapApplication mapApplication = (MapApplication)getApplicationContext();
        savedLocations = mapApplication.getMyLocations();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng Penang = new LatLng( 5.4167 , 100.3167 );
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        LatLng lastLocationPlaced = Penang;


        for (Location location: savedLocations
        ) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            // new map marker
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Lat:" + location.getLatitude() + "Long:" + location.getLongitude());
            mMap.addMarker(markerOptions);
            lastLocationPlaced = latLng;
        }

        // Zoom In/Out
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLocationPlaced, 14.0f));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {

                // count the number of times the pins is clicked
                Integer clicks = (Integer) marker.getTag();
                if (clicks == null) {
                    clicks = 0;
                }
                clicks++;
                marker.setTag(clicks);
                Toast.makeText(MapsActivity.this,"Marker" + marker.getTitle() + " was clicked" + marker.getTag() + " times.", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }
}