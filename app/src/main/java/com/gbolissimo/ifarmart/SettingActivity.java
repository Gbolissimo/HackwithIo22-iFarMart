package com.gbolissimo.ifarmart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;


public class SettingActivity extends AppCompatActivity {

    public TextView privacy, dkm, appinfo, share;
    public ImageView login;
    private Toolbar toolbar;

    public Switch switch1;
    Typeface tb, sg;
    public String contactt;
    public String blcmail;
    public String namefull;
    public String number1;
    public String phonyidd;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.setting_activity);

   //     final Intent intent=getIntent();
     //   final String content= intent.getStringExtra("content");
     //   final String title= intent.getStringExtra("title");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Settings");

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if (isDarkModeOn) {
            tv.setTextColor(Color.parseColor("#fcfcfc"));
        }
        else {
            tv.setTextColor(Color.parseColor("#000000"));
        }


        tv.setTextSize(20);
     //   tv.setTextColor(Color.parseColor("#000000"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/product.ttf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);

     //   cont1.setTypeface(tf);

        //  final LinearLayout lc1=(LinearLayout)findViewById(R.id.lc1);
        switch1 = (Switch) findViewById(R.id.switch1);
        privacy = (TextView) findViewById(R.id.privacy);
        dkm = (TextView) findViewById(R.id.dkm);
        appinfo = (TextView) findViewById(R.id.app_info);
        share = (TextView) findViewById(R.id.share);
        login = (ImageView) findViewById(R.id.adm_login);


        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newPostIntent = new Intent(SettingActivity.this, PrivacyPolicy.class);
                startActivity(newPostIntent);
            }
        });

        appinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newPostIntent = new Intent(SettingActivity.this, AppInfo.class);
                startActivity(newPostIntent);
             //   Toast.makeText(SettingActivity.this, "App Info",
               //         Toast.LENGTH_SHORT).show();

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String sharebody = "Download iFarMart App";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "iFarMart");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, sharebody);
                startActivity(Intent.createChooser(sharingIntent,"Share via"));

            }
        });

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( getPref("NightMode", getApplicationContext()).trim().equals("Yes")){

                    switch1.setChecked(false);
                    putPref("NightMode", "No", getApplicationContext());
                    Toast.makeText(SettingActivity.this, "Night Mode On!",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    switch1.setChecked(true);
                    putPref("NightMode", "Yes", getApplicationContext());
                    Toast.makeText(SettingActivity.this, "Night Mode Off!",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            dkm.setText("Disable Dark Mode");
        }
        else {

            dkm
                    .setText(
                            "Enable Dark Mode");
        }

        dkm.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {
                        // When user taps the enable/disable
                        // dark mode button
                        if (isDarkModeOn) {

                            // if dark mode is on it
                            // will turn it off
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_NO);
                            // it will set isDarkModeOn
                            // boolean to false
                            editor.putBoolean(
                                    "isDarkModeOn", false);
                            editor.apply();

                            // change text of Button
                            dkm.setText(
                                    "Enable Dark Mode");
                        }
                        else {

                            // if dark mode is off
                            // it will turn it on
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_YES);

                            // it will set isDarkModeOn
                            // boolean to true
                            editor.putBoolean(
                                    "isDarkModeOn", true);
                            editor.apply();

                            // change text of Button
                            dkm.setText(
                                    "Disable Dark Mode");
                        }
                    }
                });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


//        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
  //          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
    //        }
     //   });
     //   getPref("HubCover", getApplicationContext())
     //   putPref("UserName", doc.get("name").toString(), getApplicationContext());
       // putPref("UserPix", doc.get("pix").toString(), getApplicationContext());

    }












    public void Back(){
        super.onBackPressed();
    }

    public static void putPref(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getPref(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

}
