package com.example.worstcase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

public class OrganizerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer);

        TextView organization_header = (TextView)findViewById(R.id.organization_title)


        CheckBox organization_checkbox_aid = (CheckBox)findViewById(R.id.organization_checkbox_aid);
        CheckBox organization_checkbox_food = (CheckBox)findViewById(R.id.organization_checkbox_food);
        CheckBox organization_checkbox_water = (CheckBox)findViewById(R.id.organization_checkbox_water);

        
    }
}
