package com.go_sharp.gomh;

import android.util.Log;

import com.go_sharp.gomh.message.ModelMessage;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by gnu on 1/11/16.
 */

public class MyAndroidFirebaseMsgService extends FirebaseMessagingService {
    private static final String TAG = "MyAndroidFCMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.w("INFO","message "+remoteMessage.getData().toString());
        new ModelMessage().checkForMsg();
    }
}
