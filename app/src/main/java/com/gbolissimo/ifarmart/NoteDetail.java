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
import android.text.format.DateUtils;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.varunjohn1990.iosdialogs4android.IOSDialog;


public class NoteDetail extends AppCompatActivity {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Topics");

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView ndtime, ndtitle, ndcontent, ndlink;
    private ImageView ndimage, delete;
    private TextView emptytopic;
    private EditText adddate, addcontent;
    private ImageView ppix;
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
        setContentView(R.layout.noti_detail);

//        final Intent intent=getIntent();
  //      final String coursename= intent.getStringExtra("cname");
    //     cid= intent.getStringExtra("cid");
      //  cfacilitator= intent.getStringExtra("cfacilitator");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intent=getIntent();
        final String id = intent.getStringExtra("id");
        final String title = intent.getStringExtra("title");
        final String content = intent.getStringExtra("content");
        final String link = intent.getStringExtra("link");
        final String image = intent.getStringExtra("image");
        final String tym = intent.getStringExtra("tym");



        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Notification");

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
//            id =  user.getEmail().toString().trim().toLowerCase().replace(".","-");
//DisplayData();

            if (isConnected()) {}
            else{
   //             Toast.makeText(NoteDetail.this, "No internet connection!",
     //                   Toast.LENGTH_LONG).show();
            }

        //    TextView coursename = (TextView) findViewById(R.id.coursename);



//            progresstopic = findViewById(R.id.progressBarE);
             ndtime = findViewById(R.id.nd_tym);
            ndtitle = findViewById(R.id.nd_title);
            ndcontent = findViewById(R.id.nd_content);
            ndlink = findViewById(R.id.nd_link);
            ndimage = findViewById(R.id.nd_image);
            delete = findViewById(R.id.deletenote);

            if (DateUtils.getRelativeTimeSpanString(Long.parseLong((String)tym)).toString().equals("0 minutes ago")){
                ndtime.setText("Just now");
            }
            else{
                ndtime.setText(DateUtils.getRelativeTimeSpanString(Long.parseLong((String) tym)));}


//            ndtime.setText(tym);
            ndtitle.setText(title);
            ndcontent.setText(content);
          //  ndlink.setText(link);


            if (getPref("UserStatus", getApplicationContext()).toString().toLowerCase().trim().equals("admin")){
                delete.setVisibility(View.VISIBLE);
            }
            else  {
                delete.setVisibility(View.GONE);
            }


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new IOSDialog.Builder(getApplicationContext())
                            .title("Delete Notification")
                            .message("Are you sure you want to delete this notification?")
                            .positiveButtonText("DELETE")
                            .negativeButtonText("CANCEL")
                            .positiveClickListener(new IOSDialog.Listener() {
                                @Override
                                public void onClick(IOSDialog iosDialog) {

                                    db.collection("Notes").document(id).delete();
                                    Toast.makeText(NoteDetail.this, "Notification Deleted!",
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




        }
        else{
            //link to opening activity
            Intent newPostIntent = new Intent(NoteDetail.this, Login.class);
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
            Intent newPostIntentqq = new Intent(NoteDetail.this, About.class);
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

}
