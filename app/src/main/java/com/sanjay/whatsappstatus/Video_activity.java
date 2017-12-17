/*
 * Copyright (c) 2017. this file is created or edited by sanjay
 * mail us to kranthi0987@gmail.com
 */

package com.sanjay.whatsappstatus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import tcking.github.com.giraffeplayer2.VideoView;

public class Video_activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("", "yessss");
        final String f = getIntent().getStringExtra("mp4");
        VideoView videoView = findViewById(R.id.video_view);
        videoView.setVideoPath(f).getPlayer().start();

    }

}

