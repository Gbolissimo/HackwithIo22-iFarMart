package com.gbolissimo.ifarmart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.varunjohn1990.iosdialogs4android.IOSDialog;

import java.util.HashMap;
import java.util.Map;


public class ProductDetail extends AppCompatActivity {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Topics");

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView pname, pquantitiy, pfarmaddress, pprice, pseller, psellerc1, psellerc2, pselleremail, psellerv, ppt, callnow;
    private ImageView call1, call2, message1, message2, chat1, chat2, vimage, vimage2, sellerpix, pdimage, delete;

    public FirebaseFirestore dbb;
    public String id, pix;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_product);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        final String name = intent.getStringExtra("name");
        final String quantity = intent.getStringExtra("quantity");
        final String address = intent.getStringExtra("address");
        final String price = intent.getStringExtra("price");
        final String contact1 = intent.getStringExtra("contact1");
        final String contact2 = intent.getStringExtra("contact2");
        final String seller = intent.getStringExtra("seller");
        final String selleremail = intent.getStringExtra("selleremail");
        final String verification = intent.getStringExtra("verification");
        final String businessname = intent.getStringExtra("businessname");
        final String others1 = intent.getStringExtra("other1");
        final String others2 = intent.getStringExtra("others2");
        final String others3 = intent.getStringExtra("others3");
        final String others4 = intent.getStringExtra("others4");
        final String productpix = intent.getStringExtra("productpix");
        final String timestamp = intent.getStringExtra("timestamp");


        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText(name);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if (isDarkModeOn) {
            tv.setTextColor(Color.parseColor("#fcfcfc"));
        } else {
            tv.setTextColor(Color.parseColor("#000000"));
        }


        tv.setTextSize(20);
        //   tv.setTextColor(Color.parseColor("#000000"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/product.ttf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            pname = findViewById(R.id.pn);
            pquantitiy = findViewById(R.id.q);
            pfarmaddress = findViewById(R.id.fa);
            pprice = findViewById(R.id.p);
            pseller = findViewById(R.id.s);
            psellerc1 = findViewById(R.id.sc1);
            psellerc2 = findViewById(R.id.sc2);
            pselleremail = findViewById(R.id.se);
            psellerv = findViewById(R.id.sv);
            ppt = findViewById(R.id.pt);
            callnow = findViewById(R.id.callnow);

            call1 = findViewById(R.id.call1);
            call2 = findViewById(R.id.call2);
            message1 = findViewById(R.id.message1);
            message2 = findViewById(R.id.message2);
            chat1 = findViewById(R.id.chat1);
            chat2 = findViewById(R.id.chat2);
            vimage = findViewById(R.id.vimage);
            vimage2 = findViewById(R.id.vimage2);
            sellerpix = findViewById(R.id.seller_pix);
            delete = findViewById(R.id.delete);
//            pdimage = findViewById(R.id.pd_image);


//            ndtime.setText(tym);
            pname.setText(name);
            pquantitiy.setText(quantity);
            pfarmaddress.setText(address);
            pprice.setText(price);
            pseller.setText(seller);
            psellerc1.setText(contact1);
            psellerc2.setText(contact2);
            pselleremail.setText(selleremail);
            psellerv.setText(verification);
//            ppt.setText(timestamp);
            //          ppix.setText(productpix);

            setImageAvatar(getApplicationContext(), productpix);
            setImageAvatar2(getApplicationContext(), others2);

            if (verification.equals("Verified")) {
                vimage.setVisibility(View.VISIBLE);
                vimage2.setVisibility(View.GONE);
            } else {
                vimage.setVisibility(View.GONE);
                vimage2.setVisibility(View.VISIBLE);
            }

            if (DateUtils.getRelativeTimeSpanString(Long.parseLong((String) timestamp)).toString().equals("0 minutes ago")) {
                ppt.setText("Just now");
            } else {
                ppt.setText(DateUtils.getRelativeTimeSpanString(Long.parseLong((String) timestamp)));
            }


            callnow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + contact1));
                    startActivity(callIntent);

                }
            });

            if (user.getEmail().toLowerCase().trim().toString().equals(selleremail.toLowerCase().trim().toString()))
            {
                delete.setVisibility(View.VISIBLE);
            }else  if (getPref("UserStatus", getApplicationContext()).toString().toLowerCase().trim().equals("admin")){
                delete.setVisibility(View.VISIBLE);
            }
            else  {
                delete.setVisibility(View.GONE);
            }



            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new IOSDialog.Builder(getApplicationContext())
                            .title("Delete Post")
                            .message("Are you sure you want to delete this post?")
                            .positiveButtonText("DELETE")
                            .negativeButtonText("CANCEL")
                            .positiveClickListener(new IOSDialog.Listener() {
                                @Override
                                public void onClick(IOSDialog iosDialog) {

                                    db.collection("Products").document(id).delete();
                                    Toast.makeText(ProductDetail.this, "Product Deleted! Refresh page to see changes",
                                            Toast.LENGTH_SHORT).show();

                                    iosDialog.dismiss();
                                    onBackPressed();



                                }
                            }).negativeClickListener(new IOSDialog.Listener() {
                        @Override
                        public void onClick(IOSDialog iosDialog) {

                            iosDialog.dismiss();

                        }
                    })
                            .build()
                            .show();

                }
            });



            call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + contact1));
                startActivity(callIntent);

            }
        });

        message1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("smsto:"+contact1);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.putExtra("sms_body", "From iFarMart App");
                startActivity(i);

            }
        });

        chat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://wa.me/234" + contact1));
                startActivity(intent);

            }
        });

        call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + contact2));
                startActivity(callIntent);

            }
        });

        message2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto:"+contact2);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.putExtra("sms_body", "From iFarMart App");
                startActivity(i);

            }
        });

        chat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://wa.me/234" + contact2));
                startActivity(intent);
            }
        });



    }
        else
            {
            //link to opening activity
            Intent newPostIntent = new Intent(ProductDetail.this, Login.class);
            newPostIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // LoginActivity is a New Task
            newPostIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // The old task when coming back to this activity should be cleared so we cannot come back to it.

            startActivity(newPostIntent);
        }
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_abt) {
            Intent newPostIntentqq = new Intent(ProductDetail.this, About.class);
            //  Intent newPostIntentqq = new Intent(Main.this, RequestActivity.class);
            startActivity(newPostIntentqq);
            return true;
        }

        return super.onOptionsItemSelected(item);
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


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
     //   this.moveTaskToBack(true);
    }


    @Override
    protected void onStart() {
        super.onStart();


    }


    @Override
    protected void onStop() {
        super.onStop();


    }
    @Override
    protected void onRestart() {
        super.onRestart();
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
    private void setImageAvatar(Context context, String imgBase64){
        try {
            Resources res = getResources();
            //Nếu chưa có avatar thì để hình mặc định
            Bitmap src;
            if (imgBase64.equals("default")) {
                src = BitmapFactory.decodeResource(res, R.drawable.placeholder);
            } else {
                byte[] decodedString = Base64.decode(imgBase64, Base64.DEFAULT);
                src = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            }
            //   pname = (TextView) findViewById(R.id.pname);
            ImageView ppixx = (ImageView) findViewById(R.id.pd_image);
          //  ppixx.setImageDrawable(ImageUtils.roundedImage(context, src));

            Glide.with(getApplicationContext())
                    .load(src)
                    .placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(ppixx);
        }catch (Exception e){
        }
    }

    private void setImageAvatar2(Context context, String imgBase64){
        try {
            Resources res = getResources();
            //Nếu chưa có avatar thì để hình mặc định
            Bitmap src;
            if (imgBase64.equals("default")) {
                src = BitmapFactory.decodeResource(res, R.drawable.placeholder);
            } else {
                byte[] decodedString = Base64.decode(imgBase64, Base64.DEFAULT);
                src = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            }
            //   pname = (TextView) findViewById(R.id.pname);
            ImageView ppixx = (ImageView) findViewById(R.id.seller_pix);
              ppixx.setImageDrawable(ImageUtils.roundedImage(context, src));


        }catch (Exception e){
        }
    }

}
