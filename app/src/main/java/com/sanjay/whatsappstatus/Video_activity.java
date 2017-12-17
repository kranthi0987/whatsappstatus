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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.github.clans.fab.FloatingActionMenu;
import com.snatik.storage.Storage;

import java.io.File;


public class Video_activity extends AppCompatActivity {
    String toPath = Environment.getExternalStorageDirectory() + "/whatsapp status saver/";
    private FloatingActionMenu menuRed;
    private com.github.clans.fab.FloatingActionButton fab1;
    private com.github.clans.fab.FloatingActionButton fab2;
    private com.github.clans.fab.FloatingActionButton fab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_activity);
        final Storage storage = new Storage(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("", "yessss");
        final String f = getIntent().getStringExtra("mp4");
        VideoView videoView = findViewById(R.id.video_view);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoPath(f);
        videoView.start();

        String filename = new File(f).getName();
        //path
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
                                finish();
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
                                finish();
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

        super.onBackPressed();

    }
}




