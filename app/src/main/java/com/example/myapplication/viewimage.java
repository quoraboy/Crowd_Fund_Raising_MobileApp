package com.example.myapplication;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewimage extends Fragment {


   private RecyclerView mRecycleView;
    private image_adaptor mAdaptor;
    private ProgressBar progressBar;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private int code =101;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_viewimage, container, false);

      mRecycleView=v.findViewById(R.id.recycler_view);
      mRecycleView.setHasFixedSize(true);
      mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
       progressBar=v.findViewById(R.id.uploadprogress);
      mUploads=new ArrayList <>();
       mDatabaseRef= FirebaseDatabase.getInstance().getReference("Upload");
       mDatabaseRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Iterable<DataSnapshot> mChildren = dataSnapshot.getChildren();

               for(DataSnapshot postSnapshot :mChildren)
               {

                   String ph=postSnapshot.getKey();
                   //                   Toast.makeText(getActivity(),postSnapshot.child(postSnapshot.getKey()).child("imageUri").getValue().toString(), Toast.LENGTH_SHORT).show();
              for(DataSnapshot post : postSnapshot.getChildren())
           {
               Upload upload =new Upload();

    upload.setImageUri(post.child("imageUri").getValue().toString());
    upload.setPhoneno(ph);
    upload.setMessage(post.child("message").getValue().toString());
    upload.setProjectname(post.child("projectname").getValue().toString());
    upload.setPrice(post.child("price").getValue().toString());
     upload.setAddress(post.child("address").getValue().toString());
     upload.setEn(post.child("en").getValue().toString());
    //                   Upload upload= postSnapshot.getValue(Upload.class);

          mUploads.add(upload);

       }
               }

//               mAdaptor =new com.example.bouncehackathon.image_adaptor(getActivity(),mUploads);
              mAdaptor=new image_adaptor(getActivity(),mUploads);
               mRecycleView.setAdapter(mAdaptor);
//               progressBar.setVisibility(View.INVISIBLE);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
            progressBar.setVisibility(View.INVISIBLE);
           }
       });
        return  v;
    }

}
