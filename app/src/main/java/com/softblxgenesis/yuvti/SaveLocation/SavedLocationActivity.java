package com.softblxgenesis.yuvti.SaveLocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.softblxgenesis.yuvti.MainActivity;
import com.softblxgenesis.yuvti.R;
import com.google.android.material.textfield.TextInputEditText;

public class SavedLocationActivity extends AppCompatActivity {
    TextInputEditText inName;
    String  city, state, country, pin, locality;
    Button okSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_location);

        inName = (TextInputEditText) findViewById(R.id.inName);

        okSave= (Button) findViewById(R.id.okSave);

        city = MainActivity.getAddressesCity();
        state= MainActivity.getAddressesState();
        country = MainActivity.getAddressesCountry();
        pin = MainActivity.getAddressesPin();
        locality= MainActivity.getAddressesLocality();

        okSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(inName.getText().toString().isEmpty())){
                    ProcessInsert(inName.getEditableText().toString(), city, state, country, pin, locality);
                    inName.setText("");
                    finish();

                }
                else {
                    Toast.makeText(getApplicationContext(),"Please enter a short name of the place",Toast.LENGTH_LONG).show();
                }
                }
        });
    }

    private void ProcessInsert(String toString, String city, String state, String country, String pin, String locality) {
        String res=new dbmanager(this).addrecord(toString, city, state, country, pin, locality);
        Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();

    }

    public void BackToHomePage(View view) {
        finish();
    }
}