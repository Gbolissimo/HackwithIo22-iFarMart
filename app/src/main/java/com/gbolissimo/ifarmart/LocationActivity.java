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
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class LocationActivity extends AppCompatActivity {

    private TextView ibadan, oyo, ogbomoso, iseyin, saki;
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

     //   requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_location);

   //     final Intent intent=getIntent();
     //   final String content= intent.getStringExtra("content");
     //   final String title= intent.getStringExtra("title");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Select Location");

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
        final Intent intent=getIntent();
        final String rtype = intent.getStringExtra("type");
    //    final String spassword = intent.getStringExtra("spassword");

        ibadan = (TextView) findViewById(R.id.ibadan);
        ibadan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putPref("QueryType", rtype, getApplicationContext());
                putPref("QueryLocation", "ibadan", getApplicationContext());
                Intent newPostIntentzZ = new Intent(LocationActivity.this, ProductActivity.class);
                newPostIntentzZ.putExtra("type", rtype);
                newPostIntentzZ.putExtra("location", "ibadan");
                startActivity(newPostIntentzZ);

            }
        });
        oyo = (TextView) findViewById(R.id.oyo);
        oyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putPref("QueryType", rtype, getApplicationContext());
                putPref("QueryLocation", "oyo", getApplicationContext());
                Intent newPostIntentzZ = new Intent(LocationActivity.this, ProductActivity.class);
                newPostIntentzZ.putExtra("type", rtype);
                newPostIntentzZ.putExtra("location", "oyo");
                startActivity(newPostIntentzZ);

            }
        });
        ogbomoso = (TextView) findViewById(R.id.ogbomoso);
        ogbomoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putPref("QueryType", rtype, getApplicationContext());
                putPref("QueryLocation", "ogbomoso", getApplicationContext());
                Intent newPostIntentzZ = new Intent(LocationActivity.this, ProductActivity.class);
                newPostIntentzZ.putExtra("type", rtype);
                newPostIntentzZ.putExtra("location", "ogbomoso");
                startActivity(newPostIntentzZ);

            }
        });
        iseyin = (TextView) findViewById(R.id.iseyin);
        iseyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putPref("QueryType", rtype, getApplicationContext());
                putPref("QueryLocation", "iseyin", getApplicationContext());
                Intent newPostIntentzZ = new Intent(LocationActivity.this, ProductActivity.class);
                newPostIntentzZ.putExtra("type", rtype);
                newPostIntentzZ.putExtra("location", "iseyin");
                startActivity(newPostIntentzZ);

            }
        });
        saki = (TextView) findViewById(R.id.saki);
        saki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putPref("QueryType", rtype, getApplicationContext());
                putPref("QueryLocation", "saki", getApplicationContext());
                Intent newPostIntentzZ = new Intent(LocationActivity.this, ProductActivity.class);
                newPostIntentzZ.putExtra("type", rtype);
                newPostIntentzZ.putExtra("location", "saki");
                startActivity(newPostIntentzZ);

            }
        });





//onBackPressed();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
        switch (item.getItemId()) {




        }

        return super.onOptionsItemSelected(item);
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
