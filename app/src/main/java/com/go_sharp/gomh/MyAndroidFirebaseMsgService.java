package com.go_sharp.gomh;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by gnu on 1/11/16.
 */

public class MyAndroidFirebaseMsgService extends FirebaseMessagingService {
    private static final String TAG = "MyAndroidFCMService";
    private SharedPreferences preferences;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        preferences = getSharedPreferences(getString(R.string.app_share_preference_name), Context.MODE_PRIVATE);
    }
}
