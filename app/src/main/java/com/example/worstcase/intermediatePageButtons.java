package com.example.worstcase;
import android.widget.Button;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import java.lang.*;

public class intermediatePageButtons extends android.app.Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intermediate_page);

        Button newSheltr = (Button) findViewById(R.id.newShelt);

        newSheltr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                newShelter();
            }
        });

        Button editr = (Button) findViewById(R.id.edit);

        editr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                editShelters();
            }
        });
    }
    public void newShelter() {
        android.content.Intent intent= new android.content.Intent(this, OrganizerActivity.class);
        startActivity(intent);
    }
    public void editShelters() {
        //android.content.Intent intent= new android.content.Intent(this, ShelterPuberty.class);
       // startActivity(intent);
    }
}
