package com.go_sharp.gomh.geolocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.go_sharp.gomh.R;
import com.go_sharp.gomh.contextApp.ContextApp;
import com.go_sharp.gomh.model.ModelSend;
import com.go_sharp.gomh.util.Config;

import java.util.Calendar;

/**
 * Created by gnu on 28/02/17.
 */

public class Wakelock extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {

        Calendar calendar = Config.getCalendar();

        Log.e("WAKELOCK", "WAKELOCK INIT");

        if (calendar.get(Calendar.HOUR_OF_DAY) >= ContextApp.context.getResources().getInteger(R.integer.geolocation_schedule_start)
                && calendar.get(Calendar.HOUR_OF_DAY) <= ContextApp.context.getResources().getInteger(R.integer.geolocation_schedule_end)) {
            ContextApp.context.startService(new Intent(ContextApp.context, ServicesGeolocation.class));
        }
        new ModelSend().start();
    }
}
