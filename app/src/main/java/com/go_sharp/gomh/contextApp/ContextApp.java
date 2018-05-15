package com.go_sharp.gomh.contextApp;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.facebook.stetho.Stetho;
import com.go_sharp.gomh.BuildConfig;
import com.go_sharp.gomh.R;
import com.gosharp.apis.db.DBAPI;

import net.gshp.apiencuesta.APIEncuesta;

import java.io.File;

/**
 * Created by gnu on 13/02/18.
 */

public class ContextApp extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this);


        APIEncuesta.setPATH_FOTO(Environment.getExternalStorageDirectory() + getString(R.string.app_path_photo));
        APIEncuesta.setApplication(this);

        /*Dbapi*/
        DBAPI dbapi = DBAPI.getInstance();
        dbapi.loadPropertiesFromFile(this.getApplicationContext().getResources());
        dbapi.createDB(this.getApplicationContext());

        String path= "/sdcard" +getString(R.string.app_path_photo);
        File f = new File(path);
        if (!f.exists()) {
            if (!f.mkdirs()) {
            }
        }
    }
}
