package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class dashboardadapter extends RecyclerView.Adapter <dashboardadapter.ImageViewHolder> {

    private Context mcontext;
    private List <Upload> mUploads;
    private int index;

    public dashboardadapter(Context mcontext, List <Upload> mUploads) {
        this.mcontext = mcontext;
        this.mUploads=mUploads;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mcontext).inflate(R.layout.dashboardlist,viewGroup,false);

   return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder imageViewHolder, final int i) {
        Upload uploadcur=mUploads.get(i);
        imageViewHolder.view_odometer1.setText(uploadcur.getProjectname());

        Picasso.with(mcontext)
                .load(uploadcur.getImageUri())
                .placeholder(R.drawable.photo)
                .fit()
                .into(imageViewHolder.imageView1);


        imageViewHolder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                index = imageViewHolder.getAdapterPosition();
                String mProfPhone = mUploads.get(i).getPhoneno();
                DatabaseReference mdta= FirebaseDatabase.getInstance().getReference("Upload").child(mProfPhone).child(mUploads.get(i).key);

               mdta.removeValue();


            }
        }
        );


    }

    @Override
    public int getItemCount() {
        return mUploads.size();

    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
      private   ImageView imageView1;
       private TextView view_odometer1;
  private Button delete;
    private Button Edit;
//        LinearLayout layout;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView1=itemView.findViewById(R.id.img);
            view_odometer1=itemView.findViewById(R.id.view_odomenter1);
//            layout=itemView.findViewById(R.id.layoutimage);

            delete=itemView.findViewById(R.id.del);
            Edit=itemView.findViewById(R.id.edt);

        }
    }
}
