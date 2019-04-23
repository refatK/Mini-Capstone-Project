package com.fsck.k9;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;


public class FindFollowUpNotificationsAtBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_LOCKED_BOOT_COMPLETED)){
            Intent i = new Intent();
            i.setAction("com.fsck.k9.FollowUpNotificationsToSendNowService");
            context.startService(i);

            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            long frequency = 10 * 1000;

            PendingIntent pi = PendingIntent.getService(context,0, i,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    Calendar.getInstance().getTimeInMillis(),frequency,pi);
        }
    }
}
