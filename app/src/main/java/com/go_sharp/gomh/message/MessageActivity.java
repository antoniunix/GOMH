package com.go_sharp.gomh.message;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.go_sharp.gomh.R;
import com.go_sharp.gomh.dao.DaoMessage;
import com.go_sharp.gomh.dto.DtoMessage;
import com.go_sharp.gomh.util.Config;
import com.go_sharp.gomh.util.MD5;

public class MessageActivity extends AppCompatActivity {

    private static final String TAG = "MessageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        WebView webView = findViewById(R.id.web_view);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            long msgId = bundle.getLong(getString(R.string.msg_id));
            Log.w("MSG", "id: "+msgId);
            DaoMessage daoMessage = new DaoMessage();
            DtoMessage message = daoMessage.selectMsg(msgId);

            if (message.getContent() != null) {
                webView.loadData(message.getContent(), "text/html", "UTF-8");
                String hash = MD5.md5(System.currentTimeMillis() + "_" + msgId);
                String time = System.currentTimeMillis() + "";
                daoMessage.updateSeen(msgId, time, hash);

                try {
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.cancel("msg", (int) msgId);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }

                ModelMessage modelMessage = new ModelMessage();
                message.setIdUser("@user");
                message.setIdMessage(msgId);
                message.setTimestampCel(time);
                message.setImei(Config.getIMEI());
                message.setHash(hash);

                modelMessage.sendConfirmation(message);
            }
        }
    }
}
