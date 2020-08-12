package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {
    private final static int SEND_SMS_PERMISSION_REQ=1;

    double lat;
    double lng;
    public Context c;
    public Dialog d;
    public Button yes, no,show;
    int i;
    List <Upload> mUp;
     TextView book;
    TextView message1;
    TextView name;
    TextView price;
    TextView phoneno;
    TextView address;
    TextView engine;
    public CustomDialogClass(Context a, List <Upload> mUp,int i) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.mUp=mUp;
        this.i=i;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialogbox);
        name=(TextView)findViewById(R.id.name1);
        message1=(TextView)findViewById(R.id.message1);
        price=(TextView)findViewById(R.id.price1);
        phoneno=(TextView)findViewById(R.id.phoneno1);
        address =(TextView)findViewById(R.id.address);
        engine=(TextView)findViewById(R.id.Engine);
        yes = (Button) findViewById(R.id.payy);
        no = (Button) findViewById(R.id.exit);
        show=(Button)findViewById(R.id.show);
        book=(Button)findViewById(R.id.book);
        book.setOnClickListener(this);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        show.setOnClickListener(this);
          name.setText(mUp.get(i).getProjectname());
          message1.setText(mUp.get(i).getMessage());
          price.setText(mUp.get(i).getPrice());
          phoneno.setText(mUp.get(i).getPhoneno());
           address.setText(mUp.get(i).getAddress());
        engine.setText(mUp.get(i).getEn());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payy:
//                c.finish();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://paytm.com"));
                try {
                    c.startActivity(intent);
                }
                catch (AndroidRuntimeException e){
                    Log.e("Error",e.toString());
                }
                break;
            case R.id.exit:
                dismiss();
                break;
            case R.id.book:
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + mUp.get(i).getPhoneno()));
                intent1.putExtra("sms_body", "I am Interested in your vehicle, will be contacting you soon.");
                c.startActivity(intent1);
                break;
            case R.id.show:

                Geocoder gc = new Geocoder(c);
                if(gc.isPresent()){
                    List<Address> list = null;
                    try {
                        list = gc.getFromLocationName(mUp.get(i).getAddress(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = list.get(0);
                 lat = address.getLatitude();
                    lng = address.getLongitude();
                }

                Uri gmmIntentUri = Uri.parse("geo:"+lat+","+String.valueOf(lng));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                c.startActivity(mapIntent);
                Toast.makeText(c, "Map is opening", Toast.LENGTH_SHORT).show();
                break;
        }
        dismiss();
    }
}