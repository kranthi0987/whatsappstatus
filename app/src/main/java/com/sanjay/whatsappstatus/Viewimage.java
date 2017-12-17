/*
 * Copyright (c) 2017. this file is created or edited by sanjay
 * mail us to kranthi0987@gmail.com
 */

package com.sanjay.whatsappstatus;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.snatik.storage.Storage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Viewimage extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ImageView iv2;
    String toPath = Environment.getExternalStorageDirectory() + "/whatsapp status saver/";
    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    private FloatingActionMenu menuRed;
    private com.github.clans.fab.FloatingActionButton fab1;
    private com.github.clans.fab.FloatingActionButton fab2;
    private com.github.clans.fab.FloatingActionButton fab3;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewimage);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Storage storage = new Storage(getApplicationContext());
        //Intent i= getIntent();
        //File f= i.getExtras().getParcelable("img");
        //storage.copy(fromPath, toPath);
        final String f = getIntent().getStringExtra("img");
        Log.d("f", "onCreate: " + f);
        iv2 = findViewById(R.id.imageView4);
        iv2.setImageURI(Uri.parse(f));
        String extension = "";
        int i = f.lastIndexOf('.');
        if (i > 0) {
            extension = f.substring(i + 1);
        }


        String filename = new File(f).getName();
//fab
        final String topath1 = toPath + filename;
        menuRed = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab_copy);
        fab2 = findViewById(R.id.fab_delete);
//        fab3 = findViewById(R.id.fab_share);
        FloatingActionMenu fab = findViewById(R.id.fab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("", "fab1 " + f + " topath  " + topath1);

                storage.copy(f, topath1);
                Snackbar snackbar = Snackbar
                        .make(view, "file has been saved", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onBackPressed();
                            }
                        });
                snackbar.show();
                scanFile(topath1);
                Log.d("scan", "onClick: " + topath1);
                // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse(f)));
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("delete", "fab2 " + f);

                storage.deleteFile(f);
                Snackbar snackbar = Snackbar
                        .make(view, "file is deleted", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onBackPressed();
                            }
                        });
                snackbar.show();
                scanFile(topath1);
            }
        });
//        fab3.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("WrongConstant")
//            @Override
//            public void onClick(View view) {
//                Log.i("share", "fab3 " + f);
//                Bitmap bmp = BitmapFactory.decodeFile(f);
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_STREAM, f);
//
//                startActivity(Intent.createChooser(intent, "Share Image"));
//
//            }
//        });
    }
    private void scanFile(String path) {

        MediaScannerConnection.scanFile(this,
                new String[]{path}, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("TAG", "Finished scanning " + path);
                    }
                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();

    }
}
