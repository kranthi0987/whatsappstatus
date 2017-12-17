/*
 * Copyright (c) 2017. this file is created or edited by sanjay
 * mail us to kranthi0987@gmail.com
 */

package com.sanjay.whatsappstatus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.snatik.storage.Storage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import io.fabric.sdk.android.Fabric;

public class Splash_activity extends AppCompatActivity {
    public static final String mypreference = "mypref";
    public static final boolean isfirst = true;
    PermissionListener permissionlistener;
    SharedPreferences sharedpreferences;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_activity);
        Log.d("splash", "onCreate: ");
        FullScreencall();
        firsttime();
    }

    public void firsttime() {
        Timer t = new Timer();
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if (sharedpreferences.getBoolean("isfirst", true)) {
            editor.putBoolean("isfirst", false);
            editor.apply();
//            Toast.makeText(Splash_activity.this,
//                    "predfernce ", 2000).show();
            checkPerm();
            createDirectory();
            Toast.makeText(Splash_activity.this,
                    "check the permisions in the settings", 2000).show();

        } else {
//            Toast.makeText(Splash_activity.this,
//                    "already done ", 2000).show();
            t.schedule(new splash(), 3000);
        }
    }
    public void createDirectory(){
        Storage storage = new Storage(getApplicationContext());
        String path = Environment.getExternalStorageDirectory() + "/whatsappstatus saver/";
        if(storage.createDirectory(path, true)){
//            Toast.makeText(Splash_activity.this,
//                    "directory created", Toast.LENGTH_SHORT).show();
            Log.i("", "createDirectory: created");
        }else{
            Toast.makeText(Splash_activity.this,
                    "check the permisions in the settings", Toast.LENGTH_SHORT).show();
            Log.e("", "createDirectory: failed to created" );
        }
    }
    private void checkPerm() {
        PermissionListener permissionlistener = new PermissionListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onPermissionGranted() {
//                Toast.makeText(Splash_activity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                Timer t = new Timer();
                boolean checkConnection=new ApplicationUtility().checkConnection(Splash_activity.this);
                if (checkConnection) {
                    t.schedule(new splash(), 3000);
                } else {
                    Toast.makeText(Splash_activity.this,
                            "connection not found...plz check connection", 3000).show();
                    t.schedule(new splash(), 3000);
                }
            }
            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(Splash_activity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    public void FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    class splash extends TimerTask {
        @Override
        public void run() {
            Intent i = new Intent(Splash_activity.this,MainActivity.class);
            finish();
            startActivity(i);
        }

    }


}
