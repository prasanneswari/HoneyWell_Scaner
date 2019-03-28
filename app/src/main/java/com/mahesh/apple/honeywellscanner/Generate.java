package com.mahesh.apple.honeywellscanner;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Generate extends AppCompatActivity {
    LinearLayout linearid;
    ListView lstdata;
    DatabaseHelper myDb;
    //List<String> Barcode_dataL;
    List<String> companeyL;
    List<String> commadityL;
    List<String> timeL;
    List<String> dateL;
    List<String> quantityL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        linearid = (LinearLayout) findViewById(R.id.lineraid);
        lstdata = (ListView) findViewById(R.id.listdata);
        myDb = new DatabaseHelper(this);
        viewAll();
    }

    public void viewAll() {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            // show message
            Toast.makeText(Generate.this, "Nothing found", Toast.LENGTH_LONG).show();

            // showMessage("Error","Nothing found");
            return;
        }
      //  Barcode_dataL = new ArrayList<String>();
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
         //   Barcode_dataL.add(res.getString(1));
            companeyL.add(res.getString(2));
            commadityL.add(res.getString(3));
            timeL.add(res.getString(4));
            dateL.add(res.getString(5));
            quantityL.add(res.getString(6));


        }

        Adapter_Generate adapter = new Adapter_Generate(Generate.this, companeyL, commadityL, timeL, dateL, quantityL);
        lstdata.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Toast.makeText(Generate.this, "Get the Data", Toast.LENGTH_LONG).show();

    }
}
