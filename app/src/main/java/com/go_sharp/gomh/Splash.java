package com.go_sharp.gomh;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
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
    private ModelSplash modelSplash;

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
        /**
         * si la tabla de sepomex esta vacia, se tendra que llenar, cosa que es un proceso lento, y el splash terminara una vez concluidos los inserts
         * de lo contrario solo durara el tiempo especificado para mostrar la imagen
         */
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

    @Override
    public void onFinishThread() {
        startActivity(new Intent(Splash.this, Login.class));
        finish();
    }
}
