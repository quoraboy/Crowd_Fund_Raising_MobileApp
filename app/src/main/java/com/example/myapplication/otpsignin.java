package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;



public class otpsignin extends AppCompatActivity {


        private EditText phonetxt;

        private EditText codetxt;

        private Button btnOTP;

        private FirebaseAuth mAuth;

        private String TAG ="xyz";

        private int btntype=0;

        private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback;

        private String mVerificationId;

        private PhoneAuthProvider.ForceResendingToken mResendToken;

         public static String phonenumber ;

         public static String mDeviceNum;

    SharedPreferences sharedPref;

    SharedPreferences.Editor editor;

        @Override

        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_otpsignin);

            mAuth=FirebaseAuth.getInstance();


            phonetxt=(EditText) findViewById(R.id.phoneEditText);

            codetxt=(EditText) findViewById(R.id.codeEditText);

            btnOTP= (Button)   findViewById(R.id.btnotp);

            codetxt.setEnabled(false);

            btnOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(btntype==0)
                    {

                         phonenumber=phonetxt.getText().toString();

                        phonetxt.setEnabled(true);

                        codetxt.setEnabled(true);

                        PhoneAuthProvider.getInstance().

                                verifyPhoneNumber(phonenumber,60, TimeUnit.SECONDS,
                                        otpsignin.this,
                                        mcallback);
                    }
                    else
                        {

                            String verificationcode=codetxt.getText().toString(); //verificationcode contain the OTP

                            PhoneAuthCredential cridential=PhoneAuthProvider.getCredential(mVerificationId,verificationcode);

                            signInWithPhoneAuthCredential(cridential);
                    }
                }
            });

            mcallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override

                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    //When the code is send and the enter

                    // phone no. is in the phone in which app is runing this meathod is going to run


                    Toast.makeText(otpsignin.this,"Code send to your phone",Toast.LENGTH_SHORT).show();

                    signInWithPhoneAuthCredential(phoneAuthCredential);

                }

                @Override

                public void onVerificationFailed(FirebaseException e) {

                    //If sms didn't receive this gonna call

                    Toast.makeText(otpsignin.this,"Something went wrong",Toast.LENGTH_SHORT).show();

                }

                @Override

                public void onCodeSent(String verificationId,
                                       PhoneAuthProvider.ForceResendingToken token) {

                    //This meathod is call when ever the enter

                    // phone no. is in the other mobile other than the installed app phone no.

                    // The SMS verification code has been sent to the provided phone number, we

                    // now need to ask the user to enter the code and then construct a credential

                    // by combining the code with a verification ID.

                    Toast.makeText(otpsignin.this, verificationId, Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onCodeSent:" + verificationId);

                    Toast.makeText(otpsignin.this,"On code sent meathod",Toast.LENGTH_SHORT).show();

                    // Save verification ID and resending token so we can use them later

                    btntype=1;


                    mVerificationId = verificationId;

                    mResendToken = token;

                    btnOTP.setText("Verify code");

                    // ...
                }
            };
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

            mAuth.signInWithCredential(credential)

                .addOnCompleteListener(this, new OnCompleteListener <AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task <AuthResult> task) {

                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information

                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            mDeviceNum = phonenumber;

                            sharedPref = getSharedPreferences("num", Context.MODE_PRIVATE);

                            editor = sharedPref.edit();

                            editor.putString("Phone",phonenumber);

                            editor.commit();

                            Intent intent=new Intent(otpsignin.this, NavigationDrawer.class);

                            //intent.putExtra( "phoneno",);

                            startActivity(intent);
                            finish();


                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI

                            Toast.makeText(otpsignin.this,"ERROR",Toast.LENGTH_SHORT).show();

                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    int backButtonCount = 0;
    @Override
    public void onBackPressed() {
        backButtonCount++;
//        Toast.makeText(this,String.valueOf(backButtonCount) , Toast.LENGTH_SHORT).show();
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//            backButtonCount=0;
//        }
        if(backButtonCount > 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
//            Toast.makeText(this, "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
            backButtonCount=0;
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
//            backButtonCount++;
        }
//        else {
//            super.onBackPressed();
//        }
    }

}
