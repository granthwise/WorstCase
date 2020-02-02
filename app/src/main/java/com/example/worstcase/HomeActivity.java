package com.example.worstcase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_front_page);

        Button victim = (Button) findViewById(R.id.victimButton);
        victim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent victimIntent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(victimIntent);
            }
        });

        Button shelter = (Button) findViewById(R.id.shelterButton);
        shelter.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view) {
                Intent shelterIntent = new Intent(getApplicationContext(), OrganizerActivity.class);
                startActivity(shelterIntent);
            }
        });
    }

}

