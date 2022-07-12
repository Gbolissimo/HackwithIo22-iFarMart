package com.gbolissimo.ifarmart;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class PrivacyPolicy extends AppCompatActivity {

    private ImageView whatsapp;
    private Toolbar toolbar;

    public ImageView vimage;
    Typeface tb, sg;
    public String contactt;
    public String blcmail;
    public String namefull;
    public String number1;
    public String phonyidd;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.privacypolicy);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Privacy Policy");

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


    }


    public void Back(){
        super.onBackPressed();
    }
}
