package com.example.worstcase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Button submit = (Button) findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView organization_title = (TextView) findViewById(R.id.organization_title);
                TextView organization_provisions_header = (TextView) findViewById(R.id.organization_provisions_header);

                final CheckBox organization_checkbox_aid = (CheckBox) findViewById(R.id.organization_checkbox_aid);
                final CheckBox organization_checkbox_food = (CheckBox) findViewById(R.id.organization_checkbox_food);
                final CheckBox organization_checkbox_water = (CheckBox) findViewById(R.id.organization_checkbox_water);

                EditText organization_shelter_latitude = (EditText) findViewById(R.id.organization_shelter_latitude);
                EditText organization_shelter_longitude = (EditText) findViewById(R.id.organization_shelter_longitude);
                EditText organization_shelter_id = (EditText) findViewById(R.id.organization_id);
                EditText organization_shelter_maxcapacity = (EditText) findViewById(R.id.organization_shelter_maxcapacity);
                EditText organization_shelter_landmarks = (EditText) findViewById(R.id.organization_shelter_landmarks);

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

                if (organization_checkbox_aid.isChecked()) {
                    organization_checkbox_aid_string[0] = "true";
                }

                if (organization_checkbox_food.isChecked()) {
                    organization_checkbox_food_string[0] = "true";
                }

                if (organization_checkbox_water.isChecked()) {
                    organization_checkbox_water_string[0] = "true";
                }

            }

        });
    }

}