package com.example.worstcase;
import android.widget.Button;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import java.lang.*;

public class VictimButtonFP extends android.app.Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button victim = (Button) findViewById(R.id.victimButton);

        victim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openSesame();
            }
        });
    }
    public void openSesame() {
        android.content.Intent intent= new android.content.Intent(this, MapsActivity.class);
        //how to pass information
        startActivity(intent);
    }
}
