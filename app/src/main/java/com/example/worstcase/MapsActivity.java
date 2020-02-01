package com.example.worstcase;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        //READING A FULL TABLE OF DATA

        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAYO4MKXNF6QPMVBV3", "MiuoNvqvtGE9/xpnzQhIQGbjejGiWxD9xW3ECfYJ");
        final AmazonDynamoDB ddb = new AmazonDynamoDBClient(awsCreds);
        String table_name = "Blue-Light-Locations";
        System.out.println("PAST CREDENTIALS");

        final ScanRequest scanRequest1 = new ScanRequest()
                .withTableName("Blue-Light-Locations")
                .withAttributesToGet("Blue-Light-Number", "Location");
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


        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        BasicallyAThread foo = new BasicallyAThread();
        Thread thread = new Thread(foo);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ScanResult result = foo.getValue();

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
        boolean capacity = false;
        String landmarks = "";

        for (Map<String, AttributeValue> item : result.getItems()) {
            Set<String> locations = item.keySet();
            counter = 0;
            for (String location : locations) {
                if (counter == 0) {
                    number = item.get(location).getN();
                } else if (counter == 1) {
                    coordMap = item.get(location).getM();
                }
                else if(counter == 2){
                    suppliesMap = item.get(location).getM();
                }
                else if(counter == 3){
                    capacity = item.get(location).getBOOL();
                }
                else if(counter == 4){
                    landmarks = item.get(location).getS();
                }
                counter++;
            }
            lat = coordMap.get("Lat").getS();
            longi = coordMap.get("Long").getS();
            food = suppliesMap.get("Food").getN();
            water = suppliesMap.get("Water").getN();
            medicine = suppliesMap.get("Medicine").getN();


            shelterList.add(new shelter(Integer.valueOf(number), Double.valueOf(lat), Double.valueOf(longi),Integer.valueOf(food) ,Integer.valueOf(water) ,Integer.valueOf(medicine) ,Boolean.valueOf(capacity), String.valueOf(landmarks)));
            i++;
        }

        Map<String,AttributeValue> attributeValues = new HashMap<>();
        attributeValues.put("ID",new AttributeValue().withN("jon@doe.com"));

        attributeValues.put("Coordinates",new AttributeValue().withS("Jon Doe"));


        attributeValues.put("Supplies",new AttributeValue().withNS("Jon Doe"));

        //
        attributeValues.put("Capacity",new AttributeValue().withBOOL(true));
        attributeValues.put("Landmarks",new AttributeValue().withS("Jon Doe"));


        final PutItemRequest request = new PutItemRequest()
                .withTableName("Shelters")
                .withItem(attributeValues);

        class BasicallyAReadThread implements Runnable {
            private volatile PutItemResult result;

            @Override
            public void run() {
                result = ddb.putItem(request);
            }

            public PutItemResult getValue() {
                return result;
            }
        }

        BasicallyAReadThread bar = new BasicallyAReadThread();
        Thread thread2 = new Thread(bar);
        thread2.start();
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PutItemResult addResult = bar.getValue();



    }
}
