package com.gbolissimo.ifarmart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.varunjohn1990.iosdialogs4android.IOSDialog;


public class Main extends AppCompatActivity {
    private FirebaseFirestore dbb2 = FirebaseFirestore.getInstance();
    //private CollectionReference notebookRef2 = dbb2.collection("Hubs");
    Typeface tb, sg;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView tv_email;
    private ImageView user_image;
    private TextView pname;
    private ImageView ppix;
    private ProgressBar progress1;
    private ProgressBar progress2;
    public FirebaseFirestore dbb;
    public String id;
    private FirebaseAuth firebaseAuth;

    private ProgressBar progress6;
    private ProgressBar progress4;

    FirebaseFirestore db;


    public String id2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();



        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("iFarMart");

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

  //      if(getPref("NightMode", getApplicationContext())==null){
    //        putPref("NightMode", "No", getApplicationContext());
      //  }
    //    if ( getPref("NightMode", getApplicationContext()).trim().equals("Yes")){
      //      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

   //     }
     //   else {
       //     AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    //    }

        ReadSingleContact();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){


            id =  user.getEmail().toString().trim().toLowerCase().replace(".","-");
//DisplayData();

            if (isConnected()) {}
            else{
             //   Toast.makeText(Main.this, "No internet connection! You are working offline.",
               //         Toast.LENGTH_LONG).show();
            }

            CardView kf1 = findViewById(R.id.kfm1);
            kf1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newPostIntentzZ = new Intent(Main.this, LocationActivity.class);
                    newPostIntentzZ.putExtra("type", "cassava");
                    startActivity(newPostIntentzZ);
                   // Toast.makeText(getApplicationContext(), "Vehicle Insurance", Toast.LENGTH_SHORT).show();
                }
            });
            CardView kf2 = findViewById(R.id.kfm2);
            kf2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newPostIntentzZ = new Intent(Main.this, LocationActivity.class);
                    newPostIntentzZ.putExtra("type", "maize");
                    startActivity(newPostIntentzZ);
                    //Toast.makeText(getApplicationContext(), "House Insurance", Toast.LENGTH_SHORT).show();
                }
            });
            CardView kf3 = findViewById(R.id.kfm3);
            kf3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newPostIntentzZ = new Intent(Main.this, LocationActivity.class);
                    newPostIntentzZ.putExtra("type", "yam");
                    startActivity(newPostIntentzZ);

                    // Toast.makeText(getApplicationContext(), "Health Insurance", Toast.LENGTH_SHORT).show();
                }
            });
            CardView kf4 = findViewById(R.id.kfm4);
            kf4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newPostIntentzZ = new Intent(Main.this, LocationActivity.class);
                    newPostIntentzZ.putExtra("type", "beans");
                    startActivity(newPostIntentzZ);
                   // Toast.makeText(getApplicationContext(), "Education Insurance", Toast.LENGTH_SHORT).show();
                }
            });
            CardView kf5 = findViewById(R.id.kfm5);
            kf5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newPostIntentzZ = new Intent(Main.this, LocationActivity.class);
                    newPostIntentzZ.putExtra("type", "vegetables");
                    startActivity(newPostIntentzZ);
                   // Toast.makeText(getApplicationContext(), "Other Insurance Types", Toast.LENGTH_SHORT).show();
                }
            });
            CardView kf6 = findViewById(R.id.kfm6);
            kf6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newPostIntentzZ = new Intent(Main.this, LocationActivity.class);
                    newPostIntentzZ.putExtra("type", "fruits");
                    startActivity(newPostIntentzZ);
                    // Toast.makeText(getApplicationContext(), "Other Insurance Types", Toast.LENGTH_SHORT).show();
                }
            });
            CardView kf7 = findViewById(R.id.kfm7);
            kf7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newPostIntentzZ = new Intent(Main.this, LocationActivity.class);
                    newPostIntentzZ.putExtra("type", "poultry");
                    startActivity(newPostIntentzZ);
                    // Toast.makeText(getApplicationContext(), "Other Insurance Types", Toast.LENGTH_SHORT).show();
                }
            });
            CardView kf8 = findViewById(R.id.kfm8);
            kf8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newPostIntentzZ = new Intent(Main.this, LocationActivity.class);
                    newPostIntentzZ.putExtra("type", "fish");
                    startActivity(newPostIntentzZ);
                    // Toast.makeText(getApplicationContext(), "Other Insurance Types", Toast.LENGTH_SHORT).show();
                }
            });





            dbb = FirebaseFirestore.getInstance();
            DocumentReference df = dbb.collection("Users").document(user.getEmail().toString().trim().toLowerCase().replace(".","-"));
            df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        TextView pnamee = (TextView) findViewById(R.id.pname);
                        TextView pemail = (TextView) findViewById(R.id.pemail);
                    //    TextView wellname = (TextView) findViewById(R.id.wellname);
                        //    ppix = (ImageView) findViewById(R.id.ppix);
                   //     wellname.setText("Hi, "+doc.get("name").toString()+"!");
                        pnamee.setText(doc.get("name").toString());
                        pemail.setText(doc.get("email").toString());
                        setImageAvatar(getApplicationContext(), doc.get("pix").toString());

                        putPref("UserName", doc.get("name").toString(), getApplicationContext());
                        putPref("UserPix", doc.get("pix").toString(), getApplicationContext());
                        putPref("UserVerification", doc.get("activationstatus").toString(), getApplicationContext());
                        putPref("UserStatus", doc.get("activationcode").toString(), getApplicationContext());

                        if (doc.get("activationstatus").toString().toLowerCase().trim().equals("active")){
               //             buttonAddNote.setVisibility(View.VISIBLE);
                 //           buttonAddNote2.setVisibility(View.GONE);
                        }else{
                   //         buttonAddNote.setVisibility(View.GONE);
                     //       buttonAddNote2.setVisibility(View.VISIBLE);
                        }
                    }
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


            progress6 = findViewById(R.id.progressBar);


        }
        else{
            //link to opening activity
            Intent newPostIntent = new Intent(Main.this, Login.class);
            newPostIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // LoginActivity is a New Task
            newPostIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // The old task when coming back to this activity should be cleared so we cannot come back to it.

            startActivity(newPostIntent);

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_notification) {
            Intent newPostIntentqq = new Intent(Main.this, NoteActivity.class);
            //  Intent newPostIntentqqx = new Intent(Main.this, UsersActivity.class);
            startActivity(newPostIntentqq);
           // Toast.makeText(getApplicationContext(), "Notifications", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){

                    case R.id.nav_home:
                      //      Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                     //   Intent newPostIntentzZ = new Intent(Main.this, MyHubs.class);
                       // startActivity(newPostIntentzZ);
                          drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_profile:
                      //  Toast.makeText(getApplicationContext(), "My Profile", Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(getApplicationContext(), "My Books", Toast.LENGTH_SHORT).show();
                        Intent newPostIntentzsd = new Intent(Main.this, EditProfile.class);
                        startActivity(newPostIntentzsd);
                        break;
                    case R.id.nav_about:
                        Intent newPostIntentzZx = new Intent(Main.this, About.class);
                        startActivity(newPostIntentzZx);

                        //   Intent newPostIntentzZ = new Intent(MainActivityz.this, FollowActivity.class);
                        // startActivity(newPostIntentzZ);
                        //  drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_settings:
                        Intent newPostIntenntzZx = new Intent(Main.this, SettingActivity.class);
                        startActivity(newPostIntenntzZx);

                        //   Intent newPostIntentzZ = new Intent(MainActivityz.this, FollowActivity.class);
                        // startActivity(newPostIntentzZ);
                        //  drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getApplicationContext(), "Logged out!", Toast.LENGTH_SHORT).show();
                        Intent newPostIntent = new Intent(Main.this, Login.class);
                        newPostIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // LoginActivity is a New Task
                        newPostIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // The old task when coming back to this activity should be cleared so we cannot come back to it.
                        startActivity(newPostIntent);

                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
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

    private void setUpRecyclerView() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference notebookRef2 = dbb2.collection("Users").document(user.getEmail().toString().toLowerCase().trim().replace(".", "-")).collection("Cases");
        //Query query = notebookRef2.whereArrayContains("schoolsubs",user.getEmail().toString().toLowerCase().trim());
        Query query = notebookRef2.orderBy("nad");
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Toast.makeText(Main.this, "Success", Toast.LENGTH_SHORT).show();
                //    Toast.makeText(Main.this, String.valueOf(queryDocumentSnapshots.size()), Toast.LENGTH_SHORT).show();
                TextView emptyhub = (TextView) findViewById(R.id.emptycase);

                if (queryDocumentSnapshots.size() > 0) {
                    emptyhub.setVisibility(View.GONE);
                    progress6.setVisibility(View.GONE);
                } else {
                    emptyhub.setVisibility(View.VISIBLE);
                    progress6.setVisibility(View.GONE);
                }
                //  progress6.setVisibility(View.GONE);

            }
        });


    }

    public void DisplayData(){

        //  final TextView pname = (TextView) findViewById(R.id.pname);

        FirebaseUser usern = FirebaseAuth.getInstance().getCurrentUser();

        firebaseAuth = FirebaseAuth.getInstance();
        dbb = FirebaseFirestore.getInstance();
        DocumentReference df = dbb.collection("Users").document(usern.getEmail().toString().trim().toLowerCase().replace(".","-"));
        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    pname.setText(doc.get("contactname").toString());
                    setImageAvatar(getApplicationContext(), doc.get("contactpix").toString());

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
            ImageView ppixx = (ImageView) findViewById(R.id.ppix);
            ppixx.setImageDrawable(ImageUtils.roundedImage(context, src));
        }catch (Exception e){
        }
    }

    @Override
    public void onBackPressed(){
        this.moveTaskToBack(true);
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
   //     setUpRecyclerView();
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
    private void ReadSingleContact() {
        db = FirebaseFirestore.getInstance();
        DocumentReference user = db.collection("Popmessage").document("message1");

        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override

            public void onComplete(@NonNull Task< DocumentSnapshot > task) {

                if (task.isSuccessful()) {

                    final DocumentSnapshot doc = task.getResult();


                    if (doc.get("id")!=null) {

                        if (!doc.get("id").toString().isEmpty()) {

                            if(getPref("id", getApplicationContext())!=null){

                                if(getPref("id", getApplicationContext()).equals(doc.get("id").toString())){


                                }else {

                                    //here
                                    new IOSDialog.Builder(getApplicationContext())
                                            .title(doc.get("title").toString())              // String or String Resource ID
                                            .message(doc.get("message").toString())  // String or String Resource ID
                                            .positiveButtonText(doc.get("positive").toString())  // String or String Resource ID
                                            .negativeButtonText(doc.get("negative").toString())   // String or String Resource ID
                                            .positiveClickListener(new IOSDialog.Listener() {
                                                @Override
                                                public void onClick(IOSDialog iosDialog) {

                                                        putPref("id", doc.get("id").toString(), getApplicationContext());

                                                        iosDialog.dismiss();
                                                    Intent newPostIntentzZx = new Intent(getApplicationContext(), NoteActivity.class);
                                                    startActivity(newPostIntentzZx);




                                                }
                                            }).negativeClickListener(new IOSDialog.Listener() {
                                        @Override
                                        public void onClick(IOSDialog iosDialog) {
                                            putPref("id", doc.get("id").toString(), getApplicationContext());
                                            iosDialog.dismiss();

                                        }
                                    })
                                            .build()
                                            .show();

                                }

                            }else {
                                putPref("id", "hey", getApplicationContext());
                            }

                        }else {}


                    }


                }

            }

        })

                .addOnFailureListener(new OnFailureListener() {

                    @Override

                    public void onFailure(@NonNull Exception e) {

                    }

                });

    }


}
