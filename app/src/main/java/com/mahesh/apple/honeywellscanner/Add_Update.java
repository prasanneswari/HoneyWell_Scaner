package com.mahesh.apple.honeywellscanner;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.mahesh.apple.honeywellscanner.AutomaticBarcodeActivity.Barcode_data;

public class Add_Update extends Activity implements
        View.OnClickListener {

    Spinner company,commodity;
    Button savebtn,scanbtn;
    TextView datebtn,timebtn;
    EditText quality;

    String[] companyS={"COMPANY","JTS","Jochebedtech","HCL"};
    String[] commandtyS={"COMMANDITY","SBI","Gold","IDBI"};
    private int mYear, mMonth, mDay, mHour, mMinute;

    private static BarcodeReader barcodeReader;
    private AidcManager manager;
    static boolean addupdatescan;
    DatabaseHelper myDb;
    static String timedb,datedb,commanditydb,companydb,quantitydb;
    List<String> companyL;
    List<String> commadityL;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__update);
        commodity=(Spinner)findViewById(R.id.commadity);
        company=(Spinner)findViewById(R.id.company);
        quality=(EditText) findViewById(R.id.quantity);
      //  savebtn=(Button) findViewById(R.id.savebtn);
        scanbtn=(Button) findViewById(R.id.scanbtn);
        datebtn=(TextView) findViewById(R.id.date);
        timebtn=(TextView) findViewById(R.id.time);
        myDb = new DatabaseHelper(this);



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



        /*//Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter qualityadapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,qualityS);
        qualityadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        quality.setAdapter(qualityadapter);*/


        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy_HH:mm");
        String currentDateandTime = df.format(c.getTime());
      // Now formattedDate have current date/time

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
        // you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

        String localTime = date.format(currentLocalTime);


        String dateE = currentDateandTime.substring(0, 10);
        System.out.println("dateE; "+dateE);

        String timeE = localTime;
        System.out.println("localTime "+localTime);

        datebtn.setText(dateE);
        timebtn.setText(timeE);

        timebtn.setOnClickListener(this);
        datebtn.setOnClickListener(this);
        viewcomapnyAll();
        viewcommadityAll();


        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addupdatescan=true;

                 timedb = timebtn.getText().toString();
                 datedb = datebtn.getText().toString();
                 commanditydb = commodity.getSelectedItem().toString();
                 companydb = company.getSelectedItem().toString();
                 quantitydb = quality.getText().toString();
                Log.d("log","time"+timedb);
                Log.d("log","datedb"+datedb);
                Log.d("log","commanditydb"+commanditydb);
                Log.d("log","companydb"+companydb);
                Log.d("log","quantitydb"+quantitydb);


                    // get the intent action string from AndroidManifest.xml
                    Intent barcodeIntent = new Intent("android.intent.action.AUTOMATICBARCODEACTIVITY");
                    startActivity(barcodeIntent);

                /*Intent intent = new Intent(Add_Update.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            }
        });


       /* savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Barcode_data != null) {
                    String timedb = timebtn.getText().toString();
                    String datedb = datebtn.getText().toString();
                    String commanditydb = commandty.getSelectedItem().toString();
                    String companydb = company.getSelectedItem().toString();
                    String quantitydb = quality.getSelectedItem().toString();
                    Log.d("log","time"+timedb);
                    Log.d("log","datedb"+datedb);
                    Log.d("log","commanditydb"+commanditydb);
                    Log.d("log","companydb"+companydb);
                    Log.d("log","quantitydb"+quantitydb);

                    boolean isInserted = myDb.insertData(Barcode_data, companydb, commanditydb, timedb, datedb, quantitydb);
                    if (isInserted == true)
                        Toast.makeText(Add_Update.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Add_Update.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                }
            }

        });
*/

    }
    static BarcodeReader getBarcodeObject() {
        return barcodeReader;
    }

    @Override
    public void onClick(View v) {
        if (v == datebtn) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            datebtn.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == timebtn) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            timebtn.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
    public void viewcomapnyAll() {
        Cursor res = myDb.getAllData2();
        Log.d("array length---", "--res-" + res);

        if (res.getCount() == 0) {
            // show message
            Toast.makeText(Add_Update.this, "Nothing found", Toast.LENGTH_LONG).show();

            // showMessage("Error","Nothing found");
            return;
        }
        companyL = new ArrayList<String>();

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
            companyL.add(res.getString(1));
           // Log.d("array length---", "--companyarray-" + res.getString(1));

        }
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter companyadapter = new ArrayAdapter(this,R.layout.spinner_item,R.id.spinnertxt,companyL);
        companyadapter.setDropDownViewResource(R.layout.spinner_item);
        //Setting the ArrayAdapter data on the Spinner
        company.setAdapter(companyadapter);

        company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //Log.d("array length---", "--item-" + item);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void viewcommadityAll() {
        Cursor res = myDb.getAllData3();
        if (res.getCount() == 0) {
            // show message
            Toast.makeText(Add_Update.this, "Nothing found", Toast.LENGTH_LONG).show();

            // showMessage("Error","Nothing found");
            return;
        }
        commadityL = new ArrayList<String>();

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
            commadityL.add(res.getString(1));

        }
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter comandityadapter = new ArrayAdapter(this,R.layout.spinner_item,R.id.spinnertxt,commadityL);
        comandityadapter.setDropDownViewResource(R.layout.spinner_item);
        //Setting the ArrayAdapter data on the Spinner
        commodity.setAdapter(comandityadapter);
        commodity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}


