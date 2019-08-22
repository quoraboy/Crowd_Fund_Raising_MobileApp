package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class image_adaptor extends RecyclerView.Adapter< image_adaptor.ImageViewHolder> {

    private Context mcontext;
    private List<Upload> mUploads;
    private int index;

    public image_adaptor(Context mcontext, List <Upload> mUploads) {
        this.mcontext = mcontext;
        this.mUploads = mUploads;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
View v= LayoutInflater.from(mcontext).inflate(R.layout.image_item,viewGroup,false);
return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder imageViewHolder, final int i) {
     Upload uploadcur=mUploads.get(i);
     imageViewHolder.view_odometer.setText(uploadcur.getProjectname());
     imageViewHolder.view_price.setText(uploadcur.getMessage());
     imageViewHolder.view_location.setText(uploadcur.getPrice());
     imageViewHolder.phoneno.setText(uploadcur.getPhoneno());
        Picasso.with(mcontext)
                .load(uploadcur.getImageUri())
                .placeholder(R.drawable.photo)
                .fit()
                .into(imageViewHolder.imageView);

        index = i;

        imageViewHolder.Pay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            index = imageViewHolder.getAdapterPosition();
            String mProfPhone = mUploads.get(i).getPhoneno();

            Intent intent =mcontext.getPackageManager().getLaunchIntentForPackage("com.");
//            intent.putExtra("mProfNum",mProfPhone);

            try {
            mcontext.startActivity(intent);
            }
            catch (AndroidRuntimeException e){
            Log.e("Error",e.toString());
            }
            }
           }
        );


    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
       ImageView imageView;
       TextView view_odometer;
       TextView view_location;
       TextView view_price;
       TextView phoneno;
       Button Pay;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

          imageView=itemView.findViewById(R.id.image_View);
          view_odometer=itemView.findViewById(R.id.view_odomenter);
          view_location=itemView.findViewById(R.id.view_location);
          view_price=itemView.findViewById(R.id.view_price);
          phoneno=itemView.findViewById(R.id.phoneno);
         Pay =itemView.findViewById(R.id.paynow);

        }
    }
}
