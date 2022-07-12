package com.gbolissimo.ifarmart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;


public class EditProfile extends AppCompatActivity {
    private static final int PICK_IMAGE = 1994;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Topics");
    private ProgressDialog mProgress;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView  editimage, edbr, edcha, ss1, ss2;
    private ImageView profileimage, d, editbranch;
    private TextView edebt;
    private EditText editphone, editpenname;
    private ProgressBar progressprofile;
    private ProgressBar progress5;
    private ProgressBar progresstopic;


   public FirebaseFirestore dbb;
    public String id;
    public String cid;
    public String cfacilitator;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

//        final Intent intent=getIntent();
  //      final String coursename= intent.getStringExtra("cname");
    //     cid= intent.getStringExtra("cid");
      //  cfacilitator= intent.getStringExtra("cfacilitator");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseUser userr = FirebaseAuth.getInstance().getCurrentUser();


        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText(userr.getEmail().toString());

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
//                Toast.makeText(EditProfile.this, "No internet connection!",
  //                      Toast.LENGTH_LONG).show();
            }

        //    TextView coursename = (TextView) findViewById(R.id.coursename);



            progressprofile = findViewById(R.id.progressprofile);
             profileimage = findViewById(R.id.profile_image);
            editimage = findViewById(R.id.edit_image);
            editpenname = findViewById(R.id.edit_penname);
            editphone = findViewById(R.id.edit_phone);
            edbr = findViewById(R.id.ed_br);
            editbranch = findViewById(R.id.edit_branch);
            edcha = findViewById(R.id.ed_cha);

            ss1 = findViewById(R.id.ss1);
            ss2 = findViewById(R.id.ss2);

            mProgress = new ProgressDialog(this);

            DisplayData();


            editimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

Uplod();

                }
            });

            editbranch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectChambers();
                  //  Uplod();

                }
            });


            final int[] checkedItem = {-1};
            edbr.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            //start
                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditProfile.this);

                                            // set the custom icon to the alert dialog
                                          //  alertDialog.setIcon(R.drawable.image_logo);

                                            // title of the alert dialog
                                            alertDialog.setTitle("NBA Branch");

                                            // list of the items to be displayed to
                                            // the user in the form of list
                                            // so that user can select the item from
                                            final String[] listItems = new String[]{
                                                    "Nil",
                                                    "Aba",
                                                    "Abakaliki",
                                                    "Abeokuta",
                                                    "Abuja",
                                                    "Ado-Ekiti",
                                                    "Afikpo",
                                                    "Agbor",
                                                    "Aguata",
                                                    "Ahoada",
                                                    "Akure",
                                                    "Asaba",
                                                    "Auchi",
                                                    "Awka",
                                                    "Badagry",
                                                    "Bauchi",
                                                    "Benin",
                                                    "Birnin",
                                                    "Biu",
                                                    "Bori",
                                                    "Bwari",
                                                    "Calabar",
                                                    "Damaturu",
                                                    "Dutse",
                                                    "Eket",
                                                    "Ekpoma",
                                                    "Enugu",
                                                    "Funtua",
                                                    "Gboko",
                                                    "Gombe",
                                                    "Gusau",
                                                    "Gwagwalada",
                                                    "Ibadan",
                                                    "Idah",
                                                    "Idemili",
                                                    "Ihiala",
                                                    "Ijebu-Ode",
                                                    "Ikeja",
                                                    "Ikere",
                                                    "Ikirun",
                                                    "Ikole Ekiti",
                                                    "Ikom",
                                                    "Ikorodu",
                                                    "Ikot Ekpene",
                                                    "Ile-Ife",
                                                    "Ilesa",
                                                    "Ilorin",
                                                    "Isiala",
                                                    "Isiokpo",
                                                    "Iwo",
                                                    "Jalingo",
                                                    "Jos",
                                                    "Kabba",
                                                    "Kaduna",
                                                    "Kafanchan",
                                                    "Kano",
                                                    "Katsina",
                                                    "Lafia",
                                                    "Lagos",
                                                    "Lokoja",
                                                    "Maiduguri",
                                                    "Makurdi",
                                                    "Mbaise",
                                                    "Minna",
                                                    "Mubi",
                                                    "Nnewi",
                                                    "Nsukka",
                                                    "Obollo",
                                                    "Offa",
                                                    "Ogoja",
                                                    "Ogomosho",
                                                    "Ogwashi-Uku",
                                                    "Ohafia",
                                                    "Oji River",
                                                    "Okene",
                                                    "Okigwe",
                                                    "Okitipupa",
                                                    "Oleh",
                                                    "Ondo",
                                                    "Onitsha",
                                                    "Orlu",
                                                    "Osogbo",
                                                    "Ota",
                                                    "Otukpo",
                                                    "Owerri",
                                                    "Owo",
                                                    "Oyo",
                                                    "Pankshin",
                                                    "Port Harcourt",
                                                    "Sagamu",
                                                    "Sapele",
                                                    "Sokoto",
                                                    "Suleja",
                                                    "Ughelli",
                                                    "Umuahia",
                                                    "Uyo",
                                                    "Warri",
                                                    "Wukari",
                                                    "Yenagoa",
                                                    "Yola",
                                                    "Zaria",
                                                    "Degema",
                                                    "Saki",
                                                    "Ukwa",
                                                    "Ungoggo",
                                                    "Oron",
                                                    "Igarra",
                                                    "Sagbama",
                                                    "Anaocha",
                                                    "Keffi",
                                                    "Udu",
                                                    "Barnawa",
                                                    "Bukuru",
                                                    "Effurun",
                                                    "Epe",
                                                    "Mbano-Etiti",
                                                    "Okehi",
                                                    "Okrika",
                                                    "Omoku",
                                                    "Onueke",
                                                    "Otuocha",
                                                    "Wzeagu",
                                                    "Uromi",
                                                    "Ikare Akoko",
                                                    "Ilaro",
                                                    "Shendam"


                                            };

                                            // the function setSingleChoiceItems is the function which builds
                                            // the alert dialog with the single item selection
                                            alertDialog.setSingleChoiceItems(listItems, checkedItem[0], new DialogInterface.OnClickListener() {
                                                @SuppressLint("SetTextI18n")
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    // update the selected item which is selected by the user
                                                    // so that it should be selected when user opens the dialog next time
                                                    // and pass the instance to setSingleChoiceItems method
                                                    checkedItem[0] = which;

                                                    // now also update the TextView which previews the selected item
                                                    edbr.setText(listItems[which]);

                                                    // when selected an item the dialog should be closed with the dismiss method
                                                    dialog.dismiss();
                                                }
                                            });

                                            // set the negative button if the user
                                            // is not interested to select or change
                                            // already selected item
                                            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            });

                                            // create and build the AlertDialog instance
                                            // with the AlertDialog builder instance
                                            AlertDialog customAlertDialog = alertDialog.create();

                                            // show the alert dialog when the button is clicked
                                            customAlertDialog.show();


    //end

                }
            });





//            ededate.setText(date);
  //          edecontent.setText(content);

//

            edcha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editpenname.getText().toString().isEmpty()){
    Toast.makeText(EditProfile.this, "Name field cannot be empty!",
            Toast.LENGTH_SHORT).show();
}
else {
 //   final Intent intent=getIntent();
   // final String iddo = intent.getStringExtra("id");
                        FirebaseUser useru = FirebaseAuth.getInstance().getCurrentUser();
    db.collection("Users").document(useru.getEmail().toString().trim().toLowerCase().replace(".","-")).update("name", editpenname.getText().toString());
                        db.collection("Users").document(useru.getEmail().toString().trim().toLowerCase().replace(".","-")).update("number", editphone.getText().toString());
                        db.collection("Users").document(useru.getEmail().toString().trim().toLowerCase().replace(".","-")).update("branch", edbr.getText().toString());

    Toast.makeText(EditProfile.this, "Profile Updated!",
            Toast.LENGTH_SHORT).show();
    DisplayData();
   // onBackPressed();
//    Intent newPostIntent = new Intent(EditProfile.this, Main.class);
  //  newPostIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // LoginActivity is a New Task
    //newPostIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // The old task when coming back to this activity should be cleared so we cannot come back to it.

  //  startActivity(newPostIntent);
}


                }


//

            });







        }
        else{
            //link to opening activity
            Intent newPostIntent = new Intent(EditProfile.this, Login.class);
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
            Intent newPostIntentqq = new Intent(EditProfile.this, About.class);
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
    //    if (adapter2!=null ){
      //      adapter2.startListening();}
//setUpRecyclerView();
//        emptytopic.setVisibility(View.GONE);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */

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

    public void DisplayData(){

        //  final TextView pname = (TextView) findViewById(R.id.pname);
progressprofile.setVisibility(View.VISIBLE);
        FirebaseUser usern = FirebaseAuth.getInstance().getCurrentUser();

        firebaseAuth = FirebaseAuth.getInstance();
        dbb = FirebaseFirestore.getInstance();
        DocumentReference df = dbb.collection("Users").document(usern.getEmail().toString().trim().toLowerCase().replace(".","-"));
        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    editpenname.setText(doc.get("name").toString());
                    editphone.setText(doc.get("number").toString());
                    ss1.setText("Subscription Status: "+ doc.get("activationstatus").toString());
                    ss2.setText("Expiry Date: " + doc.get("expirydate").toString());
//                    edbr.setText(doc.get("branch").toString());

                    if (doc.get("branch").toString().isEmpty()){
                        edbr.setText("Click here to select");
                    }
                    else{
                        edbr.setText(doc.get("branch").toString());
                        editbranch.setVisibility(View.VISIBLE);
                    }
                    setImageAvatar(getApplicationContext(), doc.get("pix").toString());

                    progressprofile.setVisibility(View.GONE);
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
    private void setImageAvatar(Context context, String imgBase64){
        try {
            Resources res = getResources();
            //Nếu chưa có avatar thì để hình mặc định
            Bitmap src;
            if (imgBase64.equals("default")) {
                src = BitmapFactory.decodeResource(res, R.drawable.no_pix);
            } else {
                byte[] decodedString = Base64.decode(imgBase64, Base64.DEFAULT);
                src = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            }
            //   pname = (TextView) findViewById(R.id.pname);
            ImageView ppixx = (ImageView) findViewById(R.id.profile_image);
            ppixx.setImageDrawable(ImageUtils.roundedImage(context, src));
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

                mProgress.setMessage("Updating...");
                mProgress.show();
                FirebaseUser userk = FirebaseAuth.getInstance().getCurrentUser();
                DocumentReference contact = db.collection("Users").document(userk.getEmail().toString().trim().toLowerCase().replace(".","-"));
                contact.update("pix", imageBase64);
                Toast.makeText(getApplicationContext(), "Profile Picture Uploaded!",
                        Toast.LENGTH_SHORT).show();
                mProgress.cancel();
                profileimage.setImageDrawable(ImageUtils.roundedImage(getApplicationContext(), liteImage));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public void SelectChambers() {
        final int[] checkedItem = {-1};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditProfile.this);

        // set the custom icon to the alert dialog
        //  alertDialog.setIcon(R.drawable.image_logo);

        // title of the alert dialog
        alertDialog.setTitle("NBA Branch");

        // list of the items to be displayed to
        // the user in the form of list
        // so that user can select the item from
        final String[] listItems = new String[]{
                "Nil",
                "Aba",
                "Abakaliki",
                "Abeokuta",
                "Abuja",
                "Ado-Ekiti",
                "Afikpo",
                "Agbor",
                "Aguata",
                "Ahoada",
                "Akure",
                "Asaba",
                "Auchi",
                "Awka",
                "Badagry",
                "Bauchi",
                "Benin",
                "Birnin",
                "Biu",
                "Bori",
                "Bwari",
                "Calabar",
                "Damaturu",
                "Dutse",
                "Eket",
                "Ekpoma",
                "Enugu",
                "Funtua",
                "Gboko",
                "Gombe",
                "Gusau",
                "Gwagwalada",
                "Ibadan",
                "Idah",
                "Idemili",
                "Ihiala",
                "Ijebu-Ode",
                "Ikeja",
                "Ikere",
                "Ikirun",
                "Ikole Ekiti",
                "Ikom",
                "Ikorodu",
                "Ikot Ekpene",
                "Ile-Ife",
                "Ilesa",
                "Ilorin",
                "Isiala",
                "Isiokpo",
                "Iwo",
                "Jalingo",
                "Jos",
                "Kabba",
                "Kaduna",
                "Kafanchan",
                "Kano",
                "Katsina",
                "Lafia",
                "Lagos",
                "Lokoja",
                "Maiduguri",
                "Makurdi",
                "Mbaise",
                "Minna",
                "Mubi",
                "Nnewi",
                "Nsukka",
                "Obollo",
                "Offa",
                "Ogoja",
                "Ogomosho",
                "Ogwashi-Uku",
                "Ohafia",
                "Oji River",
                "Okene",
                "Okigwe",
                "Okitipupa",
                "Oleh",
                "Ondo",
                "Onitsha",
                "Orlu",
                "Osogbo",
                "Ota",
                "Otukpo",
                "Owerri",
                "Owo",
                "Oyo",
                "Pankshin",
                "Port Harcourt",
                "Sagamu",
                "Sapele",
                "Sokoto",
                "Suleja",
                "Ughelli",
                "Umuahia",
                "Uyo",
                "Warri",
                "Wukari",
                "Yenagoa",
                "Yola",
                "Zaria",
                "Degema",
                "Saki",
                "Ukwa",
                "Ungoggo",
                "Oron",
                "Igarra",
                "Sagbama",
                "Anaocha",
                "Keffi",
                "Udu",
                "Barnawa",
                "Bukuru",
                "Effurun",
                "Epe",
                "Mbano-Etiti",
                "Okehi",
                "Okrika",
                "Omoku",
                "Onueke",
                "Otuocha",
                "Wzeagu",
                "Uromi",
                "Ikare Akoko",
                "Ilaro",
                "Shendam"


        };

        // the function setSingleChoiceItems is the function which builds
        // the alert dialog with the single item selection
        alertDialog.setSingleChoiceItems(listItems, checkedItem[0], new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // update the selected item which is selected by the user
                // so that it should be selected when user opens the dialog next time
                // and pass the instance to setSingleChoiceItems method
                checkedItem[0] = which;

                // now also update the TextView which previews the selected item
                edbr.setText(listItems[which]);

                // when selected an item the dialog should be closed with the dismiss method
                dialog.dismiss();
            }
        });

        // set the negative button if the user
        // is not interested to select or change
        // already selected item
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // create and build the AlertDialog instance
        // with the AlertDialog builder instance
        AlertDialog customAlertDialog = alertDialog.create();

        // show the alert dialog when the button is clicked
        customAlertDialog.show();


    }
    }
