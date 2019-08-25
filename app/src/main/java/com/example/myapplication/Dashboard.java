package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends Fragment {
    SharedPreferences sharedPreferences ;
    DatabaseReference mdataref;
   String phone;
    private List <Upload> mUploads;

    private RecyclerView mRecycleView1;
    private dashboardadapter mAdaptor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_dashboard,container,false);
        sharedPreferences = getActivity().getSharedPreferences("num", Context.MODE_PRIVATE);
        phone=sharedPreferences.getString("Phone","121");     //Default value get displayed when no data is entered in main activity
        mUploads=new ArrayList <>();
        mRecycleView1=v.findViewById(R.id.recycler_view2);
        mRecycleView1.setHasFixedSize(true);
        mRecycleView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        mdataref= FirebaseDatabase.getInstance().getReference("Upload").child(phone);
       mdataref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for(DataSnapshot post:dataSnapshot.getChildren())
              {
                    Upload upload=new Upload();
                    upload.key=post.getKey();
                       upload.setPhoneno(phone);
                        upload.setImageUri(post.child("imageUri").getValue().toString());
                        upload.setProjectname(post.child("projectname").getValue().toString());
                        upload.setPrice(post.child("price").getValue().toString());
                        upload.setMessage(post.child("message").getValue().toString());

                  mUploads.add(upload);

              }
               mAdaptor=new dashboardadapter(getActivity(),mUploads);
               mRecycleView1.setAdapter(mAdaptor);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });







        return v;
    }
}
