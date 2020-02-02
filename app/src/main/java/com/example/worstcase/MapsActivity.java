package com.example.worstcase;

import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        mMap.setMinZoomPreference(7);

        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAYO4MKXNF4PU4GKOT", "NvVpxh0NIAwZpk2zNjDL7ZDg+RMxQXnTK/Kpb4OX");
        final AmazonDynamoDB ddb = new AmazonDynamoDBClient(awsCreds);
        String table_name = "Blue-Light-Locations";
        System.out.println("PAST CREDENTIALS");

        final ScanRequest scanRequest1 = new ScanRequest()
                .withTableName("Shelters")
                .withAttributesToGet("ID", "Capacity", "Coordinates" , "Landmarks" , "Supplies");
        System.out.println("created the scan request");


        class BasicallyAThread implements Runnable {
            private volatile ScanResult result;

            @Override
            public void run() {
                result = ddb.scan(scanRequest1);
            }

            public ScanResult getValue() {
                return result;
            }
        }

        System.out.println("BEFORE THE THREAD CREATION");
        BasicallyAThread foo = new BasicallyAThread();
        Thread thread = new Thread(foo);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ScanResult result = foo.getValue();
        System.out.println("GOT TO THE SHELTERLIST");
        ArrayList<shelter> shelterList = new ArrayList<>();
        int i = 0;
        int counter = 0;
        String number = "";
        String longi = "";
        String lat = "";
        String food = "";
        String water = "";
        String medicine = "";

        Map<String,AttributeValue> coordMap = new HashMap<String,AttributeValue>();
        Map<String,AttributeValue> suppliesMap = new HashMap<String,AttributeValue>();
        String capacity = "";
        String landmarks = "";

        for (Map<String, AttributeValue> item : result.getItems()) {
            Set<String> locations = item.keySet();
            counter = 0;
            for (String location : locations) {
                if (counter == 0) {
                    capacity = item.get(location).getS();
                } else if (counter == 1) {
                    coordMap = item.get(location).getM();
                }
                else if(counter == 2){
                    landmarks = item.get(location).getS();
                }
                else if(counter == 3){
                    suppliesMap = item.get(location).getM();
                }
                else if(counter == 4){
                    number = item.get(location).getN();
                }
                counter++;
            }

            lat = coordMap.get("Lat").getS();
            longi = coordMap.get("Long").getS();
            food = suppliesMap.get("Food").getS();
            water = suppliesMap.get("Water").getS();
            medicine = suppliesMap.get("Medicine").getS();

        shelterList.add(new shelter(Integer.valueOf(number), Double.valueOf(lat), Double.valueOf(longi),Integer.valueOf(food) ,Integer.valueOf(water) ,Integer.valueOf(medicine) ,capacity, String.valueOf(landmarks)));
        }

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // get the last know location from your location manager.
        final Location finalLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // now get the lat/lon from the location and do something with it.
        System.out.println(finalLocation.getLatitude());
        System.out.println(finalLocation.getLongitude());
        LatLng location = new LatLng(finalLocation.getLatitude(), finalLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(location).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

        int index = 0;
        double distance = Math.sqrt(((29.69 - shelterList.get(0).getLat()) * (29.69 - shelterList.get(0).getLat())) + ((277.68 - shelterList.get(0).getLongi()) * (277.68 - shelterList.get(0).getLongi())));
        double smallest = distance;
        for (int x = 1; x < shelterList.size(); x++) {
            double tempDistance = Math.sqrt(((29.69 - shelterList.get(x).getLat()) * (29.69 - shelterList.get(x).getLat())) + ((277.68 - shelterList.get(x).getLongi()) * (277.68 - shelterList.get(x).getLongi())));
            if (shelterList.get(x).getCapacity() == false && tempDistance < smallest ) {
                smallest = tempDistance;
                index = x;
            }
        }
        for (int x = 0; x < shelterList.size(); x++) {
            LatLng shelter = new LatLng(shelterList.get(x).getLat(), shelterList.get(x).getLongi());
            if (x == index) {
                mMap.addMarker(new MarkerOptions().position(shelter).title(" Food:" + shelterList.get(x).getStringFood() + " Water: " + shelterList.get(x).getStringWater() + " Medicine:" + shelterList.get(x).getStringMedicine() + " At Capacity: " + shelterList.get(x).getCapacity()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            else {
                mMap.addMarker(new MarkerOptions().position(shelter).title(" Food:" + shelterList.get(x).getStringFood() + " Water: " + shelterList.get(x).getStringWater() + " Medicine:" + shelterList.get(x).getStringMedicine() + " At Capacity: " + shelterList.get(x).getCapacity()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(shelter));
        }



    }
}
