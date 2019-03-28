package com.mahesh.apple.honeywellscanner;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter_Addupdate extends ArrayAdapter<String> {
    private Context context;
    private List<String> barcodedata;
    private List<String> charecterset;
    private List<String> codeid;
    private List<String> aimid;
    private List<String> timestamp;

    public static String lcusname;
    public static String lfullname;
    public static String lcuscontact;
    public static String lapntdate;
    public static String lvisitdate;
    static int posval;
    public static String lnotes;
    TextView barcodedataT,charectersetT,codeidT,aimidT,timestampT;

    public Adapter_Addupdate(Context context, List<String> barcodedataS, List<String> charectersetS, List<String> codeidS, List<String> aimidS, List<String> timestampS) {

        super(context, R.layout.activity_barcode, barcodedataS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.barcodedata = barcodedataS;
        this.charecterset = charectersetS;
        this.codeid = codeidS;
        this.aimid = aimidS;
        this.timestamp = timestampS;

    }
    @Override
    public android.view.View getView(final int position, android.view.View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_adapter__addupdate, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);
        //Assigning IDs from xml
        posval=position;
        barcodedataT = (TextView) rowView.findViewById(R.id.barcodedta);
        charectersetT = (TextView) rowView.findViewById(R.id.charecterset);
        codeidT = (TextView) rowView.findViewById(R.id.codeid);
        aimidT = (TextView) rowView.findViewById(R.id.aimid);
        timestampT = (TextView) rowView.findViewById(R.id.timestamp);
        try {

            //Assigning values from array to individual layouts in list view
            barcodedataT.setText(barcodedata.get(position));
            charectersetT.setText(charecterset.get(position));
            codeidT.setText(codeid.get(position));
            aimidT.setText(aimid.get(position));
            timestampT.setText(timestamp.get(position));

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
