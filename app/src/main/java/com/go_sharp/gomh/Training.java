package com.go_sharp.gomh;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.go_sharp.gomh.adapter.AdapterDownload;
import com.go_sharp.gomh.contextApp.ContextApp;
import com.go_sharp.gomh.dialog.DialogDownLoadFile;
import com.go_sharp.gomh.dto.DtoDownloadableFiles;
import com.go_sharp.gomh.model.ModelDownload;
import com.go_sharp.gomh.model.ModelToolBar;
import com.go_sharp.gomh.util.BottomNavigationViewHelper;
import com.go_sharp.gomh.util.ChangeFontStyle;
import com.go_sharp.gomh.util.MD5;

import java.io.File;

public class Training extends AppCompatActivity implements View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private ListView lstDownload;
    private ModelDownload model;
    private AdapterDownload adapter;
    private BottomNavigationView bottomNavigationView;

    private void init() {
        lstDownload = findViewById(R.id.lstDownload);
        new ModelToolBar(this).loadInfo(getString(R.string.label_training), "");
        model = new ModelDownload(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.action_training);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = model.getAdapter();
        lstDownload.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Log.e("clicl", "click");
        if (v.getId() == R.id.lyt_row) {
            DtoDownloadableFiles dto = (DtoDownloadableFiles) v.getTag();
            fileExist(dto);
        }
    }

    public void fileExist(DtoDownloadableFiles dto) {
        String typeFile = dto.getExt();
        String nameFile = Environment.getExternalStorageDirectory() + ContextApp.context.getResources().getString(R.string.app_path_photo) + dto.getMd5() + "_" + dto.getTitle() + typeFile;

        File file = new File(nameFile);
        dto.setNameFiel(nameFile);
        if (file.exists() && dto.getMd5().contains(MD5.fileToMD5(nameFile))) {
            openFile(dto);
        } else {
            if (file.exists()) {
                file.delete();
            }
            if (isNetwork()) {
                DialogDownLoadFile dialogDownLoadFile = new DialogDownLoadFile();
                FragmentManager fm = getSupportFragmentManager();
                dto.setNameFiel(nameFile);
                Bundle bundle = new Bundle();
                bundle.putParcelable(ContextApp.context.getResources().getString(R.string.app_bundle_name), dto);
                dialogDownLoadFile.setArguments(bundle);
                dialogDownLoadFile.show(fm, "Down");
                dialogDownLoadFile.setCancelable(false);
            } else {
                Toast.makeText(this, "Verifique que su dispositivo cuente con Internet",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    public static boolean isNetwork() {
        ConnectivityManager connec = (ConnectivityManager) ContextApp.context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo red = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected() || red.isConnected()) {
            Log.e("network", "Wifi " + wifi.isConnected() + " rec " + red.isConnected());
            return true;
        }
        return false;

    }

    public void openFile(DtoDownloadableFiles dto) {
        String typeFile = dto.getExt();
        File file = new File(dto.getNameFiel());
        Intent intent;
        Uri apkUri;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            apkUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
            intent = new Intent(Intent.ACTION_VIEW);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, apkUri);
            intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            startActivityForResult(intent, 0);
        } else {
            apkUri = Uri.fromFile(file);
            intent = new Intent(Intent.ACTION_VIEW);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, apkUri);
            intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            startActivityForResult(intent, 0);
        }

        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (typeFile.equals(".pdf")) {
            intent.setDataAndType(apkUri, "application/pdf");
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else if (typeFile.equals(".mp4") || typeFile.equals(".avi") || typeFile.equals(".mpg")) {
            intent.setDataAndType(apkUri, "video/*");
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        } else if (typeFile.equals(".png") || typeFile.equals(".jpg")) {
            intent.setDataAndType(apkUri, "image/*");
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        try {
            startActivityForResult(intent, 0);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No tiene aplicacion para ver el documento",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                finish();
                break;
            case R.id.action_task:
                startActivity(new Intent(this, Task.class));
                finish();
                break;
            case R.id.action_exit:
                finish();
                break;
        }
        return true;
    }
}

