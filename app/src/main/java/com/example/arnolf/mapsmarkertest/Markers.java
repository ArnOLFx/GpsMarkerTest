package com.example.arnolf.mapsmarkertest;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by ArnOLF on 2015-07-20.
 */
public class Markers implements Runnable {

    GoogleMap mMap;
    MapsActivity ma;
    Marker marker;
    HashMap<String, Long> timeStamps;
    HashMap<String, Marker> markerHashMap;
    ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor();

    public Markers(GoogleMap mMap, MapsActivity ma) {
        this.mMap = mMap;
        this.ma = ma;

        markerHashMap = new HashMap<>();
        timeStamps = new HashMap<>();

    }

    public void addMarker(Marker marker) {
        this.marker = marker;

        markerHashMap.put(marker.getId(), marker);
        timeStamps.put(marker.getId(), new Date().getTime());

        if (marker.getId() == null) {
            Log.d("MARKER", "MARKER == NULL");
        } else if (marker.getId() != null) {
            Log.d("MARKER", "MARKER != NULL");
        }

        if (markerHashMap.isEmpty()) {
            Log.d("HASHMAP", "MARKER HASHMAP EMPTY");
        } else if (!markerHashMap.isEmpty()) {
            Log.d("HASHMAP", "MARKER HASHMAP NOT EMPTY");
        }

        if (timeStamps.isEmpty()) {
            Log.d("HASHMAP", "TIME HASHMAP EMPTY");
        } else if (!timeStamps.isEmpty()) {
            Log.d("HASHMAP", "TIME HASHMAP NOT EMPTY");
        }
    }

    public void delMarker(String id) {

        if ((!timeStamps.isEmpty() && !markerHashMap.isEmpty()) && (timeStamps.get(id) != null)) {

            if ((((timeStamps.get(id) * 1000) * 60) + 1) >= ((new Date().getTime() * 1000) * 60)) {
                markerHashMap.get(id).remove();
                markerHashMap.remove(id);
                timeStamps.remove(id);
            } else {
                ma.tv2.setText("No marker to be deleted");
            }

        }
    }

    public void run() {

    }
}
