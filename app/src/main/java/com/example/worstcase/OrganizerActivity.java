package com.example.worstcase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import java.util.HashMap;
import java.util.Map;

public class OrganizerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer);

        Button submit = (Button)findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            TextView organization_title = (TextView)findViewById(R.id.organization_title);
            TextView organization_provisions_header = (TextView)findViewById(R.id.organization_provisions_header);

            final CheckBox organization_checkbox_aid = (CheckBox)findViewById(R.id.organization_checkbox_aid);
            final CheckBox organization_checkbox_food = (CheckBox)findViewById(R.id.organization_checkbox_food);
            final CheckBox organization_checkbox_water = (CheckBox)findViewById(R.id.organization_checkbox_water);

            EditText organization_shelter_latitude = (EditText)findViewById(R.id.organization_shelter_latitude);
            EditText organization_shelter_longitude = (EditText)findViewById(R.id.organization_shelter_longitude);
            EditText organization_shelter_id = (EditText)findViewById(R.id.organization_id);
            EditText organization_shelter_maxcapacity = (EditText)findViewById(R.id.organization_shelter_maxcapacity);
            EditText organization_shelter_landmarks = (EditText)findViewById(R.id.organization_shelter_landmarks);

            //return these to dynamo
            String organization_shelter_id_string = organization_shelter_id.getText().toString();
            String organization_shelter_latitude_string = organization_shelter_latitude.getText().toString();
            String organization_shelter_longitude_string = organization_shelter_longitude.getText().toString();
            String organization_shelter_landmarks_string = organization_shelter_landmarks.getText().toString();
            String organization_shelter_maxcapacity_string = organization_shelter_maxcapacity.getText().toString();

            final String[] organization_checkbox_aid_string = new String[1];
            final String[] organization_checkbox_food_string = new String[1];
            final String[] organization_checkbox_water_string = new String[1];


            organization_checkbox_aid_string[0] = "false";
            organization_checkbox_water_string[0] = "false";
            organization_checkbox_food_string[0] = "false";

            if(organization_checkbox_aid.isChecked()){
                organization_checkbox_aid_string[0] = "true";
            }

            if(organization_checkbox_food.isChecked()){
                organization_checkbox_food_string[0] = "true";
            }

            if(organization_checkbox_water.isChecked()){
                organization_checkbox_water_string[0] = "true";
            }

            String food_string = "";
            String med_string = "";
            String water_string = "";
            if(organization_checkbox_food_string[0].equals("false")){
                food_string = "0";
            }
            else{
                food_string = "2";
            }

            if(organization_checkbox_aid_string[0].equals("false")){
                med_string = "0";
            }
            else{
                med_string = "2";
            }

            if(organization_checkbox_water_string[0].equals("false")){
                water_string = "0";
            }
            else{
                water_string = "2";
            }


            ////////////////////// SEND THE STRINGS TO DYNAMO HERER/////////////////////////////////////
            BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAYO4MKXNF4PU4GKOT", "NvVpxh0NIAwZpk2zNjDL7ZDg+RMxQXnTK/Kpb4OX");
            final AmazonDynamoDB ddb = new AmazonDynamoDBClient(awsCreds);


            Map<String, AttributeValue> attributeValues = new HashMap<>();

            attributeValues.put("ID",new AttributeValue().withN(organization_shelter_id_string));
            Map< String,AttributeValue> coordMap = new HashMap<String,AttributeValue>();

            attributeValues.put("Capacity",new AttributeValue().withS("false"));

            coordMap.put("Lat",new AttributeValue().withS(organization_shelter_latitude_string));
            coordMap.put("Long",new AttributeValue().withS(organization_shelter_longitude_string));
            attributeValues.put("Coordinates",new AttributeValue().withM(coordMap));

            attributeValues.put("Landmarks",new AttributeValue().withS(organization_shelter_landmarks_string));

            Map< String,AttributeValue> suppliesMap = new HashMap<String,AttributeValue>();
            suppliesMap.put("Food",new AttributeValue().withS(food_string));
            suppliesMap.put("Water",new AttributeValue().withS(water_string));
            suppliesMap.put("Medicine",new AttributeValue().withS(med_string));

            attributeValues.put("Supplies",new AttributeValue().withM(suppliesMap));

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
            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeIntent);

        }
    });




    }
}
