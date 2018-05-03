package com.go_sharp.gomh;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.go_sharp.gomh.listener.OnFinishThread;
import com.go_sharp.gomh.model.ModelSplash;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity implements OnFinishThread {

    private Timer timer;
    private WeakReference<Splash> weakReference;
    private static final int ALL_PERMISSIONS = 200;

    private void init() {
        timer = new Timer();
        weakReference = new WeakReference<>(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.GET_ACCOUNTS, android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.READ_PHONE_STATE};
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, ALL_PERMISSIONS);
        } else {
            File dir = new File(getString(R.string.app_path_photo));
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.e("INFO", "no se creo la carpeta");
                }
            }

            dummyTimer();
        }
    }

    private void dummyTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Splash activity = weakReference.get();
                if (activity != null && !activity.isFinishing()) {
                    startActivity(new Intent(Splash.this, Home.class));
                    finish();
                }
            }
        };
        timer.schedule(timerTask, 1500);
    }

    @Override
    public void onFinishThread() {
        startActivity(new Intent(Splash.this, Home.class));
        finish();
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        // user rejected the permission
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                        if (!showRationale) {
                            Toast.makeText(this, "Por favor acepte todos los permisos",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    }
                }
            }
        }
    }
}
