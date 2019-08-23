package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends Fragment {
   EditText name;
   EditText age;
   EditText organisation;
   EditText qualification;
   Button submit;
   DatabaseReference mdatabase;
     SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
   String t="0";

    String phone;
    profileinfo profileinfo;

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
          sharedPreferences = getActivity().getSharedPreferences("num", Context.MODE_PRIVATE);
        phone=sharedPreferences.getString("Phone","121");     //Default value get displayed when no data is entered in main activity
          mdatabase =FirebaseDatabase.getInstance().getReference();
          submit.setText("Save Information");
         sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
         t=sharedPreferences.getString("t","0");
       name.setText(sharedPreferences.getString("name",""));
       age.setText(sharedPreferences.getString("age",""));
       organisation.setText(sharedPreferences.getString("organisation",""));
       qualification.setText(sharedPreferences.getString("qualification",""));

       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

             if(t=="0") {
                 profileinfo = new profileinfo(name.getText().toString().trim(), age.getText().toString().trim(), qualification.getText().toString().trim(), organisation.getText().toString().trim());
                 mdatabase.child(phone).setValue(profileinfo);
                 submit.setText("Edit Saved Information");
                 sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);

                 editor = sharedPreferences.edit();
                 t = "1";
                 editor.putString("name", profileinfo.getName());
                 editor.putString("age", profileinfo.getAge());
                 editor.putString("organisation", profileinfo.getOrganization());
                 editor.putString("qualification", profileinfo.getQualification());
                 editor.putString("t", t);
                 name.setEnabled(false);
                 age.setEnabled(false);
                 organisation.setEnabled(false);
                 qualification.setEnabled(false);

                 editor.commit();
             }
             else if(t=="1")
             {

                         name.setEnabled(true);
                         age.setEnabled(true);
                         organisation.setEnabled(true);
                         qualification.setEnabled(true);
                 sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
                 editor = sharedPreferences.edit();
                         t="0";
                 editor.putString("t", t);
                 editor.commit();
                         submit.setText("Saved Information");


             }
           }
       });





        return  v;
    }
}
