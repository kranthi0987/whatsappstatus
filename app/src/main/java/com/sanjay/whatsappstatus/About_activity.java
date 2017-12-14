/*
 * Copyright (c) 2017. this file is created or edited by sanjay
 * mail us to kranthi0987@gmail.com
 */

package com.sanjay.whatsappstatus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class About_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

}
