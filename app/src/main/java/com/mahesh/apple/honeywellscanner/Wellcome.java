package com.mahesh.apple.honeywellscanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Wellcome extends AppCompatActivity {

    Button addbtn,viewbtn,generatebtn,uploadbtn,fetchbtn;
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress ;
    AlertDialog.Builder builderLoading;
    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        addbtn=(Button)findViewById(R.id.addbtn);
        viewbtn=(Button)findViewById(R.id.viewbtn);
        generatebtn=(Button)findViewById(R.id.generate);
        uploadbtn=(Button)findViewById(R.id.upload);
        fetchbtn=(Button)findViewById(R.id.fetchbtn);


        dialog_progress = new ProgressDialog(Wellcome.this);
        builderLoading = new AlertDialog.Builder(Wellcome.this);
        myDb = new DatabaseHelper(this);


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wellcome.this, Add_Update.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wellcome.this, Viewjava.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        generatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wellcome.this, Generate.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        fetchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getinward_data();
                getcompany();
                getcommadity();

            }
        });

    }

    public void getcompany(){

        String getcompanyS = "{\"username\":\"jtsadmin\",\"password\":\"jtsadmin\"}";
        //   Log.d("sending string is :", AddS.toString());
        Log.d("jsnresponse getcompanyS", "---" + getcompanyS);
        String getcompany_url = "http://cld003.jts-prod.in:5913/WareHouseApp/get_company/";
        JSONObject lstrmdt = null;

        try {
            lstrmdt = new JSONObject(getcompanyS);
            Log.d("jsnresponse....", "---" + getcompanyS);
            // dialog_progress.setMessage("connecting ...");
            dialog_progress.show();

            JSONSenderVolleygetvalue(getcompany_url, lstrmdt);

        } catch (JSONException e) {

        }

    }
    public void JSONSenderVolleygetvalue(String getcompany_url, final JSONObject json)
    {
        // Log.d("---Orgusers url-----", "---"+getusers_url);
        Log.d("555555", "getcompany_url"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                getcompany_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //  Log.d(" ", response.toString());
                        Log.d("----JSONOrguser volly--", "---"+response.toString());

                        if (response!=null){
                            if (response.has("error_code")){
                                try {
                                    String errorCode = response.getString("error_code");
                                    if (errorCode.contentEquals("0")){
                                        //Toast.makeText(getApplicationContext(), "Response=successfully added", Toast.LENGTH_LONG).show();
                                        dialog_progress.dismiss();

                                        JSONArray dataArray = new JSONArray(response.getString("get_company"));

                                        if (dataArray.length()!=0) {

                                            for (int i = 0; i < dataArray.length(); i++) {
                                                //  Log.d("array length---", "---" + i);

                                                JSONObject dataObject = new JSONObject(dataArray.get(i).toString());

                                                String company_id = dataObject.getString("company_id");
                                                String company_desc = dataObject.getString("company_desc");

                                               // Log.d("array length---", "--company_id-" + company_id);
                                               // Log.d("array length---", "--company_desc-" + company_desc);

                                                boolean isInserted = myDb.insertData2(company_desc);
                                                if (isInserted == true) {
                                                    Toast.makeText(Wellcome.this, "Data Inserted", Toast.LENGTH_LONG).show();

                                                }
                                                else
                                                    Toast.makeText(Wellcome.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        else {
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(Wellcome.this);
                                            builder.setMessage("Reasponse=Unable to get company data");

                                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface1, int i) {

                                                    dialog_progress.dismiss();
                                                    dialogInterface1.dismiss();
                                                }
                                            });

                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                        }

                                    }
                                    else {
                                        // Toast.makeText(getApplicationContext(), "Not added the notes", Toast.LENGTH_LONG).show();

                                        String msg;
                                        if(response.has("Response")){
                                            msg=response.getString("Response");
                                        }
                                        else{
                                            msg="Unable to get the company data";
                                        }
                                        //   Log.d("errorCode", "" + errorCode);
                                        final AlertDialog.Builder builder = new AlertDialog.Builder(Wellcome.this);
                                        builder.setMessage(msg);

                                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface1, int i) {

                                                dialog_progress.dismiss();
                                                dialogInterface1.dismiss();
                                            }
                                        });

                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }else {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Wellcome.this);
                                builder.setMessage("Reasponse=Unable to get the company data");

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface1, int i) {

                                        dialog_progress.dismiss();
                                        dialogInterface1.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "shulamithi: " + String.valueOf(error));
                //  Log.d("my test error-----","shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                dialog_progress.dismiss();

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                //headers.put("Content-Type","application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                //return (headers != null || headers.isEmpty()) ? headers : super.getHeaders();
                return headers;
            }
        };

        jsonObjReq.setTag("");
        addToRequestQueue(jsonObjReq);
    }
    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }

    public void getcommadity(){

        String getcommadityS = "{\"username\":\"jtsadmin\",\"password\":\"jtsadmin\"}";
        //   Log.d("sending string is :", AddS.toString());
        Log.d("jsnrespon getcommadityS", "---" + getcommadityS);
        String getcommadity_url = "http://cld003.jts-prod.in:5913/WareHouseApp/get_commodity/";
        JSONObject lstrmdt = null;

        try {
            lstrmdt = new JSONObject(getcommadityS);
            Log.d("jsnresponse....", "---" + getcommadityS);
            // dialog_progress.setMessage("connecting ...");
            dialog_progress.show();

            JSONSenderVolleycommadity(getcommadity_url, lstrmdt);

        } catch (JSONException e) {

        }

    }
    public void JSONSenderVolleycommadity(String getcommadity_url, final JSONObject json)
    {

        // Log.d("---Orgusers url-----", "---"+getusers_url);
        Log.d("555555", "getcommadity_url"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                getcommadity_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //  Log.d(" ", response.toString());
                        Log.d("----JSONOrguser volly--", "---"+response.toString());

                        if (response!=null){
                            if (response.has("error_code")){
                                try {
                                    String errorCode = response.getString("error_code");
                                    if (errorCode.contentEquals("0")){
                                        //Toast.makeText(getApplicationContext(), "Response=successfully added", Toast.LENGTH_LONG).show();
                                        dialog_progress.dismiss();

                                        JSONArray dataArray = new JSONArray(response.getString("get_commodity"));

                                        if (dataArray.length()!=0) {

                                            for (int i = 0; i < dataArray.length(); i++) {
                                                //  Log.d("array length---", "---" + i);

                                                JSONObject dataObject = new JSONObject(dataArray.get(i).toString());

                                                String commodity_id = dataObject.getString("commodity_id");
                                                String commodity_desc = dataObject.getString("commodity_desc");

                                             //   Log.d("array length---", "--commodity_id-" + commodity_id);
                                                //Log.d("array length---", "--commodity_desc-" + commodity_desc);

                                                boolean isInserted = myDb.insertData3(commodity_desc);
                                              //  Log.d("array length---", "--isInserted-" + isInserted);

                                                if (isInserted == true) {
                                                    Toast.makeText(Wellcome.this, "Data Inserted", Toast.LENGTH_LONG).show();

                                                }
                                                else
                                                    Toast.makeText(Wellcome.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        else {
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(Wellcome.this);
                                            builder.setMessage("Reasponse=Unable to get the commadity data");

                                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface1, int i) {

                                                    dialog_progress.dismiss();
                                                    dialogInterface1.dismiss();
                                                }
                                            });

                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                        }

                                    }
                                    else {
                                        // Toast.makeText(getApplicationContext(), "Not added the notes", Toast.LENGTH_LONG).show();

                                        String msg;
                                        if(response.has("Response")){
                                            msg=response.getString("Response");
                                        }
                                        else{
                                            msg="Unable to get the commadity data";
                                        }
                                        //   Log.d("errorCode", "" + errorCode);
                                        final AlertDialog.Builder builder = new AlertDialog.Builder(Wellcome.this);
                                        builder.setMessage(msg);

                                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface1, int i) {

                                                dialog_progress.dismiss();
                                                dialogInterface1.dismiss();
                                            }
                                        });

                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }else {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Wellcome.this);
                                builder.setMessage("Reasponse=Unable to get the commadity data");

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface1, int i) {

                                        dialog_progress.dismiss();
                                        dialogInterface1.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "shulamithi: " + String.valueOf(error));
                //  Log.d("my test error-----","shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                dialog_progress.dismiss();

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                //headers.put("Content-Type","application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                //return (headers != null || headers.isEmpty()) ? headers : super.getHeaders();
                return headers;
            }
        };

        jsonObjReq.setTag("");
        addToRequestQueue1(jsonObjReq);
    }
    public <T> void addToRequestQueue1(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }


    public void getinward_data(){

        String getinward_dataS = "{\"username\":\"jtsadmin\",\"password\":\"jtsadmin\"}";
        //   Log.d("sending string is :", AddS.toString());
        Log.d("jsn getinward_dataS", "---" + getinward_dataS);
        String getinward_url = "http://cld003.jts-prod.in:5913/WareHouseApp/get_inwards_data/";
        JSONObject lstrmdt = null;

        try {
            lstrmdt = new JSONObject(getinward_dataS);
            Log.d("jsnresponse....", "---" + getinward_dataS);
            // dialog_progress.setMessage("connecting ...");
            dialog_progress.show();

            JSONSenderVolleygetinward(getinward_url, lstrmdt);

        } catch (JSONException e) {

        }

    }
    public void JSONSenderVolleygetinward(String getinward_url, final JSONObject json)
    {

        // Log.d("---Orgusers url-----", "---"+getusers_url);
        Log.d("555555", "getinward_url"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                getinward_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //  Log.d(" ", response.toString());
                        Log.d("----JSONOrguser volly--", "---"+response.toString());

                        if (response!=null){
                            if (response.has("error_code")){
                                try {
                                    String errorCode = response.getString("error_code");
                                    if (errorCode.contentEquals("0")){
                                        //Toast.makeText(getApplicationContext(), "Response=successfully added", Toast.LENGTH_LONG).show();
                                        dialog_progress.dismiss();

                                        JSONArray dataArray = new JSONArray(response.getString("get_inwards_data"));

                                        if (dataArray.length()!=0) {

                                            for (int i = 0; i < dataArray.length(); i++) {
                                                //  Log.d("array length---", "---" + i);

                                                JSONObject dataObject = new JSONObject(dataArray.get(i).toString());

                                                String barcode = dataObject.getString("barcode");
                                                String company = dataObject.getString("company");
                                                String commodity = dataObject.getString("commodity");
                                                String quantity = dataObject.getString("quantity");
                                                String changedate = dataObject.getString("changedate");

                                                String date = changedate.substring(0, 10);
                                                System.out.println("date; "+date);

                                                String time = changedate.substring(12, 16);
                                                System.out.println("time; "+time);


                                                Log.d("array length---", "--changedate-" + changedate);

                                                boolean isInserted = myDb.insertData1(barcode,company,commodity,time,date,quantity);
                                                //Log.d("array length---", "--isInserted-" + isInserted);

                                                if (isInserted == true) {
                                                    Toast.makeText(Wellcome.this, "Inward Data Inserted", Toast.LENGTH_LONG).show();

                                                }
                                                else
                                                    Toast.makeText(Wellcome.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        else {
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(Wellcome.this);
                                            builder.setMessage("Reasponse=Unable to get the commadity data");

                                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface1, int i) {

                                                    dialog_progress.dismiss();
                                                    dialogInterface1.dismiss();
                                                }
                                            });

                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                        }

                                    }
                                    else {
                                        // Toast.makeText(getApplicationContext(), "Not added the notes", Toast.LENGTH_LONG).show();

                                        String msg;
                                        if(response.has("Response")){
                                            msg=response.getString("Response");
                                        }
                                        else{
                                            msg="Unable to get the commadity data";
                                        }
                                        //   Log.d("errorCode", "" + errorCode);
                                        final AlertDialog.Builder builder = new AlertDialog.Builder(Wellcome.this);
                                        builder.setMessage(msg);

                                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface1, int i) {

                                                dialog_progress.dismiss();
                                                dialogInterface1.dismiss();
                                            }
                                        });

                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }else {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Wellcome.this);
                                builder.setMessage("Reasponse=Unable to get the commadity data");

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface1, int i) {

                                        dialog_progress.dismiss();
                                        dialogInterface1.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "shulamithi: " + String.valueOf(error));
                //  Log.d("my test error-----","shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                dialog_progress.dismiss();

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                //headers.put("Content-Type","application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                //return (headers != null || headers.isEmpty()) ? headers : super.getHeaders();
                return headers;
            }
        };

        jsonObjReq.setTag("");
        addToRequestQueue2(jsonObjReq);
    }
    public <T> void addToRequestQueue2(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }
}
