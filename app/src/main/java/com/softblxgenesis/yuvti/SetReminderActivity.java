package com.softblxgenesis.yuvti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.softblxgenesis.yuvti.ProfileDetailsDB.ProfileDatabase;
import com.softblxgenesis.yuvti.ProfileDetailsDB.YuvAttr;
import com.softblxgenesis.yuvti.SendAlertSMSNotification.AppGlobalVar;
import com.softblxgenesis.yuvti.SendAlertSMSNotification.NotificationReceiver;
import com.softblxgenesis.yuvti.SendAlertSMSNotification.SendNotification;
import com.softblxgenesis.yuvti.SendSMS.SqliteDatabase;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.softblxgenesis.yuvti.SendAlertSMSNotification.AppGlobalVar.CHANNEL_1_ID;

public class SetReminderActivity extends AppCompatActivity {

        NumberPicker picker1;
        NumberPicker picker2;
        TextView tSet;
        TextView rSet;

        final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
        private NotificationManagerCompat notificationManager;
        public static int timeIs = 240;
        public static int AlerttimeIs=60;
        Handler handler = new Handler();
        Runnable runnable ;
        ArrayList<String> PhoneNo;
        int ContactSize;
        private SqliteDatabase mDatabase;
        String smsLocation ;
        String message10seconds;
        ProfileDatabase profileDatabase;
        YuvAttr yuvAttr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        profileDatabase = new ProfileDatabase(this);
        yuvAttr = profileDatabase.getProfile(1);
        message10seconds = ""+yuvAttr.getMessage()+"";


        Button btn = (Button) findViewById(R.id.send);
        mDatabase = new SqliteDatabase(this);
        PhoneNo =  mDatabase.AllPhoneNo();
        ContactSize = mDatabase.AllPhoneNo().size();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SetReminderActivity.this, "Notification will be send after "+timeIs/60+" minute",
                        Toast.LENGTH_SHORT).show();
                new Timer().schedule(
                        new TimerTask(){
                            public void run(){
                                startTimer();
                            }
                        }, timeIs*1000);
                MainActivity.btngetStarted.setVisibility(View.GONE);
                MainActivity.btnsetReminder.setVisibility(View.GONE);
                MainActivity.btnstopSharing.setVisibility(View.VISIBLE);
                MainActivity.Heading.setText("Stop Sharing");
                finish();

            }
        });

        picker1 = findViewById(R.id.numberpicker1);
        rSet = (TextView) findViewById(R.id.rSet);
        picker1.setMinValue(1);
        picker1.setMaxValue(10);

        picker2 = findViewById(R.id.numberpicker_main_picker);
        tSet = (TextView) findViewById(R.id.tSet);


        picker1.setMinValue(5);
        picker1.setMaxValue(30);
        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int time) {
                rSet.setText("Set reminder \nto send notification \nafter "+time+" minutes");
                timeIs=time*60;
            }
        });

        picker2.setMinValue(1);
        picker2.setMaxValue(10);
        picker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int time) {
                tSet.setText("Send Alerts \nto selected contacts \nin every "+time+ " minutes");
                AlerttimeIs=time*60;
            }
        });
        notificationManager = NotificationManagerCompat.from(this);

        if(checkPermission(Manifest.permission.SEND_SMS)){
            //add info if permission enabled
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }

    }

    public void startTimer() {

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, AlerttimeIs*1000);


                new Timer().schedule(
                        new TimerTask(){
                            public void run(){
                                notificationManager.cancelAll();

                                String smsMessage = message10seconds;
                                for(int i = 0 ; i<ContactSize; i++){

                                    if(PhoneNo.get(i) == null || ContactSize == 0 ){
                                        return;
                                    }
                                    if(smsMessage == null || smsMessage.length() == 0){
                                        return;
                                    }
                                }

                                if(checkPermission(Manifest.permission.SEND_SMS)){
                                    SmsManager smsManager = SmsManager.getDefault();
                                    for(int i = 0 ; i<ContactSize; i++){
                                        smsManager.sendTextMessage(PhoneNo.get(i), null, smsMessage, null, null);
                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }


                            }
                        }, 10000);
                new Timer().schedule(
                        new TimerTask(){
                            public void run(){
                                smsLocation = ""+yuvAttr.getName()+" has not responded for 30 seconds make sure you call and ask her.\n"+
                                        "Women helpline no - 022-26111103\nLast Location - "+MainActivity.locality+
                                        "\n\nExact Point\nhttp://maps.google.com/maps?q=loc:"+MainActivity.latti+","+MainActivity.longi+"";

                                for(int i = 0 ; i<ContactSize; i++){

                                    if(PhoneNo.get(i) == null || ContactSize == 0 ){
                                        return;
                                    }
                                    if(smsLocation == null || smsLocation.length() == 0){
                                        return;
                                    }
                                }

                                if(checkPermission(Manifest.permission.SEND_SMS)){
                                    SmsManager smsManager = SmsManager.getDefault();
                                    ArrayList<String> parts = smsManager.divideMessage(smsLocation);
                                    for(int i = 0 ; i<ContactSize; i++){
                                        smsManager.sendMultipartTextMessage(PhoneNo.get(i), null, parts, null, null);
                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            }
                        }, 20000);

                String title = "Are You Safe or Not ?";
                String message = "Please select your command within 10 second";

                Intent activityIntent = new Intent(SetReminderActivity.this, SendNotification.class);
                PendingIntent contentIntent = PendingIntent.getActivity(SetReminderActivity.this,
                        0, activityIntent, 0);


                Intent broadcastIntent1 = new Intent(SetReminderActivity.this, NotificationReceiver.class);
                broadcastIntent1.putExtra("yes", true);
                PendingIntent actionIntent1 = PendingIntent.getBroadcast(SetReminderActivity.this,
                        0, broadcastIntent1, PendingIntent.FLAG_UPDATE_CURRENT);

                Intent broadcastIntent2 = new Intent(SetReminderActivity.this, NotificationReceiver.class);
                broadcastIntent2.putExtra("no", false);
                PendingIntent actionIntent2 = PendingIntent.getBroadcast(SetReminderActivity.this,
                        1, broadcastIntent2, PendingIntent.FLAG_UPDATE_CURRENT);


                Notification notification = new NotificationCompat.Builder(SetReminderActivity.this, CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.ic_icon)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setColor(Color.BLUE)
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setOnlyAlertOnce(true)
                        .addAction(R.mipmap.ic_launcher, "YES", actionIntent1)
                        .addAction(R.mipmap.ic_launcher, "NO", actionIntent2)
                        .build();
                notificationManager.notify(1, notification);
                AppGlobalVar.setHandler(handler);
                AppGlobalVar.setRunnable(runnable);

            }


        }, AlerttimeIs*1000);

    }



    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }




}