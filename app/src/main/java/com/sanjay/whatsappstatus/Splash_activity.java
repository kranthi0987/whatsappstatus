package com.sanjay.whatsappstatus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.snatik.storage.Storage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Splash_activity extends AppCompatActivity {
    PermissionListener permissionlistener;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);
        Log.d("splash", "onCreate: ");
        if (Build.VERSION.SDK_INT >= 23) {
            checkPerm();
            createDirectory();
            return;
        }
        else{
            Toast.makeText(Splash_activity.this,
                    "check the permisions in the settings", 2000).show();
        }



    }
    public void createDirectory(){
        Storage storage = new Storage(getApplicationContext());
        String path= Environment.getExternalStorageDirectory() + "/whatsapp status saver/";
        if(storage.createDirectory(path, true)){
            Toast.makeText(Splash_activity.this,
                    "directory created", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Splash_activity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
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

    class splash extends TimerTask {

        @Override
        public void run() {
            Intent i = new Intent(Splash_activity.this,MainActivity.class);

            finish();
            startActivity(i);
        }

    }





}
