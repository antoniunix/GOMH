package com.go_sharp.gomh.message;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.go_sharp.gomh.Home;
import com.go_sharp.gomh.Network.NetworkConfig;
import com.go_sharp.gomh.R;
import com.go_sharp.gomh.contextApp.ContextApp;
import com.go_sharp.gomh.dao.DaoMessage;
import com.go_sharp.gomh.dto.DtoMessage;
import com.go_sharp.gomh.util.SharePreferenceCustom;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.gshp.APINetwork.NetworkTask;

import org.apache.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class ModelMessage {
    private Context context;
    private NetworkConfig networkConfig;

    public ModelMessage() {
        this.context = ContextApp.context;
        networkConfig = new NetworkConfig(new HandlerTask(Looper.getMainLooper()), ContextApp.context);
    }

    public void checkForMsg() {
        new Thread() {
            public void run() {
                networkConfig.GET("multireport/catalog/message_service_all", "message");
            }

        }.start();
    }

    private void makeNotifications(List<DtoMessage> messages) {
        if (messages.size() > 0) {
            for (DtoMessage message : messages) {
                Log.w(TAG, new Gson().toJson(message));
                switch (message.getType_id()) {
                    case 1:
                        showMsgNotification(message);
                        break;
                    case 2:
                        SharePreferenceCustom.write(R.string.app_share_preference_name, R.string.app_share_preference_sync, "true");
                        showSyncNotification(message);
                        //ShortcutBadger.applyCount(context, polls);
                        break;
                }
            }
        }
    }

    public void sendConfirmation(final DtoMessage message) {
        new Thread() {
            public void run() {
                List<DtoMessage> messages = new ArrayList<>();
                messages.add(message);

                String json = new Gson().toJson(messages);
                Log.w(TAG, "msg " + json);
                Map<String, String> header = new HashMap<>();
                header.put(context.getString(R.string.network_header_name_application_json), context.getString(R.string.network_header_application_json));
                networkConfig.POST("multireport/insertnt/rcmessage/" + message.getId(), json, "msgconf" + message.getId(), header);
            }
        }.start();
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_stat_name : R.mipmap.ic_launcher;
    }

    private void showMsgNotification(DtoMessage message) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, context.getString(R.string.msg_channel_id))
                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(getNotificationIcon())
                        .setColor(context.getResources().getColor(R.color.colorPrimary))
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                                R.mipmap.ic_launcher))
                        .setContentTitle(message.getTitle())
                        .setContentText(message.getDescription())
                        .setAutoCancel(false)
                        .setOngoing(true);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MessageActivity.class);
        resultIntent.putExtra(context.getString(R.string.msg_id), message.getId());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(Home.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent((int) message.getId(), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify("msg", (int) message.getId(), mBuilder.build());
    }

    private void showSyncNotification(DtoMessage message) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, context.getString(R.string.sync_channel_id))
                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(getNotificationIcon())
                        .setColor(context.getResources().getColor(R.color.colorPrimary))
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                                R.mipmap.ic_launcher))
                        .setContentTitle(message.getTitle())
                        .setContentText(message.getDescription())
                        .setAutoCancel(false)
                        .setOngoing(true);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, Home.class);
        Log.w("MSG", "id: " + message.getId());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(Home.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify("sync", 2, mBuilder.build());
    }

    class HandlerTask extends Handler {

        HandlerTask(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            NetworkTask nt = (NetworkTask) msg.obj;
            if (nt.getTag().equals("message")) {
                Type typeObj = new TypeToken<List<DtoMessage>>() {
                }.getType();

                List<DtoMessage> messages = new Gson().fromJson(nt.getResponse(), typeObj);
                if (messages != null) {
                    new DaoMessage().delete();
                    new DaoMessage().insert(messages);

                    makeNotifications(messages);
                }
            } else if (nt.getTag().contains("msgconf") && (nt.getResponseStatus() == HttpStatus.SC_OK
                    || nt.getResponseStatus() == HttpStatus.SC_CREATED)) {
                long id = Long.valueOf(nt.getTag().substring(nt.getTag().indexOf("f") + 1));
                DaoMessage daoMessage = new DaoMessage();
                daoMessage.deleteById(id);
            }
        }
    }
}
