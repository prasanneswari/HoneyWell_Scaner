package com.mahesh.apple.honeywellscanner;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Adapter_Generate extends ArrayAdapter<String> {

    private Context context;
  //  private List<String> barcodedata;
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

    public Adapter_Generate(Context context, List<String> companeyS, List<String> commadityS, List<String> timeS,List<String> dateS, List<String> quantityS) {

        super(context, R.layout.activity_generate, companeyS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
      //  this.barcodedata = barcodedataS;
        this.companey = companeyS;
        this.commadity = commadityS;
        this.time = timeS;
        this.date = dateS;
        this.quantity = quantityS;

    }
    @Override
    public android.view.View getView(final int position, android.view.View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_adapter__generate, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);
        //Assigning IDs from xml
        posval=position;
     //   barcodedataT = (TextView) rowView.findViewById(R.id.barcodedta);
        companeyT = (TextView) rowView.findViewById(R.id.companeyA);
        commadityT = (TextView) rowView.findViewById(R.id.commandityA);
        timeT = (TextView) rowView.findViewById(R.id.timeA);
        dateT = (TextView) rowView.findViewById(R.id.dateA);
        quantityT = (TextView) rowView.findViewById(R.id.quantityA);

        try {

            //Assigning values from array to individual layouts in list view
          //  barcodedataT.setText(barcodedata.get(position));
            companeyT.setText(companey.get(position));
            commadityT.setText(commadity.get(position));
            timeT.setText(time.get(position));
            dateT.setText(date.get(position));
            quantityT.setText(quantity.get(position));

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

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
}
