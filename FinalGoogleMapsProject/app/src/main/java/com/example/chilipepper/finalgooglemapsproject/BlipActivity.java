package com.example.chilipepper.finalgooglemapsproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by delshawnkirksey on 12/10/15.
 */
public class BlipActivity extends AppCompatActivity {

    ArrayList<String> blipProps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_details);

        Intent intent = getIntent();
        blipProps = intent.getStringArrayListExtra("Blip Properties");

        if (blipProps != null)
        {
            TextView dialogName = (TextView) findViewById(R.id.txtName);
            dialogName.setText(blipProps.get(0));

            TextView dialogAddress = (TextView) findViewById(R.id.txtAddress);
            dialogAddress.setText(blipProps.get(1));

            TextView dialogCity = (TextView) findViewById(R.id.txtCity);
            dialogCity.setText(blipProps.get(2));

            TextView dialogState = (TextView) findViewById(R.id.txtState);
            dialogState.setText(blipProps.get(3));

            TextView dialogZip = (TextView) findViewById(R.id.txtZip);
            dialogZip.setText(blipProps.get(4));

            TextView dialogRecord = (TextView) findViewById(R.id.txtRecord);
            dialogRecord.setText(blipProps.get(5));

            TextView dialogViolation = (TextView) findViewById(R.id.txtViolation);
            dialogViolation.setText(blipProps.get(6));

            TextView dialogDesc = (TextView) findViewById(R.id.txtDesc);
            dialogDesc.setText(blipProps.get(7));

            TextView dialogComments = (TextView) findViewById(R.id.txtComments);
            dialogComments.setText(blipProps.get(8));

            TextView dialogAction = (TextView) findViewById(R.id.actionStatus);
            dialogAction.setText(blipProps.get(9));

            ImageView iImage = (ImageView) findViewById(R.id.detailImage);

            if (blipProps.get(9).equalsIgnoreCase("Abated"))
            {
                iImage.setImageResource(R.drawable.clean);
            } else if (blipProps.get(9).equalsIgnoreCase("Not Abated"))
            {
                iImage.setImageResource(R.drawable.td);
            } else
            {
                iImage.setImageResource(R.drawable.tu);
            }

            Button dialogButton = (Button) findViewById(R.id.btnCancel);
            dialogButton.setText("OK");
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }
    }

}
