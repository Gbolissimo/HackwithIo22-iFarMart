package com.gbolissimo.ifarmart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


public class ProductActivity extends AppCompatActivity {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView tv_email;
    private ImageView user_image;
    private TextView emptytopic;
    private TextView pname;
    private ImageView ppix;
    private ProgressBar progress5;
    private ProgressBar progresstopic;
    private ProductAdapter adapter2;

   public FirebaseFirestore dbb;
    public String id;
    public String cid;
    public String cfacilitator;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main3);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intentt=getIntent();
        final String type= intentt.getStringExtra("type");
        final String location= intentt.getStringExtra("location");

        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
//        tv.setText(getPref("QueryType", getApplicationContext()).toUpperCase()+" in "+getPref("QueryLocation", getApplicationContext()).toUpperCase());
        tv.setText(getPref("QueryType", getApplicationContext()).toUpperCase()+" in "+getPref("QueryLocation", getApplicationContext()).toUpperCase());

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


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            id =  user.getEmail().toString().trim().toLowerCase().replace(".","-");


            if (isConnected()) {}
            else{
                Toast.makeText(ProductActivity.this, "No internet connection!",
                        Toast.LENGTH_LONG).show();
            }

        //    TextView coursename = (TextView) findViewById(R.id.coursename);



            progresstopic = findViewById(R.id.progressBar);
        //     progress4 = findViewById(R.id.progress4);

//            progress5.setVisibility(View.VISIBLE);
//            progress4.setVisibility(View.VISIBLE);






            setUpRecyclerView();

//            final Intent intent=getIntent();
  //          final String typee= intent.getStringExtra("type");
    //        final String locationn= intent.getStringExtra("location");

            FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.fab);
            fb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent newPostIntent = new Intent(ProductActivity.this, AddProduct.class);
               //     newPostIntent.putExtra("type", typee);
               //     newPostIntent.putExtra("location", locationn);
                    startActivity(newPostIntent);

                }
            });
        }
        else{
            //link to opening activity
            Intent newPostIntent = new Intent(ProductActivity.this, Login.class);
            newPostIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // LoginActivity is a New Task
            newPostIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // The old task when coming back to this activity should be cleared so we cannot come back to it.

            startActivity(newPostIntent);

        }


    }

    public void setUpRecyclerView() {
        final Intent intent=getIntent();
              final String type= intent.getStringExtra("type");
        final String location= intent.getStringExtra("location");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference productRef = db.collection("Products");
//        Query query = productRef.whereEqualTo("others1",getPref("QueryType", getApplicationContext())+getPref("QueryLocation", getApplicationContext())).orderBy("ttimestamp", Query.Direction.DESCENDING);
        Query query = productRef.whereEqualTo("others1",type+location).orderBy("ttimestamp", Query.Direction.DESCENDING);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                 //   Toast.makeText(ProductActivity.this, String.valueOf(queryDocumentSnapshots.size()), Toast.LENGTH_SHORT).show();
            //     emptytopic = (TextView) findViewById(R.id.emptycase);

                if (queryDocumentSnapshots.size() > 0){
              //      emptytopic.setVisibility(View.GONE);
                    progresstopic.setVisibility(View.GONE);
                }
                else{
                //    emptytopic.setVisibility(View.VISIBLE);
                    progresstopic.setVisibility(View.GONE);
                }
                //  progress6.setVisibility(View.GONE);

            }
        });



        FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();

        adapter2 = new ProductAdapter(options);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter2);

        adapter2.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Product hub = documentSnapshot.toObject(Product.class);
                String id = documentSnapshot.getId();
//                String title = hub.getSchoolname();
                String path = documentSnapshot.getReference().getPath();

            }
        });


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
            Intent newPostIntentqq = new Intent(ProductActivity.this, About.class);
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
        if (adapter2!=null ){
            adapter2.startListening();}
        //      setUpRecyclerView();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (adapter2!=null ){
//        adapter2.stopListening();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (adapter2!=null ){
            adapter2.startListening();
        }
        // setUpRecyclerView();
//        emptytopic.setVisibility(View.GONE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //  if (adapter2!=null ){
        //    adapter2.startListening();}

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
