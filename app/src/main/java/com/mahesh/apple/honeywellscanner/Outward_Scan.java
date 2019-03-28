package com.mahesh.apple.honeywellscanner;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Outward_Scan extends Activity implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {
    private com.honeywell.aidc.BarcodeReader barcodeReader;
    private ListView barcodeList;
    static String Barcode_data;
    DatabaseHelper myDb;
    String timeS,dateS,commandityS,companyS,quantityS;
    List<String> Barcode_dataL;
    List<String> companeyL;
    List<String> commadityL;
    List<String> timeL;
    List<String> dateL;
    List<String> quantityL;
    static List<String>idscanL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward__scan);
        myDb = new DatabaseHelper(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        // get bar code instance from MainActivity
        barcodeReader = Outward.getBarcodeObject();


        if (barcodeReader != null) {

            // register bar code event listener
            barcodeReader.addBarcodeListener(this);

            // set the trigger mode to client control
            try {
                barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
                        BarcodeReader.TRIGGER_CONTROL_MODE_AUTO_CONTROL);
            } catch (UnsupportedPropertyException e) {
                Toast.makeText(this, "Failed to apply properties", Toast.LENGTH_SHORT).show();
            }
            // register trigger state change listener
            barcodeReader.addTriggerListener(this);

            Map<String, Object> properties = new HashMap<String, Object>();
            // Set Symbologies On/Off
            properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);
            properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, false);
            // Set Max Code 39 barcode length
            properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 10);
            // Turn on center decoding
            properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);
            // Enable bad read response
            properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, true);
            // Apply the settings
            barcodeReader.setProperties(properties);
        }

        // get initial list
        barcodeList = (ListView) findViewById(R.id.listdata);

    }
    @Override
    public void onBarcodeEvent(final BarcodeReadEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // update UI to reflect the data

                List<String> list = new ArrayList<String>();
                List<String> Barcode_dataL = new ArrayList<String>();
                List<String> Character_SetL = new ArrayList<String>();
                List<String> CodeL = new ArrayList<String>();
                List<String> AIM_IDL = new ArrayList<String>();
                List<String> TimestampL = new ArrayList<String>();

                list.add("Barcode data: " + event.getBarcodeData());
                list.add("Character Set: " + event.getCharset());
                list.add("Code ID: " + event.getCodeId());
                list.add("AIM ID: " + event.getAimId());
                list.add("Timestamp: " + event.getTimestamp());
                Log.d("log::","list"+list);

                Barcode_data =event.getBarcodeData();
                Log.d("log::","Barcode_data"+Barcode_data);
                String Character_Set = String.valueOf(event.getCharset());
                Log.d("log::","Character_Set"+Character_Set);
                String Code =event.getCodeId();
                Log.d("log::","Code"+Code);
                String AIM_ID =event.getAimId();
                Log.d("log::","AIM_ID"+AIM_ID);
                String Timestamp =event.getTimestamp();
                Log.d("log::","Timestamp"+Timestamp);

                Barcode_dataL.add(Barcode_data);
                Character_SetL.add(Character_Set);
                CodeL.add(Code);
                AIM_IDL.add(AIM_ID);
                TimestampL.add(Timestamp);

                //  shredscanvalues(Barcode_dataL,Character_SetL,CodeL,AIM_IDL,TimestampL);

                /*final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                        AutomaticBarcodeActivity.this, android.R.layout.simple_list_item_1, list);

                barcodeList.setAdapter(dataAdapter);*/

                outwardscanAll(Barcode_data);
            }
        });
    }

    // When using Automatic Trigger control do not need to implement the
    // onTriggerEvent function
    @Override
    public void onTriggerEvent(TriggerStateChangeEvent event) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onResume() {
        super.onResume();
        if (barcodeReader != null) {
            try {
                barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
                Toast.makeText(this, "Scanner unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (barcodeReader != null) {
            // release the scanner claim so we don't get any scanner
            // notifications while paused.
            barcodeReader.release();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (barcodeReader != null) {
            // unregister barcode event listener
            barcodeReader.removeBarcodeListener(this);

            // unregister trigger state change listener
            barcodeReader.removeTriggerListener(this);
        }
    }

    public void outwardscanAll(String barcode) {
        Cursor res = myDb.getScanData(barcode);
        if(res.getCount() == 0) {
            // show message
            Toast.makeText(Outward_Scan.this,"Nothing found", Toast.LENGTH_LONG).show();

            // showMessage("Error","Nothing found");
            return;
        }
        idscanL = new ArrayList<String>();
        Barcode_dataL = new ArrayList<String>();
        companeyL = new ArrayList<String>();
        commadityL = new ArrayList<String>();
        timeL = new ArrayList<String>();
        dateL = new ArrayList<String>();
        quantityL = new ArrayList<String>();

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            /*buffer.append("Id :"+ res.getString(0)+"\n");
            buffer.append("Name :"+ res.getString(1)+"\n");
            buffer.append("Surname :"+ res.getString(2)+"\n");
            buffer.append("Marks :"+ res.getString(3)+"\n\n");
            buffer.append("Marks :"+ res.getString(4)+"\n\n");
            Log.d("log","id value"+res.getString(0));
            Log.d("log","barcodevalue value"+res.getString(1));
          */
            idscanL.add(res.getString(0));

            Barcode_dataL.add(res.getString(1));
            companeyL.add(res.getString(2));
            commadityL.add(res.getString(3));
            timeL.add(res.getString(4));
            dateL.add(res.getString(5));
            quantityL.add(res.getString(6));


        }

        Adaptre_OutwardView adapter = new Adaptre_OutwardView(Outward_Scan.this, Barcode_dataL, companeyL, commadityL, timeL, dateL, quantityL);
        barcodeList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Toast.makeText(Outward_Scan.this, "Get the Data", Toast.LENGTH_LONG).show();

        // Show all data
        // showMessage("Data",buffer.toString());

    }
}
