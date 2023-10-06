package com.softblxgenesis.yuvti.MenuOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.softblxgenesis.yuvti.MainActivity;
import com.softblxgenesis.yuvti.ProfileDetailsDB.ProfileDatabase;
import com.softblxgenesis.yuvti.ProfileDetailsDB.YuvAttr;
import com.softblxgenesis.yuvti.R;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {
    private Switch aSwitch;
    private TextView txtMode;
    private ProfileDatabase profileDatabase;
    private YuvAttr yuvAttr;
    private EditText name;
    private Button save;
    private TextInputEditText message;
    private EditText pinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileDatabase = new ProfileDatabase(this);
        aSwitch = (Switch) findViewById(R.id.mode);
        txtMode = (TextView) findViewById(R.id.txtmode);
        name = (EditText) findViewById(R.id.UpdateName);
        save = (Button) findViewById(R.id.save);
        message = (TextInputEditText) findViewById(R.id.messageYuv);
        pinView = (EditText) findViewById(R.id.PinV);
        yuvAttr = profileDatabase.getProfile(1);
        name.setText(yuvAttr.getName());
        message.setText(yuvAttr.getMessage());
        pinView.setText(""+yuvAttr.getPin()+"");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm = name.getText().toString();

                String msg = message.getText().toString();
                String pin = pinView.getText().toString();

                if (nm.equals("") && msg.equals("") && pin.equals("")){
                    Toast.makeText(ProfileActivity.this,"Please fill the required fields",Toast.LENGTH_SHORT).show();

                }
                else {
                    if (pin.length()==4){
                        profileDatabase.updateProfile(1, nm,Integer.parseInt(pin),msg);
                        Toast.makeText(ProfileActivity.this,"Profile Successfully Updated",Toast.LENGTH_SHORT).show();
                        MainActivity.helloUser.setText("Hello, "+nm+"");
                        finish();
                    }
                    else {

                        Toast.makeText(ProfileActivity.this,"Please enter 4 digit Yuv Pin",Toast.LENGTH_SHORT).show();
                        pinView.setText("");
                    }

                }
            }
        });

        //Color Mode codes start

        // Saving state of our app
        // using SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", true);

        // When user reopens the app
        // after applying dark/light mode

        if (isDarkModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            txtMode.setText("DARK");
            aSwitch.setChecked(false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            txtMode.setText("LIGHT");
            aSwitch.setChecked(true);
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (aSwitch.isChecked() && isDarkModeOn){
                    // if dark mode is on it
                    // will turn it off
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    // it will set isDarkModeOn
                    // boolean to false
                    editor.putBoolean("isDarkModeOn", false);
                    editor.apply();
                    // change text of Button
                    txtMode.setText("LIGHT");

                    //txtMode.setText("Disable Dark Mode");
                    //    aSwitch.setChecked(false);

                } else {

                    // if dark mode is off
                    // it will turn it on
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    // it will set isDarkModeOn
                    // boolean to true
                    editor.putBoolean("isDarkModeOn", true);
                    editor.apply();
                    // aSwitch.setChecked(true);
                    // change text of Button
                    txtMode.setText("DARK");
                    //txtMode.setText("Enable Dark Mode");

                }
            }
        });

        //----------------------------------------Color Mode codes end

    }
}