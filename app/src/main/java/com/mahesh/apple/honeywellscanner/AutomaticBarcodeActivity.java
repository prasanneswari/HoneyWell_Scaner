package com.mahesh.apple.honeywellscanner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.honeywell.aidc.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mahesh.apple.honeywellscanner.Add_Update.commanditydb;
import static com.mahesh.apple.honeywellscanner.Add_Update.companydb;
import static com.mahesh.apple.honeywellscanner.Add_Update.datedb;
import static com.mahesh.apple.honeywellscanner.Add_Update.quantitydb;
import static com.mahesh.apple.honeywellscanner.Add_Update.timedb;

public class AutomaticBarcodeActivity extends Activity implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {

    private com.honeywell.aidc.BarcodeReader barcodeReader;
    private ListView barcodeList;
    static String Barcode_data;
    DatabaseHelper myDb;
    String timeS,dateS,commandityS,companyS,quantityS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        myDb = new DatabaseHelper(this);

       /* Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        timeS= extras.getString("timedb");
        dateS= extras.getString("datedb");
        commandityS= extras.getString("commanditydb");
        companyS= extras.getString("companydb");
        quantityS= extras.getString("quantitydb");*/

        // set lock the orientation
        // otherwise, the onDestory will trigger when orientation changes
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


            // get bar code instance from MainActivity
            barcodeReader = Add_Update.getBarcodeObject();


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
        barcodeList = (ListView) findViewById(R.id.listViewBarcodeData);
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
                Adapter_Addupdate adapter = new Adapter_Addupdate(AutomaticBarcodeActivity.this, Barcode_dataL,Character_SetL,CodeL,AIM_IDL,TimestampL);
                barcodeList.setAdapter(adapter);
                scanpop();

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

    public void scanpop(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AutomaticBarcodeActivity.this);
        LayoutInflater inflater = (LayoutInflater) AutomaticBarcodeActivity.this.getSystemService(getApplication().LAYOUT_INFLATER_SERVICE);
        View dialogLayout = inflater.inflate(R.layout.scansuccess_popup,
                null);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setView(dialogLayout, 0, 0, 0, 0);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        WindowManager.LayoutParams wlmp = dialog.getWindow()
                .getAttributes();
        wlmp.gravity = Gravity.CENTER;
        Button success = (Button) dialogLayout.findViewById(R.id.scansuccess);

        boolean isInserted = myDb.insertData(Barcode_data, companydb, commanditydb, timedb, datedb, quantitydb);
        if (isInserted == true) {
            Toast.makeText(AutomaticBarcodeActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AutomaticBarcodeActivity.this, Wellcome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else
            Toast.makeText(AutomaticBarcodeActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();



        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //delgroup(grpnm);
                dialog.dismiss();

            }

        });
        dialog.show();

    }

}
