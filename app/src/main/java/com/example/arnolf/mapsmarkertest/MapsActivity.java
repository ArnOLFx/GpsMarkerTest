package com.example.arnolf.mapsmarkertest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;

public class MapsActivity extends FragmentActivity implements LocationListener, GoogleMap.OnMarkerClickListener {

    /**
     * 11.899038399999995 57.6589749
     * 11.925536899999997 57.6660834
     * 11.869722799999977 57.6640556
     */

    /**
     * A marker should have a location and a timestamp.
     * When the marker is 5min old, it should turn yellow.
     * When the marker is 10min old, it should turn red.
     */

    private GoogleMap mMap;
    double lat, lon;

    TextView tv, tv2;
    Button button;

    Marker marker;
    Markers markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        tv = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        button = (Button) findViewById(R.id.setMarker);

        initMap();
    }

    public void onClick(View v) {

        Date d = new Date();

        if (!Double.isNaN(lat) && !Double.isNaN(lon)) {
            tv.setText(lat + " " + lon);

            marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lon))
                    .title(d.toString())
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.circle_green)));

            marker.showInfoWindow();

            if (marker != null) {
                markers.addMarker(marker);
                Log.d("MARKER", "MARKER != NULL");
            } else {
                Log.d("MARKER", "MARKER == NULL");
            }

            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 12.0f);
            mMap.animateCamera(update);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                initMap();
            }
        }
    }

    private void initMap() {

        SupportMapFragment mf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        String provider = lm.getBestProvider(new Criteria(), true);

        if (provider == null) {
            onProviderDisabled(provider);
        }

        LatLng POS = new LatLng(57.713685, 11.99295);

        lat = POS.latitude;
        lon = POS.longitude;

        mMap = mf.getMap();
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(POS, 10.0f));
/*        mMap.addMarker(new MarkerOptions()
                .position(POS)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.circle_green)));
*/
        if (mMap != null) {
            markers = new Markers(mMap, this);
            Log.d("MAP", "MAP != NULL");
        } else {
            Log.d("MAP", "MAP == NULL");
        }

    }

    @Override
    public void onLocationChanged(Location location) {

        if (location == null) {
            tv.setText("No location available!");
        } else {
                Log.d("LOCATION", "Location Not Null");
            if (Double.isNaN(mMap.getMyLocation().getLongitude())
                    && Double.isNaN(mMap.getMyLocation().getLongitude())) {
                Log.d("MyLocation", "MyLocation Is Null");
                lat = location.getLatitude();
                lon = location.getLongitude();
            } else {
                Log.d("MyLocation", "MyLocation Is Not Null");
                lat = mMap.getMyLocation().getLatitude();
                lon = mMap.getMyLocation().getLongitude();
            }

            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 12.0f);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 12.0f));
            mMap.animateCamera(update);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Phone is in airplane mode");
        builder.setCancelable(false);
        builder.setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent startGps = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(startGps);
            }
        });
        builder.setNegativeButton("Leave GPS off", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Log.d("MARKER-CLICK", "MARKER" + marker);
        return false;
    }
}
