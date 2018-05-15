package com.go_sharp.gomh;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.go_sharp.gomh.listener.OnFinishThread;
import com.go_sharp.gomh.model.ModelSplash;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity implements OnFinishThread {

    private Timer timer;
    private WeakReference<Splash> weakReference;
    private ModelSplash modelSplash;
    private static final int ALL_PERMISSIONS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        modelSplash = new ModelSplash(this);
        timer = new Timer();
        weakReference = new WeakReference<>(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.GET_ACCOUNTS, android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, ALL_PERMISSIONS);
        } else {
            File dir = new File(Environment.getExternalStorageDirectory()+getString(R.string.app_path_photo));
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.w("INFO", "no se creo la carpeta "+dir.getAbsolutePath());
                }
            }

            if (!modelSplash.fillSepomex()) {
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        Splash activity = weakReference.get();
                        if (activity != null && !activity.isFinishing()) {
                            startActivity(new Intent(Splash.this, Login.class));
                            finish();
                        }
                    }
                };
                timer.schedule(timerTask, 1500);
            } else {
                Snackbar.make(findViewById(R.id.activity_splash), R.string.Splash_configuration_init, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Action", null).show();
            }
        }
    }

    @Override
    public void onFinishThread() {
        startActivity(new Intent(Splash.this, Login.class));
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
}
