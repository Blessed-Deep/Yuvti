package com.softblxgenesis.yuvti.SaveLocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.softblxgenesis.yuvti.R;

public class ViewItemSavedLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_saved_location);

        TextView shortName = (TextView) findViewById(R.id.shortName);
        TextView idCity = (TextView) findViewById(R.id.IDCity);
        TextView idState = (TextView) findViewById(R.id.IDState);
        TextView idCountry = (TextView) findViewById(R.id.IDCountry);
        TextView idPin = (TextView) findViewById(R.id.IDPin);
        TextView idLocality = (TextView) findViewById(R.id.IDLocality);

        Intent i = getIntent();
        String sName = i.getStringExtra("name");
        shortName.setText(sName);
        String city = i.getStringExtra("city");
        idCity.setText(city);
        String state = i.getStringExtra("state");
        idState.setText(state);
        String country = i.getStringExtra("country");
        idCountry.setText(country);
        String pin = i.getStringExtra("pin");
        idPin.setText(pin);
        String locality = i.getStringExtra("locality");
        idLocality.setText(locality);

    }
}