package com.gbolissimo.ifarmart;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.himanshusoni.edittextspinner.EditTextSpinner;

public class AddActivity extends AppCompatActivity {

    private static final String TAG=AddActivity.class.getSimpleName();
    private EditText emmailsu;
    public EditText passwordsu;
    public EditText namesu;
    private EditText numbersu;
    private TextView signupsu;
    private ProgressDialog mProgress;
    private Toolbar toolbar;

    FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Sign up");

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if (isDarkModeOn) {
            tv.setTextColor(Color.parseColor("#fcfcfc"));
        }
        else {
            tv.setTextColor(Color.parseColor("#000000"));
        }


        tv.setTextSize(21);
      //  tv.setTextColor(Color.parseColor("#000000"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/product.ttf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);



        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        // Initialize
        emmailsu=(EditText)findViewById(R.id.emailsu);
        passwordsu=(EditText)findViewById(R.id.passwordsu);
        namesu=(EditText)findViewById(R.id.namesu);
       numbersu=(EditText)findViewById(R.id.numbersu);

        signupsu=(TextView) findViewById(R.id.signupsu);

        mProgress = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        // Register user click listener
        signupsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get name, email and password
                final String Name = namesu.getText().toString();
                final String Email = emmailsu.getText().toString();
                final String Password = passwordsu.getText().toString();

                final String Number = numbersu.getText().toString();
                final String uEmail = emmailsu.getText().toString().trim().toLowerCase().replace(".","-");


                if (isConnected()) {
                    // Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();



                if (Name.isEmpty() || Email.isEmpty() || Password.isEmpty()) {
                    // Show message when field is empty
                    showErrorMessageToUser("Field cannot be empty");

                } else {

                    //If email and password provided display progress dialog
                         mProgress.setMessage("Please wait.....");
                       mProgress.show();

                    final String email =emmailsu.getText().toString().trim();
                    final String password =passwordsu.getText().toString().trim();
                    //creating a new user
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(AddActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //checking if success
                                    if (task.isSuccessful()) {
                                        //display some message here
                                        final Long timestamp = System.currentTimeMillis();
                                        String tym = timestamp.toString();


                                        Map < String, Object > newContact = new HashMap < > ();
                                        newContact.put("id", uEmail);
                                        newContact.put("pix", "default");
                                        newContact.put("name", Name);
                                        newContact.put("email", email.toLowerCase());
                                        newContact.put("password", password);
                                        newContact.put("number", Number);
                                        newContact.put("number2", "");
                                        newContact.put("branch", "");
                                        newContact.put("activationcode", "");
                                        newContact.put("activationstatus", "");
                                        newContact.put("expirydate", "");
                                        newContact.put("extra1", tym);
                                        newContact.put("extra2", email.toLowerCase().replace(".","-").trim());
                                        newContact.put("extra3", "");
                                        newContact.put("extra4", "");
                                        db.collection("Users").document(uEmail).set(newContact)
                                                .addOnSuccessListener(new OnSuccessListener< Void >() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(AddActivity.this, "Registration Successful",
                                                                Toast.LENGTH_SHORT).show();


                                                        mProgress.cancel();
                                                      //  Signin();
                                                        Intent newPostIntentqw = new Intent(AddActivity.this, Login2.class);
                                                        newPostIntentqw.putExtra("semail", email);
                                                        newPostIntentqw.putExtra("spassword", password);
                                                        startActivity(newPostIntentqw);

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(AddActivity.this, "ERROR" + e.toString(),
                                                                Toast.LENGTH_SHORT).show();
                                                        Log.d("TAG", e.toString());
                                                    }
                                                });

                                    } else {
                                        //display some message here
                                        Toast.makeText(AddActivity.this, "Registration Error! Please check your network connection or try another email address.", Toast.LENGTH_LONG).show();
                                        mProgress.cancel();
                                    }
                               //     progressDialog.dismiss();


                                }
                            });
              }
                } else {
                      Toast.makeText(getApplicationContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
                //    StyleableToast.makeText(AddActivity.this, "No Internet Connection!", R.style.styleabletoast_2).show();
                }
            }

                });}


    private void showErrorMessageToUser(String errorMessage){
        //Create an AlertDialog to show error message
        AlertDialog.Builder builder=new AlertDialog.Builder(AddActivity.this);
        builder.setMessage(errorMessage)
                .setTitle("Field cannot be empty")
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog=builder.create();
        dialog.show();


    }

    private void Signin() {

        //Getting Email and password from users
        String email = emmailsu.getText().toString().trim();
        String password = passwordsu.getText().toString().trim();


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(AddActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(AddActivity.this,"Successfully signed in", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(AddActivity.this, Main.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // LoginActivity is a New Task
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // The old task when coming back to this activity should be cleared so we cannot come back to it.
                    startActivity(intent);
                }else{
                    Toast.makeText(AddActivity.this,"Error! Please check your email, password or internet connection.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

}
