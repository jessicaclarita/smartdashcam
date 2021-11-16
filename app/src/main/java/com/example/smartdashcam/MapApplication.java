package com.example.smartdashcam;

import android.app.Application;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class MapApplication extends Application {
    // set up single meaning only one instance is allowed
    private static MapApplication singleton;

    // history/list of the  previous locations
    private List<Location>myLocations;

    public List<Location> getMyLocations() {
        return myLocations;
    }

    public void setMyLocations(List<Location> myLocations) {
        this.myLocations = myLocations;

    }

    public MapApplication getInstance() {
        return singleton;
    }
    // the properties for the singleton
    public void onCreate() {
        super.onCreate();
        singleton = this;
        myLocations = new ArrayList<>();

    }
}
