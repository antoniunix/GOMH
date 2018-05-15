package com.go_sharp.gomh.geolocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.go_sharp.gomh.Network.NetworkConfig;
import com.go_sharp.gomh.R;
import com.go_sharp.gomh.contextApp.ContextApp;
import com.go_sharp.gomh.dao.DaoReportGeolocation;
import com.go_sharp.gomh.dto.DtoReportGeolocation;
import com.go_sharp.gomh.util.Config;
import com.go_sharp.gomh.util.SharePreferenceCustom;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.gshp.api.utils.Crypto;

import net.gshp.APINetwork.NetworkTask;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobGeolocation extends JobService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;

    private List<DtoReportGeolocation> dto;
    private DaoReportGeolocation daoReportGeolocation;
    private NetworkConfig networkConfig;

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.w("JobGeolocation", "Start Job Called");
        daoReportGeolocation = new DaoReportGeolocation();
        networkConfig = new NetworkConfig(new HandlerTask(), ContextApp.context);

        setUpLocationClientIfNeeded();
        mLocationRequest = LocationRequest.create();
        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setFastestInterval(30000);
        mLocationRequest.setInterval(30000);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.w("JobGeolocation", "stopped");
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient,
                mLocationRequest, this); // This is the changed line.
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void setUpLocationClientIfNeeded() {
        if (mGoogleApiClient == null)
            buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        this.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        this.mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        long last = Long.parseLong(SharePreferenceCustom.read(R.string.app_share_preference_name,
                R.string.app_share_preference_last_geo, "0"));
        long now = System.currentTimeMillis();
        long diff = now - last;
        //Log.w("JobGeolocation", "last: " + last + " new: " + now);
        long diffMinutes = diff / (60 * 1000) % 60;
        //Log.w("JobGeolocation", "diff: " + diff + " minutes: " + diffMinutes);

        if (diffMinutes > 1) {
            long id = new DaoReportGeolocation().Insert(new DtoReportGeolocation()
                    .setLatitude(location.getLatitude())
                    .setLongitude(location.getLongitude())
                    .setBattery(String.valueOf(Config.getBatteryLevel()))
                    .setAccuracy(String.valueOf(location.getAccuracy()))
                    .setImei(Config.getIMEI())
                    .setSatelliteUtc(String.valueOf(location.getTime()))
                    .setDate(String.valueOf(now))
                    .setTz(Config.getTimeZone())
                    .setVersion("1")
                    .setHash(Crypto.MD5(System.currentTimeMillis() + " " + Math.random()))
                    .setProvider(location.getProvider()));
            SharePreferenceCustom.write(R.string.app_share_preference_name,
                    R.string.app_share_preference_last_geo, location.getTime() + "");
            sendLocation();
        }
    }

    public void sendLocation() {
        dto = daoReportGeolocation.Select();
        new Thread() {
            public void run() {
                String json = new Gson().toJson(dto);
                Log.w("JobGeolocation", "SEND GEO " + json);
                Map<String, String> header = new HashMap<>();
                header.put(ContextApp.context.getString(R.string.network_header_name_application_json),
                        ContextApp.context.getString(R.string.network_header_application_json));
                networkConfig.POST("device/geo", json, "geo", header);
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class HandlerTask extends Handler {
        @Override
        public void handleMessage(Message msg) {
            NetworkTask completedTask = (NetworkTask) msg.obj;
            if (completedTask != null && (completedTask.getTag().equals("geo"))) {
                Log.w("JobGeolocation", "status GEO" + completedTask.getResponseStatus());
                if (completedTask.getResponseStatus() == HttpStatus.SC_CREATED || completedTask.getResponseStatus() == HttpStatus.SC_OK) {
                    new DaoReportGeolocation().delete();
                }
                stopSelf();
            }
        }
    }
}
