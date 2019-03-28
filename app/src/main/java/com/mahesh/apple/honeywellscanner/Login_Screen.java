package com.mahesh.apple.honeywellscanner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Screen extends AppCompatActivity {

    EditText mail,pwd;
    TextView forgetpwd;
    Button login,signinbtn;
    RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress ;
    AlertDialog.Builder builderLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mail=(EditText)findViewById(R.id.mail);
        pwd=(EditText)findViewById(R.id.pwd);
        forgetpwd=(TextView) findViewById(R.id.forgetpwd);
        login=(Button) findViewById(R.id.loginbtn);
        signinbtn=(Button) findViewById(R.id.singinbtn);
        dialog_progress = new ProgressDialog(Login_Screen.this);
        builderLoading = new AlertDialog.Builder(Login_Screen.this);


        login.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Screen.this, Signup_Screen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    public void login(){

        String getloginS = "{\"username\":\"jtsadmin\",\"password\":\"jtsadmin\"}";
        //   Log.d("sending string is :", AddS.toString());
        Log.d("jsnresponse getloginS", "---" + getloginS);
        String getlogin_url = "http://cld003.jts-prod.in:5913/WareHouseApp/login/";
        JSONObject lstrmdt = null;

        try {
            lstrmdt = new JSONObject(getloginS);
            Log.d("jsnresponse....", "---" + getloginS);
            // dialog_progress.setMessage("connecting ...");
            dialog_progress.show();

            JSONSenderVolleygetvalue(getlogin_url, lstrmdt);

        } catch (JSONException e) {

        }

    }
    public void JSONSenderVolleygetvalue(String getlogin_url, final JSONObject json)
    {
        // Log.d("---Orgusers url-----", "---"+getusers_url);
        Log.d("555555", "getlogin_url"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                getlogin_url, json,

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
                                        Intent intent = new Intent(Login_Screen.this, Inward_Outward.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
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
                                        final AlertDialog.Builder builder = new AlertDialog.Builder(Login_Screen.this);
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
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Login_Screen.this);
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
}
