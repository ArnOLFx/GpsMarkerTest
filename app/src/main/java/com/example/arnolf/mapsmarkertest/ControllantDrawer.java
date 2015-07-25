package com.example.arnolf.mapsmarkertest;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ArnOLF on 2015-07-23.
 */
public class ControllantDrawer {

    GoogleMap map;
    double lat, lon;
    HashMap<Double, Double> markerPos;

    public ControllantDrawer(GoogleMap map) {
        this.map = map;

        markerPos = new HashMap<>();
    }

    public void addMarker(GoogleMap map) {

        if (map != null) {
            Log.d("Map", "Map != null");

            this.map = map;

            if (map.getMyLocation() != null) {
                Log.d("MyLocation", "MyLoc != null");

                lat = map.getMyLocation().getLatitude();
                lon = map.getMyLocation().getLongitude();

                markerPos.put(lat, lon);
            } else {
                Log.d("MyLocation", "MyLoc == null");
            }
        } else {
            Log.d("Map", "Map == null");
        }
        drawMarker();
    }

    public void drawMarker() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                /**
                 * add new marker for every element in hashmap
                 */

                Iterator it = markerPos.entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();

                    Log.d("Entry's", "Key: " + pair.getKey() + " : " + "Value: " + pair.getValue());

                    double la = (double) pair.getKey();
                    double lo = (double) pair.getValue();

                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(la, lo))
                            .title(new Date().toString())
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.circle_green)));

                    it.remove();
                }
            }
        };
        r.run();
    }
}
