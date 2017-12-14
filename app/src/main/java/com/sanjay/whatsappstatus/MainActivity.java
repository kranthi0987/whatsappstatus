/*
 * Copyright (c) 2017. this file is created or edited by sanjay
 * mail us to kranthi0987@gmail.com
 */

package com.sanjay.whatsappstatus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.util.ArrayList;

import static com.sanjay.whatsappstatus.util.util.sendFeedback;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    //File file;
    ArrayList<File> list;
    GridView gv;
    File location = new File(Environment.getExternalStorageDirectory() + "/whatsapp/media/.Statuses/");
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    private AdView mAdView;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
// Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Check for SD Card

       /* if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG)
                    .show();
        } else {
            file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "/whatsapp/media/.Statuses/");
            Log.d("", "onCreate: " + file);
            file.mkdirs();
        }*/

     /*   if (file.isDirectory()) {
            listFile = file.listFiles();
            FilePathStrings = new String[listFile.length];
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                FileNameStrings[i] = listFile[i].getName();
                Log.d("", "strings: "+FilePathStrings[i]);
                Log.d("", "names: "+FileNameStrings[i]);
            }

        }*/

        list = imageReader(location);

        Log.d("", "location " + Environment.getExternalStorageDirectory());
        gv = findViewById(R.id.gridview);
        gv.setAdapter(new GridAdapter());

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                startActivity(new Intent(getApplicationContext(), Viewimage.class).putExtra("img", list.get(position).toString()));
            }
        });
        // feedback preference click listener

    }

    ArrayList<File> imageReader(File root) {
        ArrayList<File> a = new ArrayList<>();
        File[] files = root.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                a.addAll(imageReader(files[i]));
            } else {
                if (files[i].getName().endsWith(".jpg")) {
                    a.add(files[i]);
                    Log.d("files", "imageReader: " + files[i]);
                }
            }
        }
        Log.i("a", "imageReader: " + a);
        return a;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(MainActivity.this, Check_Updates.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(this, About_activity.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {

            sendFeedback(getApplicationContext());
        } else if (id == R.id.nav_send) {
            Intent i = new Intent(MainActivity.this, Check_Updates.class);
            startActivity(i);
            Log.d("", "onNavigationItemSelected: checking new version");
            Toast.makeText(MainActivity.this, "checking for updates",
                    Toast.LENGTH_LONG).show();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            Log.d("", "getCount: " + list.size());
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            Log.d("", "getItem: " + list.get(position));
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.single_grid, parent, false);
            ImageView iv = convertView.findViewById(R.id.imageView3);
            iv.setImageURI(Uri.parse(getItem(position).toString()));

            return convertView;
        }

    }
}
