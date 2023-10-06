package com.softblxgenesis.yuvti.SendAlertSMSNotification;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Handler;

public class AppGlobalVar extends Application {
    public static final String CHANNEL_1_ID = "channel1";
    private static Handler handler = new Handler();
    private static Runnable runnable;

    public static Handler getHandler() {
        return handler;
    }

    public static void setHandler(Handler handler) {
        AppGlobalVar.handler = handler;
    }

    public static Runnable getRunnable() {
        return runnable;
    }

    public static void setRunnable(Runnable runnable) {
        AppGlobalVar.runnable = runnable;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }
    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Yuvti Notification",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Yuvti");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

        }
    }


}
