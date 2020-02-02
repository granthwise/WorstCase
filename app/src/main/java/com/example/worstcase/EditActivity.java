package com.example.worstcase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;

import java.util.HashMap;
import java.util.Map;

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

                String maxCap = "";

                if(organization_shelter_maxcapacity_string.equals("0")){
                    maxCap = "false";
                }
                else{
                    maxCap = "true";
                }

                BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAYO4MKXNF4PU4GKOT", "NvVpxh0NIAwZpk2zNjDL7ZDg+RMxQXnTK/Kpb4OX");
                final AmazonDynamoDB ddb = new AmazonDynamoDBClient(awsCreds);



                Map< String, AttributeValue> updatesuppliesMap = new HashMap<String,AttributeValue>();
                updatesuppliesMap.put("Food",new AttributeValue().withS(organization_shelter_food_string));
                updatesuppliesMap.put("Water",new AttributeValue().withS(organization_shelter_water_string));
                updatesuppliesMap.put("Medicine",new AttributeValue().withS(organization_shelter_aid_string));




                Map<String, AttributeValue> keysMap = new HashMap<>();
                keysMap.put("ID", new AttributeValue().withN(organization_shelter_id_string));


                final UpdateItemRequest updateRequest = new UpdateItemRequest()
                        .withTableName("Shelters")
                        .withKey(keysMap)
                        .addAttributeUpdatesEntry("Supplies",new AttributeValueUpdate().withValue(new AttributeValue().withM(updatesuppliesMap)).withAction(AttributeAction.PUT))
                        .addAttributeUpdatesEntry("Capacity",new AttributeValueUpdate().withValue(new AttributeValue().withS(maxCap)));


                class BasicallyAUpdateThread implements Runnable {
                    private volatile UpdateItemResult result;

                    @Override
                    public void run() {
                        result = ddb.updateItem(updateRequest);
                    }

                    public UpdateItemResult getValue() {
                        return result;
                    }
                }

                BasicallyAUpdateThread yeet = new BasicallyAUpdateThread();
                Thread thread4 = new Thread(yeet);
                thread4.start();
                try {
                    thread4.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        });
    }

}