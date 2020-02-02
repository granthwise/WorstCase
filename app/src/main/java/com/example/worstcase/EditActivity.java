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

                TextView update_title = (TextView) findViewById(R.id.update_title);
                TextView edit_organization_provisions_header = (TextView) findViewById(R.id.edit_organization_provisions_header);

                EditText edit_organization_shelter_aid = (EditText) findViewById(R.id.edit_organization_shelter_aid);
                EditText edit_organization_shelter_food = (EditText) findViewById(R.id.edit_organization_shelter_food);
                EditText edit_organization_shelter_id = (EditText) findViewById(R.id.edit_organization_id);
                EditText edit_organization_shelter_maxcapacity = (EditText) findViewById(R.id.edit_organization_shelter_maxcapacity);
                EditText edit_organization_shelter_water = (EditText) findViewById(R.id.edit_organization_shelter_water);

                //return these to dynamo
                String organization_shelter_aid_string = edit_organization_shelter_aid.getText().toString();
                String organization_shelter_food_string = edit_organization_shelter_food.getText().toString();
                String organization_shelter_water_string = edit_organization_shelter_water.getText().toString();
                String organization_shelter_id_string = edit_organization_shelter_id.getText().toString();
                String organization_shelter_maxcapacity_string = edit_organization_shelter_maxcapacity.getText().toString();

                final String[] organization_checkbox_aid_string = new String[1];
                final String[] organization_checkbox_food_string = new String[1];
                final String[] organization_checkbox_water_string = new String[1];


                organization_checkbox_aid_string[0] = "false";
                organization_checkbox_water_string[0] = "false";
                organization_checkbox_food_string[0] = "false";


                //INFO GOES HERE GAIJASDINIDSSDNTY

            }

        });
    }

}