package com.mahesh.apple.honeywellscanner;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeReader;

public class Outward extends AppCompatActivity {

    Button viewbtn,cartbtn,scanbtn;

    private static BarcodeReader barcodeReader;
    private AidcManager manager;
    static boolean outwardscan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward);
        viewbtn=(Button)findViewById(R.id.viewbtn);
        cartbtn=(Button)findViewById(R.id.cartbtn);
        scanbtn=(Button)findViewById(R.id.scanbtn);
        outwardscan=true;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // create the AidcManager providing a Context and a
        // CreatedCallback implementation.
        AidcManager.create(this, new AidcManager.CreatedCallback() {

            @Override
            public void onCreated(AidcManager aidcManager) {
                manager = aidcManager;
                barcodeReader = manager.createBarcodeReader();
            }
        });

        // outwardscan=true;

        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Outward.this, OutwardView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Outward.this, Cart_Data.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barcodeIntent = new Intent("android.intent.action.SCANBARCODEACTIVITY");
                startActivity(barcodeIntent);
            }
        });

    }
    static BarcodeReader getBarcodeObject() {
        return barcodeReader;
    }
}
