package com.softblxgenesis.yuvti.LoginApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.softblxgenesis.yuvti.ProfileDetailsDB.ProfileDatabase;
import com.softblxgenesis.yuvti.ProfileDetailsDB.YuvAttr;
import com.softblxgenesis.yuvti.R;
import com.google.android.material.textfield.TextInputEditText;

import online.devliving.passcodeview.PasscodeView;

import static android.view.View.VISIBLE;

public class CreateYuvPin extends AppCompatActivity {
    private PrefManager prefManager;
    public static String Fpin="0",YuvPin="";
    ImageView TypeC;

    private ProfileDatabase profileDatabase;
    private YuvAttr yuvAttr;
    int count=0;
    int lastYuvid = 0;
    TextInputEditText textInputEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();

            finish();
        }
        setContentView(R.layout.activity_create_yuv_pin);

        profileDatabase = new ProfileDatabase(this);

        textInputEditText = (TextInputEditText) findViewById(R.id.inName);
        TypeC = (ImageView)  findViewById(R.id.TypeC);
        Button btn = (Button) findViewById(R.id.createYuv);
        LinearLayout l1 = (LinearLayout) findViewById(R.id.Type);
        LinearLayout l2 = (LinearLayout) findViewById(R.id.ReType);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textInputEditText.getText().toString();
                if (!name.equals("") && !YuvPin.equals("")){
                    count = getLastYuvId();
                    profileDatabase.addProfile(new YuvAttr((count + 1), name, Integer.parseInt(YuvPin), ""+name+" has not responded for 10 seconds make sure you call and ask her."));

                } else {
                    Toast.makeText(getApplicationContext(),"Please fill the required fields",Toast.LENGTH_SHORT).show();
                    textInputEditText.setText("");
                    l1.setVisibility(View.VISIBLE);

                }


                launchHomeScreen();
            }
        });
        PasscodeView passcodeView1 = (PasscodeView) findViewById(R.id.passcode_view1);
        PasscodeView passcodeView2 = (PasscodeView) findViewById(R.id.passcode_view2);

        passcodeView1.setPasscodeEntryListener(new PasscodeView.PasscodeEntryListener() {
            @Override
            public void onPasscodeEntered(String passcode) {
                Fpin= ""+passcode+"";
                l1.setVisibility(View.INVISIBLE);
                l2.setVisibility(View.VISIBLE);
                passcodeView1.clearText();
                TypeC.setVisibility(View.INVISIBLE);
            }
        });

        passcodeView2.setPasscodeEntryListener(new PasscodeView.PasscodeEntryListener() {
            @Override
            public void onPasscodeEntered(String passcode) {
                if (Fpin.equals(passcode)){
                    TypeC.setVisibility(VISIBLE);
                    YuvPin=""+passcode+"";

                } else {
                    l2.setVisibility(View.INVISIBLE);
                    l1.setVisibility(View.VISIBLE);
                    passcodeView2.clearText();
                    TypeC.setVisibility(View.INVISIBLE);
                    Toast.makeText(CreateYuvPin.this,"YUV Code Mismatch",Toast.LENGTH_LONG).show();
                }
            }
        });




    }

    private int getLastYuvId(){
        int count = 0;
        SQLiteDatabase db = profileDatabase.getReadableDatabase();
        String query = "SELECT * FROM " + profileDatabase.TABLE_YUV;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor != null && !cursor.isClosed()){
            cursor.moveToLast();

            if(cursor.getCount() == 0) {
                count = 1;
            }else{
                count = cursor.getInt(cursor.getColumnIndex(profileDatabase.ID));
            }
        }

        return count;

    }


    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(CreateYuvPin.this, YuvPinOpen.class));
        finish();
    }
}