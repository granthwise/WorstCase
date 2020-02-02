package com.example.worstcase;
import android.content.Intent;
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

        Button newShelter = (Button) findViewById(R.id.newShelt);
        Button editor = (Button) findViewById(R.id.edit);

        newShelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), OrganizerActivity.class);
                //how to pass information
                startActivity(startIntent);
            }
        });

        editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), EditActivity.class);
                //how to pass information
                startActivity(startIntent);
            }
        });
    }
}


