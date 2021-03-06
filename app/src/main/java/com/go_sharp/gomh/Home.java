package com.go_sharp.gomh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.go_sharp.gomh.dialog.DialogAccount;
import com.go_sharp.gomh.dialog.DialogSync;
import com.go_sharp.gomh.dto.DtoBundle;
import com.go_sharp.gomh.geolocation.AlarmGeolocation;
import com.go_sharp.gomh.model.ModelHome;
import com.go_sharp.gomh.model.ModelToolBar;
import com.go_sharp.gomh.util.BottomNavigationViewHelper;
import com.go_sharp.gomh.util.Config;
import com.go_sharp.gomh.util.SharePreferenceCustom;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = "Home";

    private ImageButton btnTBHelp, btnTBSync, btnTBAccount;
    private Button btnStart;
    private BottomNavigationView bottomNavigationView;

    private GoogleMap mGoogleMap;
    private SupportMapFragment mapFrag;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    private ModelHome model;
    DtoBundle dtoBundle;

    private void init() {
        btnTBHelp = findViewById(R.id.btnTBHelp);
        btnTBSync = findViewById(R.id.btnTBSync);
        btnTBAccount = findViewById(R.id.btnTBAccount);
        btnStart = findViewById(R.id.btn_start);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        new ModelToolBar(this).loadInfo("INICIO");
        mapFrag.getMapAsync(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        btnTBHelp.setOnClickListener(this);
        btnTBSync.setOnClickListener(this);
        btnTBAccount.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        model = new ModelHome();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dtoBundle = new DtoBundle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        AlarmGeolocation.getInstance();
        dtoBundle.setIdReportLocal(model.getReportId());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_training:
                startActivity(new Intent(this, Training.class));
                break;
            case R.id.action_task:
                startActivity(new Intent(this, Task.class));
                break;
            case R.id.action_exit:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (checkBasic()) {
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(20).bearing(45).tilt(90).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mGoogleMap.clear();
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Home.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnTBAccount.getId()) {
            new DialogAccount().show(getSupportFragmentManager(), "Fragment_dialog_account");

        } else if (v.getId() == btnTBSync.getId()) {
            if (SharePreferenceCustom.read(getString(R.string.app_share_preference_name), getString(R.string.app_share_preference_user_account), null) != null) {
                DialogSync diFragmentSync = new DialogSync();
                diFragmentSync.setCancelable(false);
                diFragmentSync.show(getSupportFragmentManager(), "DialogFragmentSync");
            } else {
                new DialogAccount().show(getSupportFragmentManager(), "Fragment_dialog_account");
            }
        } else if (v.getId() == btnTBHelp.getId()) {

        } else if (v.getId() == btnStart.getId()) {
            int statusReport = model.statusReport();
            Log.w(TAG, "status report: " + statusReport);
            if (statusReport == getResources().getInteger(R.integer.first_report)) {
                startActivity(new Intent(this, MenuReport.class)
                        .putExtra(getString(R.string.app_bundle_name), dtoBundle));
            } else if (statusReport == getResources().getInteger(R.integer.open_report)) {
                startActivity(new Intent(this, MenuReport.class)
                        .putExtra(getString(R.string.app_bundle_name), dtoBundle));
            } else if (statusReport == getResources().getInteger(R.integer.complete_report)) {
                dtoBundle.setIdReportLocal(0);
                startActivity(new Intent(this, ReportList.class)
                        .putExtra(getString(R.string.app_bundle_name), dtoBundle));
            }
        }
    }

    private boolean checkBasic() {
        boolean flag = false;
        if (!Config.isDateAutomatic()) {
            Toast.makeText(getApplicationContext(),
                    "Debe poner la hora en autamatico", Toast.LENGTH_LONG)
                    .show();
            startActivityForResult(new Intent(
                    android.provider.Settings.ACTION_DATE_SETTINGS), -1);
        } else if (!Config.isGPSenabled()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Debe encender la localización, activar opción \"GPS\" para continuar",
                    Toast.LENGTH_LONG).show();
            startActivityForResult(new Intent(
                    android.provider.Settings.ACTION_SETTINGS), -1);
        } else if (!Config.isDateAutomatic1()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Debe Activar Zona Horaria Automatica",
                    Toast.LENGTH_LONG).show();
            startActivityForResult(new Intent(
                            android.provider.Settings.ACTION_DATE_SETTINGS),
                    -1);
        } else if (Config.isMockLocation()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Desactivar opción \"Coordenadas falsas\" para continuar",
                    Toast.LENGTH_LONG).show();
            startActivityForResult(new Intent(
                            android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS),
                    -1);
        } else {
            flag = true;
        }
        return flag;
    }
}
