package com.example.myapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


import static android.app.Activity.RESULT_OK;


public class rentit extends Fragment {




  public static Uri uriprofileimage;
  private Button chooseImage;
private ImageView imagePreview;
private Button btnuploadImage;
private TextView viewImageofcar;
EditText odomenter,edtstart,edtend;
EditText vehiclePrice;
EditText locationOfVeicle;

private StorageTask mUploadtask;
private ProgressBar uploadProgress;
String id;

    private StorageReference mstoreref;
    DatabaseReference mDatabase;


    private int requestcode=12;
   public rentit() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.rentit,container,false);
        Bundle args = getArguments();
        id = args.getString("id");
           chooseImage=v.findViewById(R.id.chooseImage);
           edtend=v.findViewById(R.id.edtend);
           edtstart = v.findViewById(R.id.edtstart);
           imagePreview=v.findViewById(R.id.Imagepreview);
           btnuploadImage=v.findViewById(R.id.btnuploadImage);
           viewImageofcar=v.findViewById(R.id.viewImageofcar);
        odomenter=v.findViewById(R.id.odometer);
       locationOfVeicle=v.findViewById(R.id.locationofVehicle);
       vehiclePrice=v.findViewById(R.id.vehicleprice);
   uploadProgress=v.findViewById(R.id.uploadprogress);

   imagePreview.setImageDrawable(getResources().getDrawable(R.drawable.photo));


           mstoreref= FirebaseStorage.getInstance().getReference("Upload");


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Upload");

        viewImageofcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_Container,new viewimage()).addToBackStack(null).commit();

                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.screen_area,new viewimage());
                ft.commit();



            }
        });



           btnuploadImage.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   uploadImageToFirebaseStorage();
               }
           });

           chooseImage.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Showimagechooser();

               }
           });

        return v;
    }



    private void Showimagechooser() {

        Intent intent=new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent,requestcode);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //Toast.makeText(this, "OnactivityResult", Toast.LENGTH_SHORT).show();

        if(requestCode==12 && resultCode==RESULT_OK &&
                data!=null && data.getData()!=null)
        {

            uriprofileimage=data.getData();

            imagePreview.setImageURI(uriprofileimage);

            try
            {

                //      Toast.makeText(this, "try", Toast.LENGTH_SHORT).show();

                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uriprofileimage);

                imagePreview.setImageBitmap(bitmap);

                //uploadImageToFirebaseStorage();
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private String getFileExtention(Uri uri)
    {

        ContentResolver CR=getActivity().getContentResolver();

        MimeTypeMap mine=MimeTypeMap.getSingleton();

        return  mine.getExtensionFromMimeType(CR.getType(uri));
    }




    private void uploadImageToFirebaseStorage() {

        if(uriprofileimage!=null)

        {

            //        Toast.makeText(this, "UploadImageToFirebase", Toast.LENGTH_SHORT).show();


            mstoreref=mstoreref.child(System.currentTimeMillis()+"."+getFileExtention(uriprofileimage));


            mstoreref.putFile(uriprofileimage).addOnSuccessListener(new OnSuccessListener <UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Handler handler=new Handler();
//                    handler.postDelayed();

                    mstoreref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener <Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String st=uri.toString();
                            String[] imagearray = {st};
                            String start = edtstart.getText().toString();
                            String end = edtend.getText().toString();
                            String[] startarr = start.split("-");
                            String[] endarr = end.split("-");
                            Upload upload=new Upload(odomenter.getText().toString(),vehiclePrice.getText().toString(), locationOfVeicle.getText().toString(),st);

                            String uploadID=mDatabase.push().getKey();
                            mDatabase.child(uploadID).setValue(upload);
                          imagePreview.setImageResource(R.drawable.photo);
                          odomenter.setText("");
                          locationOfVeicle.setText("");
                          vehiclePrice.setText("");
                        }
                    });


//                    mDatabase.child("Odometer").setValue();
//                    mDatabase.child("Price").setValue();
//                    mDatabase.child("Location").setValue();
//                    mDatabase.child("ImageUri").setValue();

                    Toast.makeText(getActivity(), "Uploaded successful", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener <UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                     double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                     uploadProgress.setProgress((int)progress);
                }
            });
        }
        else{
            Toast.makeText(getActivity(), "Please select an Image", Toast.LENGTH_SHORT).show();
        }

    }




}
