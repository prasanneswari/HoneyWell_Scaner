package com.mahesh.apple.honeywellscanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeReader;

import java.util.ArrayList;
import java.util.List;

public class Viewjava extends AppCompatActivity {

    ListView listdata;
    Button scanlst;

    private static BarcodeReader barcodeReader;
    private AidcManager manager;
    static boolean viewscan;
    String Barcode_data,Character_Set,Code,AIM_ID,Timestamp;
    List<String> Barcode_dataL;
    List<String> companeyL;
    List<String> commadityL;
    List<String> timeL;
    List<String> dateL;
    List<String> quantityL;
    static List<String>idL;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        listdata=(ListView)findViewById(R.id.listdata);
        //scanlst=(Button) findViewById(R.id.scanlst);

        myDb = new DatabaseHelper(this);
        viewAll();

    }

    public void viewAll() {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            Toast.makeText(Viewjava.this,"Nothing found", Toast.LENGTH_LONG).show();

           // showMessage("Error","Nothing found");
            return;
        }
        idL = new ArrayList<String>();
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
            idL.add(res.getString(0));

            Barcode_dataL.add(res.getString(1));
            companeyL.add(res.getString(2));
            commadityL.add(res.getString(3));
            timeL.add(res.getString(4));
            dateL.add(res.getString(5));
            quantityL.add(res.getString(6));
        }

        Adapter_View adapter = new Adapter_View(Viewjava.this, Barcode_dataL, companeyL, commadityL, timeL, dateL, quantityL);
        listdata.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Toast.makeText(Viewjava.this, "Get the Data", Toast.LENGTH_LONG).show();

        // Show all data
       // showMessage("Data",buffer.toString());

    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
}
}
