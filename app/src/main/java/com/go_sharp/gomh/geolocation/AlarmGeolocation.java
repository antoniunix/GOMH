package com.go_sharp.gomh.geolocation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.go_sharp.gomh.R;
import com.go_sharp.gomh.contextApp.ContextApp;

/**
 * Created by gnu on 28/02/17.
 */

public class AlarmGeolocation {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private final static AlarmGeolocation INSTANCE = new AlarmGeolocation();

    private AlarmGeolocation() {
        setAlarm();
    }

    public static AlarmGeolocation getInstance() {
        return INSTANCE;
    }


    private void setAlarm() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            alarmMgr = (AlarmManager) ContextApp.context
                    .getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(ContextApp.context, Wakelock.class);
            alarmIntent = PendingIntent.getBroadcast(ContextApp.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System
                    .currentTimeMillis(), ContextApp.context.getResources().getInteger(R.integer.geolocation_alarm_start), alarmIntent);
        } else {
            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(ContextApp.context));
            Job myJob = dispatcher.newJobBuilder()
                    .setService(JobGeolocation.class)
                    .setTag("geo_job")
                    .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                    .setTrigger(Trigger.executionWindow(ContextApp.context.getResources().getInteger(R.integer.geo_start),
                            ContextApp.context.getResources().getInteger(R.integer.geo_start_end)))
                    .setReplaceCurrent(true)
                    .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .build();

            dispatcher.mustSchedule(myJob);
        }
    }

}
