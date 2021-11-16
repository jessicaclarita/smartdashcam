package com.example.smartdashcam;

import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ShowSavedLocationList extends AppCompatActivity {


    ListView mlv_savedLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved_location_list);

        mlv_savedLocations = findViewById(R.id.mlv_wayPoints);

        MapApplication mapApplication = (MapApplication)getApplicationContext();
        List<Location> savedLocations = mapApplication.getMyLocations();

        mlv_savedLocations.setAdapter(new ArrayAdapter<Location>( this, android.R.layout.simple_list_item_1, savedLocations ));
    }
}