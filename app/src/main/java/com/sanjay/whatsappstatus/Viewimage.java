/*
 * Copyright (c) 2017. this file is created or edited by sanjay
 * mail us to kranthi0987@gmail.com
 */

package com.sanjay.whatsappstatus;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.clans.fab.FloatingActionMenu;
import com.snatik.storage.Storage;

import java.io.File;

public class Viewimage extends AppCompatActivity {
    ImageView iv2;
    private FloatingActionMenu menuRed;
    private com.github.clans.fab.FloatingActionButton fab1;
    private com.github.clans.fab.FloatingActionButton fab2;
    private com.github.clans.fab.FloatingActionButton fab3;
    String toPath = Environment.getExternalStorageDirectory() + "/whatsapp status saver/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewimage);
        final Storage storage = new Storage(getApplicationContext());


        //Intent i= getIntent();
        //File f= i.getExtras().getParcelable("img");

        //storage.copy(fromPath, toPath);
        final String f = getIntent().getStringExtra("img");
        Log.d("f", "onCreate: " + f);
        iv2 = findViewById(R.id.imageView4);
        iv2.setImageURI(Uri.parse(f));
//fab
        menuRed = (FloatingActionMenu) findViewById(R.id.fab);
        fab1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_copy);
        fab2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_delete);
        fab3 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_share);
        FloatingActionMenu fab1 = (FloatingActionMenu) findViewById(R.id.fab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "onClick: fab1 cliked");
                
                storage.copy(f, toPath);
            }
        });
    }
}
