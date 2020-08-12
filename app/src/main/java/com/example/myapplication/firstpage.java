package com.example.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Timer;

public class firstpage extends AppCompatActivity {
    Timer timer;
    TextView go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

  go=(TextView)findViewById(R.id.go);

        Typeface myTypeface = Typeface.create(ResourcesCompat.
                getFont(getApplicationContext(), R.font.madame),Typeface.BOLD);

       go.setTypeface(myTypeface);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                 Intent mainIntent = new Intent(firstpage.this, NavigationDrawer.class);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        }, 3000);

    }

}
