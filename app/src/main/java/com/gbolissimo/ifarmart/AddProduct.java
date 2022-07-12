package com.gbolissimo.ifarmart;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;


public class AddProduct extends AppCompatActivity {
    private static final int PICK_IMAGE = 1994;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Toolbar toolbar;
    private TextView uploadbt, post;
    private ImageView user_image;
    private TextView add;
    private EditText name, quantity, farmaddress, price, contact1, contact2, productbase64;
    private ImageView uploadimage;
    private ProgressBar progress5;
    private ProgressBar progresstopic;
    private ProgressDialog mProgress;
   public FirebaseFirestore dbb;
    public String id;
    public String cid;
    public String cfacilitator;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Add Product");

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
//        tv.setTextColor(Color.parseColor("#000000"));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/product.ttf");
        tv.setTypeface(tf);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            id =  user.getEmail().toString().trim().toLowerCase().replace(".","-");
//DisplayData();

            if (isConnected()) {}
            else{
//                Toast.makeText(AddNote.this, "No internet connection!",
  //                      Toast.LENGTH_LONG).show();
            }


            mProgress = new ProgressDialog(this);



//            progresstopic = findViewById(R.id.progressBarE);
             name = findViewById(R.id.p_name);
            quantity = findViewById(R.id.p_q);
            farmaddress = findViewById(R.id.p_fa);
            price = findViewById(R.id.p_p);
            contact1 = findViewById(R.id.p_c1);
            contact2 = findViewById(R.id.p_c2);
            productbase64 = findViewById(R.id.p_base64);

            uploadimage = findViewById(R.id.up_image);
            uploadbt = findViewById(R.id.up_bt);
            post = findViewById(R.id.post_product);

            uploadbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Uplod();
                }


            });

            post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (name.getText().toString().isEmpty() && quantity.getText().toString().isEmpty() ) {
                        Toast.makeText(AddProduct.this, "Product name and description fields cannot be empty.",
                                Toast.LENGTH_SHORT).show();
                    } else {



                        final Long timestamp = System.currentTimeMillis();
                        String tym = timestamp.toString();

                        final Intent intent=getIntent();
                        final String type= intent.getStringExtra("type");
                        final String location= intent.getStringExtra("location");

                        Map<String, Object> newContact = new HashMap<>();
                        newContact.put("id", tym.trim());
                        newContact.put("productname", name.getText().toString());
                        newContact.put("quantity", quantity.getText().toString());
                        newContact.put("farmaddress", farmaddress.getText().toString());
                        newContact.put("price", price.getText().toString());
                        newContact.put("contactno1", contact1.getText().toString());
                        newContact.put("contactno2", contact2.getText().toString());
                        newContact.put("seller", getPref("UserName", getApplicationContext()).toString());
                        newContact.put("selleremail", user.getEmail().toString());
                        newContact.put("verification", getPref("UserVerification", getApplicationContext()).toString());
                        newContact.put("businessname", "");
                        newContact.put("others1", type+location);
                        newContact.put("others2", getPref("UserPix", getApplicationContext()).toString());
                        newContact.put("others3", "");
                        newContact.put("others4", "");
                        newContact.put("productpix", productbase64.getText().toString());
                        newContact.put("ttimestamp", tym);


                        db.collection("Products").document(tym).set(newContact);
                        Toast.makeText(AddProduct.this, "Product Added! Refresh page to see changes.",
                                Toast.LENGTH_LONG).show();


                        onBackPressed();

                    }
                }


            });

        }
        else{
            //link to opening activity
            Intent newPostIntent = new Intent(AddProduct.this, Login.class);
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
            Intent newPostIntentqq = new Intent(AddProduct.this, About.class);
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
    //    if (adapter2!=null ){
      //      adapter2.startListening();}
//setUpRecyclerView();
//        emptytopic.setVisibility(View.GONE);
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

            uploadimage.setImageDrawable(ImageUtils.roundedImage(context, src));
        }catch (Exception e){
        }
    }

    public void Uplod() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        //      dialogInterface.dismiss();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(getApplicationContext(), "No Image Selected", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(data.getData());

                Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
                imgBitmap = ImageUtils.cropToSquare(imgBitmap);
                InputStream is = ImageUtils.convertBitmapToInputStream(imgBitmap);
                final Bitmap liteImage = ImageUtils.makeImageLite(is,
                        imgBitmap.getWidth(), imgBitmap.getHeight(),
                        ImageUtils.AVATAR_WIDTH, ImageUtils.AVATAR_HEIGHT);

                String imageBase64 = ImageUtils.encodeBase64(liteImage);
                //  myAccount.avata = imageBase64;

                mProgress.setMessage("Please wait...");
                mProgress.show();

            //    putPref("HubCover", imageBase64, getApplicationContext());
                productbase64.setText(imageBase64);
                mProgress.cancel();
                uploadimage.setImageDrawable(ImageUtils.roundedImage(getApplicationContext(), liteImage));
                Toast.makeText(getApplicationContext(), "Image Added!",
                        Toast.LENGTH_SHORT).show();



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void showErrorMessageToUser(String errorMessage){
        //Create an AlertDialog to show error message
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(AddProduct.this);
        builder.setMessage(errorMessage)
                .setTitle("Error")
                .setPositiveButton(android.R.string.ok, null);
        android.app.AlertDialog dialog=builder.create();
        dialog.show();


    }

}
