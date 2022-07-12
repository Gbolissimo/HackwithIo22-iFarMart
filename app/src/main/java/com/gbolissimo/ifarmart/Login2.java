package com.gbolissimo.ifarmart;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login2 extends AppCompatActivity {

    //UI controls
    private TextView mSignin;
    private EditText mEmail, mPassword;
    private ProgressDialog mProgress;
    private TextView mForgotPassword, mSignup;
    private Toolbar toolbar;
    //Firebase Auth class
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        FirebaseAuth.getInstance().signOut();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Login");

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
//        tv.setTextColor(Color.parseColor("#000000"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/product.ttf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);


        //UI components
        mSignin = (TextView) findViewById(R.id.signinn);
        mSignup = (TextView) findViewById(R.id.loginsu);
        mForgotPassword = (TextView) findViewById(R.id.loginfp);
        mEmail = (EditText) findViewById(R.id.emailn);
        mPassword = (EditText) findViewById(R.id.passwordn);

        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);

        final Intent intent=getIntent();
        final String semail = intent.getStringExtra("semail");
        final String spassword = intent.getStringExtra("spassword");

        mEmail.setText(semail);
        mPassword.setText(spassword);

        Signin();
        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signin();

            }
        });

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent signup = new Intent(Login.this,SignUpActivity.class);
                Intent signup = new Intent(Login2.this,AddActivity.class);
                startActivity(signup);

            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent forgot = new Intent(Login2.this,ResetPassword.class);
                startActivity(forgot);

            }
        });


    }

    private void Signin() {

        //Getting Email and password from users
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(Login2.this,"Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(Login2.this,"Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        //If email and password provided display progress dialog
        mProgress.setMessage("Please wait.....");
        mProgress.show();


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Login2.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(Login2.this,"Successfully signed in", Toast.LENGTH_LONG).show();

              //      putPref("Cover", "", getApplicationContext());
               //     putPref("Title", "", getApplicationContext());

                    Intent intent = new Intent(Login2.this, Main.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // LoginActivity is a New Task
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // The old task when coming back to this activity should be cleared so we cannot come back to it.
                    startActivity(intent);
                }else{
                    Toast.makeText(Login2.this,"Error! Please check your email, password or internet connection.", Toast.LENGTH_LONG).show();
                    mProgress.cancel();
                }

            }
        });

    }
    public static String getPref(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    public static void putPref(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

}
