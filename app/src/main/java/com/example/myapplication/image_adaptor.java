package com.example.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        Picasso.with(mcontext)
                .load(uploadcur.getImageUri())
                .placeholder(R.drawable.photo)
                .fit()
                .into(imageViewHolder.imageView);

        index = i;

        imageViewHolder.layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            index = imageViewHolder.getAdapterPosition();
            String mProfPhone = mUploads.get(i).getPhoneno();
          copytoclipboard(mProfPhone);
//            Intent intent =mcontext.getPackageManager().getLaunchIntentForPackage("www.paytm.com");
//intent.putExtra("mProfNum",mProfPhone);
                CustomDialogClass cdd=new CustomDialogClass(view.getContext(),mUploads,i);
                cdd.show();
            }
           }
        );


    }



    public void InternetAlert(List<Upload> mUp, int i)
    {

           String p= mUp.get(i).getPhoneno();
           String m=mUp.get(i).getMessage();
           String pro=mUp.get(i).getProjectname();
           String price =mUp.get(i).getPrice();
            AlertDialog.Builder dialog = new AlertDialog.Builder(mcontext);
            dialog.setMessage(pro + "\n" + m + "\n" + p+ "\n" + price );
            dialog.setPositiveButton("Pay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""));
                    try {
                        mcontext.startActivity(intent);
                    }
                    catch (AndroidRuntimeException e){
                        Log.e("Error",e.toString());
                    }
                }
            });
            dialog.setNegativeButton("I don't want to pay", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();




    }

    private void copytoclipboard(String text)
    {

        ClipboardManager clipboard = (ClipboardManager)
                mcontext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", text);
//                newUri(getContentResolver(),"URI",text);

        clipboard.setPrimaryClip(clip);

        Toast.makeText(mcontext, "Phone number copied to Clipboard", Toast.LENGTH_SHORT).show();


    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
      private ImageView imageView;
      private   TextView view_odometer;

       LinearLayout layout;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

          imageView=itemView.findViewById(R.id.image_View);
          view_odometer=itemView.findViewById(R.id.view_odomenter);
          layout=itemView.findViewById(R.id.layoutimage);

        }
    }
}
