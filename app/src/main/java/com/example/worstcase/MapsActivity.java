package com.example.worstcase;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.lang.*;

//Proof of Concept Nate
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Add a marker in Sydney and move the camera
        //LatLng startPlace = new LatLng(29.64997, 277.65287);
        //mMap.addMarker(new MarkerOptions().position(startPlace).title("Start Place").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(startPlace));


        ArrayList<shelter> str = new ArrayList<>();

        shelter test1 = new shelter(29.64997, 277.6513, 2,1,0,false, "At chick fil a");
        shelter test2 = new shelter(29.7, 277.7, 2,1,2,true, "whats up");
        shelter test3 = new shelter(29.66, 277.66, 2,0,2,false, "At new place");
        shelter test4 = new shelter(29.6, 277.8, 2,1,2,false, "At new place again");
        str.add(test1);
        str.add(test2);
        str.add(test3);
        str.add(test4);
        LatLng current = new LatLng(29.69, 277.68);
        mMap.addMarker(new MarkerOptions().position(current).title("current location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        int index = 0;
        double distance = Math.sqrt(((29.69 - str.get(0).getLat()) * (29.69 - str.get(0).getLat())) + ((277.68 - str.get(0).getLongi()) * (277.68 - str.get(0).getLongi())));
        double smallest = distance;
        for (int x = 1; x < str.size(); x++) {
            double tempDistance = Math.sqrt(((29.69 - str.get(x).getLat()) * (29.69 - str.get(x).getLat())) + ((277.68 - str.get(x).getLongi()) * (277.68 - str.get(x).getLongi())));
            if (str.get(x).getCapacity() == false && tempDistance < smallest ) {
                smallest = tempDistance;
                index = x;
            }
        }
        for (int x = 0; x < str.size(); x++) {
            LatLng shelter = new LatLng(str.get(x).getLat(), str.get(x).getLongi());
            if (x == index) {
                mMap.addMarker(new MarkerOptions().position(shelter).title(str.get(x).getName() + " Food:" + str.get(x).getStringFood() + " Water: " + str.get(x).getStringWater()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            else {
                mMap.addMarker(new MarkerOptions().position(shelter).title(str.get(x).getName() + " Food:" + str.get(x).getStringFood() + " Water: " + str.get(x).getStringWater()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(shelter));
        }

    }
}
