package com.mahesh.apple.honeywellscanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.mahesh.apple.honeywellscanner.Viewjava.idL;

public class Adapter_View  extends ArrayAdapter<String> {

    private Context context;
    private List<String> barcodedata;
    private List<String> companey;
    private List<String> commadity;
    private List<String> time;
    private List<String> date;
    private List<String> quantity;


    public static String lcusname;
    public static String lfullname;
    public static String lcuscontact;
    public static String lapntdate;
    public static String lvisitdate;
    static int posval;
    public static String lnotes;
    TextView barcodedataT,companeyT,commadityT,timeT,dateT,quantityT;
    Button radiobtnb,radiocenterbtn;
    DatabaseHelper myDb;
    int id;
    String[] companyS={"COMPANY","JTS","Jochebedtech","HCL"};
    String[] commandtyS={"COMMANDITY","SBI","Gold","IDBI"};
    String[] qualityS={"QUANTITY","200"};



    public Adapter_View(Context context, List<String> barcodedataS, List<String> companeyS, List<String> commadityS, List<String> timeS,List<String> dateS, List<String> quantityS) {

        super(context, R.layout.activity_view, barcodedataS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.barcodedata = barcodedataS;
        this.companey = companeyS;
        this.commadity = commadityS;
        this.time = timeS;
        this.date = dateS;
        this.quantity = quantityS;

    }
    @Override
    public android.view.View getView(final int position, android.view.View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_adapter__view, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);
        //Assigning IDs from xml
        posval=position;
        myDb = new DatabaseHelper(context);

        barcodedataT = (TextView) rowView.findViewById(R.id.barcodedta);
        companeyT = (TextView) rowView.findViewById(R.id.companeyA);
        commadityT = (TextView) rowView.findViewById(R.id.commandityA);
        timeT = (TextView) rowView.findViewById(R.id.timeA);
        dateT = (TextView) rowView.findViewById(R.id.dateA);
        quantityT = (TextView) rowView.findViewById(R.id.quantityA);

        radiobtnb = (Button) rowView.findViewById(R.id.radiobtn);
        radiocenterbtn = (Button) rowView.findViewById(R.id.radiocenterbtn);

        try {

            //Assigning values from array to individual layouts in list view
            barcodedataT.setText(barcodedata.get(position));
            companeyT.setText(companey.get(position));
            commadityT.setText(commadity.get(position));
            timeT.setText(time.get(position));
            dateT.setText(date.get(position));
            quantityT.setText(quantity.get(position));

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        radiobtnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radiocenterbtn.setVisibility(View.VISIBLE);
                radiobtnb.setVisibility(View.GONE);
                updatepop();

            }
        });
        radiocenterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radiobtnb.setVisibility(View.VISIBLE);
                radiocenterbtn.setVisibility(View.GONE);

            }
        });

        /*rowView.setOnClickListener(new Viewjava.OnClickListener() {
            @Override
            public void onClick(Viewjava view) {
                Intent intent = new Intent(getContext(), Userview_task.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                ltcame = tcname[position];
                ltstatusname = tstatusname[position];
                lDtapntdate = tapntdate[position];
                lStcmpltdate = tcmpltdate[position];
                ltapnttype = tapnttype[position];

                Log.d("Location" ," ltcame1111 :" + ltcame);
                Log.d("Location" ," ltstatusname2222 :" + ltstatusname);
                Log.d("Location" ," lDtapntdate33333 :" + lDtapntdate);
                Log.d("Location" ," lStcmpltdate44444 :" + lStcmpltdate);
                Log.d("Location" ," ltapnttype55555 :" + ltapnttype);

            }
        });*/
        return rowView;
    }

    public void updatepop(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogLayout = inflater.inflate(R.layout.view_update_popup,
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
        Button update = (Button) dialogLayout.findViewById(R.id.updatebtn);
        final EditText barcodeE = (EditText) dialogLayout.findViewById(R.id.barcodedtaE);
        final Spinner companeyE = (Spinner) dialogLayout.findViewById(R.id.companeyE);
        final Spinner commadityE = (Spinner) dialogLayout.findViewById(R.id.commandityE);
        final EditText timeE = (EditText) dialogLayout.findViewById(R.id.timeE);
        final EditText dateE = (EditText) dialogLayout.findViewById(R.id.dateE);
        final EditText quantityE = (EditText) dialogLayout.findViewById(R.id.quantityE);

        barcodeE.setText(barcodedata.get(posval));
       // companeyE.setText(companey.get(posval));
        //commadityE.setText(commadity.get(posval));
        timeE.setText(time.get(posval));
        dateE.setText(date.get(posval));
        quantityE.setText(quantity.get(posval));
         id= Integer.parseInt(idL.get(posval));
        Log.d("log","id value"+id);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter companyadapter = new ArrayAdapter(context,R.layout.spinner_item,R.id.spinnertxt,companyS);
        companyadapter.setDropDownViewResource(R.layout.spinner_item);
        //Setting the ArrayAdapter data on the Spinner
        companeyE.setAdapter(companyadapter);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter comandityadapter = new ArrayAdapter(context,R.layout.spinner_item,R.id.spinnertxt,commandtyS);
        comandityadapter.setDropDownViewResource(R.layout.spinner_item);
        //Setting the ArrayAdapter data on the Spinner
        commadityE.setAdapter(comandityadapter);

      /*  //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter qualityadapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item,qualityS);
        qualityadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        quantityE.setAdapter(qualityadapter);
*/


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String barcodeU=barcodeE.getText().toString();
                String timeU=timeE.getText().toString();
                String dateU=dateE.getText().toString();
                String companyU = companeyE.getSelectedItem().toString();
                String commadityU = commadityE.getSelectedItem().toString();
                String quantityU = quantityE.getText().toString();


                boolean isUpdate = myDb.updateData(id,barcodeU,companyU,commadityU,timeU,dateU,quantityU);

                if(isUpdate == true) {
                    Toast.makeText(context, "Data Update", Toast.LENGTH_LONG).show();

                   // dialog.dismiss();
                    context.startActivity(new Intent(context, Viewjava.class));


                    /*Intent intent = new Intent(context, Viewjava.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/
                }
                else
                    Toast.makeText(context,"Data not Updated",Toast.LENGTH_LONG).show();

            }
        });

        dialog.show();

    }
}
