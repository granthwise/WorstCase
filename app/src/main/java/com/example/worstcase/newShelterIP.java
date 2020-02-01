package com.example.worstcase;
import android.widget.Button;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import java.lang.*;

public class newShelterIP extends android.app.Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intermediate_page);

        Button victim = (Button) findViewById(R.id.newShelt);

        victim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                newShelter();
            }
        });
    }
    public void newShelter() {
        android.content.Intent intent= new android.content.Intent(this, JoesNewShelter.class);
        startActivity(intent);
    }
}
