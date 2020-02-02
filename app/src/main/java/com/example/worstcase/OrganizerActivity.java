package com.example.worstcase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class OrganizerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer);

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
        String organization_id_string = organization_title.getText().toString();
        String organization_shelter_latitude_string = organization_shelter_latitude.getText().toString();
        String organization_shelter_longitude_string = organization_shelter_longitude.getText().toString();
        String organization_shelter_landmarks_string = organization_shelter_landmarks.getText().toString();
        String organization_shelter_maxcapacity_string = organization_shelter_maxcapacity.getText().toString();

        organization_checkbox_aid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String organization_checkbox_aid_string = "false";
                if(organization_checkbox_aid.isChecked()){
                    organization_checkbox_aid_string = "true";
                }
            }
        });

        organization_checkbox_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String organization_checkbox_food_string = "false";
                if(organization_checkbox_aid.isChecked()){
                    organization_checkbox_food_string = "true";
                }
            }
        });

        organization_checkbox_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String organization_checkbox_water_string = "false";
                if(organization_checkbox_water.isChecked()){
                    organization_checkbox_water_string = "true";
                }
            }
        });











    }
}
