package com.softblxgenesis.yuvti.SendAlertSMSNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.hasExtra("yes")){

            AppGlobalVar.getHandler().removeCallbacks(AppGlobalVar.getRunnable());
            Toast.makeText(context, "Yuvti has been Deactivated", Toast.LENGTH_LONG).show();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.cancelAll();

        }
        else if (intent.hasExtra("no")){
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.cancelAll();

            Toast.makeText(context, "No Clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
