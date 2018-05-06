package com.go_sharp.gomh;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.go_sharp.gomh.listener.OnDissmis;
import com.go_sharp.gomh.listener.OnDissmisDialogListener;

/**
 * Created by leo on 4/05/18.
 */

public class Permissions extends AppCompatActivity implements OnDissmis {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.GET_ACCOUNTS, android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if (!hasPermissions(context, PERMISSIONS)) {
            onDissmisDialogListenerStatus(0);
        } else {
            startActivity(new Intent(this, Splash.class));
            finish();
        }
    }

    /* Verifica que tenga los permisos necesarios de la App */
    public static boolean hasPermissions(Context context, String... permissions){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null){
            for(String permission : permissions){
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onDissmisDialogListenerStatus(int status) {
        int PERMISSION_ALL = 1;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e(getResources().getString(R.string.app_name), "Sin permiso ACCESS_FINE_LOCATION");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ALL);
            }
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.e(getResources().getString(R.string.app_name), "Sin permiso WRITE_EXTERNAL_STORAGE");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_ALL);
            }
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Log.e(getResources().getString(R.string.app_name), "Sin permiso CALL_PHONE");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, PERMISSION_ALL);
            }
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                Log.e(getResources().getString(R.string.app_name), "Sin permiso GET_ACCOUNTS");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.GET_ACCOUNTS}, PERMISSION_ALL);
            }
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Log.e(getResources().getString(R.string.app_name), "Sin permiso CAMERA");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_ALL);
            }
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                Log.e(getResources().getString(R.string.app_name), "Sin permiso READ_PHONE_STATE");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, PERMISSION_ALL);
            }
        }
    }

    @Override
    public void onDismissDialogListener(Object obj) {

    }
}
