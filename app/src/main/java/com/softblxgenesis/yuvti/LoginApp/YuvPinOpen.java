package com.softblxgenesis.yuvti.LoginApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softblxgenesis.yuvti.MainActivity;
import com.softblxgenesis.yuvti.ProfileDetailsDB.ProfileDatabase;
import com.softblxgenesis.yuvti.ProfileDetailsDB.YuvAttr;
import com.softblxgenesis.yuvti.R;



public class YuvPinOpen extends AppCompatActivity {
    private ProfileDatabase profileDatabase;
    private YuvAttr yuvAttr;
    public static int tst=0;
    private String pinCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuv_pin_open);

        profileDatabase = new ProfileDatabase(this);
        //Color Mode codes start

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", true);

        // When user reopens the app


        if (isDarkModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        //----------------------------------------Color Mode codes end


        yuvAttr = profileDatabase.getProfile(1);
        pinCode =""+yuvAttr.getPin();

        EditText filled  = (EditText) findViewById(R.id.field);

        Button go = (Button) findViewById(R.id.gobtn);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passcode = filled.getText().toString();
                if (!passcode.equals("") && passcode.equals(pinCode))
                {
                    Intent intent = new Intent(YuvPinOpen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    filled.setText("");
                    Toast.makeText(YuvPinOpen.this, "Invalid PassCode " , Toast.LENGTH_SHORT).show();
                }
            }
        });


    }




}