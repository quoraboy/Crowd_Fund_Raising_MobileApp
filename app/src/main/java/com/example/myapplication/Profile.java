package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Profile extends Fragment {
   EditText name;
   EditText age;
   EditText organisation;
   EditText qualification;
   Button submit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_profile,container,false);
          name=v.findViewById(R.id.name);
          age=v.findViewById(R.id.age);
          organisation=v.findViewById(R.id.organisation);
          qualification=v.findViewById(R.id.qualification);
          submit=v.findViewById(R.id.Save);


          submit.setText("Save Information");


    return  v;
    }
}
