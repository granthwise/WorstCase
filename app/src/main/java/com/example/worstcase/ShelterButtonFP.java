package com.example.worstcase;
import android.widget.Button;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import java.lang.*;

public class ShelterButtonFP extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button shelter = (Button) findViewById(R.id.shelterButton);

        shelter.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view) {
                weSheltering();
            }
        });
    }
    public void weSheltering() {
        android.content.Intent intent = new android.content.Intent(this, intermediate_layout.xml);
        startActivity(intent);
    }
}
