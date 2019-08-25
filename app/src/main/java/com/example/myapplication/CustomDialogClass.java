package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Context c;
    public Dialog d;
    public Button yes, no;
    int i;
    List <Upload> mUp;

    TextView message;
    TextView name;
    TextView price;
    TextView phoneno;

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
        message=(TextView)findViewById(R.id.message1);
        price=(TextView)findViewById(R.id.price1);
        phoneno=(TextView)findViewById(R.id.phoneno1);
        yes = (Button) findViewById(R.id.payy);
        no = (Button) findViewById(R.id.exit);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

          name.setText(mUp.get(i).getProjectname());
          message.setText(mUp.get(i).getMessage());
          price.setText(mUp.get(i).getPrice());
          phoneno.setText(mUp.get(i).getPhoneno());

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
            default:
                break;
        }
        dismiss();
    }
}