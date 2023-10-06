package com.softblxgenesis.yuvti;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import android.widget.TextView;
import android.widget.Toast;

import com.softblxgenesis.yuvti.MenuOptions.AboutActivity;
import com.softblxgenesis.yuvti.ProfileDetailsDB.ProfileDatabase;
import com.softblxgenesis.yuvti.ProfileDetailsDB.YuvAttr;
import com.softblxgenesis.yuvti.SendSMS.AlertActivity;
import com.softblxgenesis.yuvti.MenuOptions.ProfileActivity;
import com.softblxgenesis.yuvti.SaveLocation.FatchDataActivity;
import com.softblxgenesis.yuvti.SaveLocation.SavedLocationActivity;
import com.softblxgenesis.yuvti.SendAlertSMSNotification.AppGlobalVar;
import com.softblxgenesis.yuvti.SendAlertSMSNotification.SendNotification;
import com.softblxgenesis.yuvti.SendSMS.SqliteDatabase;
import com.imankur.analogclockview.AnalogClock;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {
    public static String city;
    public static String state;
    public static String country;
    public static String pin;
    public static String locality;
    public static double latti;
    public static double longi;
    LocationManager locationManager;
    TextView tvLocality;
    private SqliteDatabase mDatabase;
    public static CardView btngetStarted;
    public static CardView btnstopSharing;
    public static CardView btnsetReminder;
    public static TextView Heading;
    int ContactSize;
    public static  TextView helloUser;

    private ProfileDatabase profileDatabase;
    private YuvAttr yuvAttr;

    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                locationEnabled();
                getLocation();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        profileDatabase = new ProfileDatabase(this);
        helloUser= (TextView) findViewById(R.id.helloUser);

        yuvAttr = profileDatabase.getProfile(1);
        helloUser.setText("Hello, "+yuvAttr.getName());
        android.widget.AnalogClock ang = (android.widget.AnalogClock) findViewById(R.id.AnalogClock2);
        AnalogClock analogClock = (AnalogClock) findViewById(R.id.AnalogClock1);
        analogClock.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                analogClock.setVisibility(View.GONE);
                ang.setVisibility(View.VISIBLE);
                return true;

            }
        });
        ang.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ang.setVisibility(View.GONE);
                analogClock.setVisibility(View.VISIBLE);
                return true;
            }
        });

        findViewById(R.id.digitalClock1);
        tvLocality = (TextView) findViewById(R.id.tvLocality);

        //when GET STARTED clicked
        btngetStarted = (CardView) findViewById(R.id.getStarted);
        mDatabase = new SqliteDatabase(this);

        Heading = (TextView) findViewById(R.id.showHeading);

        btngetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactSize = mDatabase.AllPhoneNo().size();
                if (!(ContactSize==0) && !tvLocality.getText().equals("Searching...")){
                    Intent intent=new Intent(MainActivity.this, SendNotification.class);
                    startActivity(intent);
                } else if (ContactSize==0 ){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Add Phone Numbers")
                            .setMessage("We need phone numbers to send alerts")
                            .setCancelable(false)
                            .setPositiveButton("Add", new
                                    DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                            startActivity(new Intent(MainActivity.this,AlertActivity.class));
                                        }
                                    })
                            .setNegativeButton("Cancel",null)
                            .show();
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Searching Location")
                            .setMessage("Please wait for searching location or check your location permission")
                            .setCancelable(false)
                            .setPositiveButton("OK",null)
                            .show();

                }

            }
        });

        btnsetReminder = (CardView) findViewById(R.id.setReminder);
        btnsetReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactSize = mDatabase.AllPhoneNo().size();
                if (!(ContactSize==0) && !tvLocality.getText().equals("Searching...")){
                    openActivitySetReminder();
                } else if (ContactSize==0 ){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Add Phone Numbers")
                            .setMessage("We need phone numbers to send alerts")
                            .setCancelable(false)
                            .setPositiveButton("Add", new
                                    DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                            startActivity(new Intent(MainActivity.this,AlertActivity.class));
                                        }
                                    })
                            .setNegativeButton("Cancel",null)
                            .show();
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Searching Location")
                            .setMessage("Please wait for searching location or check your location permission")
                            .setCancelable(false)
                            .setPositiveButton("OK",null)
                            .show();
                }

            }
        });


        //when STOP SHARING clicked
        btnstopSharing = (CardView) findViewById(R.id.stopSharing);
        btnstopSharing.setVisibility(View.GONE);
        btnstopSharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppGlobalVar.getHandler().removeCallbacks(AppGlobalVar.getRunnable());
                Toast.makeText(MainActivity.this, "Yuvti has been Deactivated", Toast.LENGTH_LONG).show();
                btngetStarted.setVisibility(View.VISIBLE);
                btnsetReminder.setVisibility(View.VISIBLE);
                btnstopSharing.setVisibility(View.GONE);
                Heading.setText("Time to leave?");

            }
        });

        CardView policeS = (CardView) findViewById(R.id.nearByPoliceS);
        policeS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("https://www.google.co.in/maps/search/Police+Station");
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (ActivityNotFoundException e){
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationEnabled();
        getLocation();



        CardView sLocation = (CardView) findViewById(R.id.savedLocation);
        sLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivitySavedLocation();
            }
        });

        CardView button = (CardView) findViewById(R.id.mbtn);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, button);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.one){
                            Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
                            startActivity(intent);
                        } else if (item.getItemId() == R.id.two){
                            Intent intent=new Intent(MainActivity.this, AlertActivity.class);
                            startActivity(intent);
                        } else if (item.getItemId() == R.id.three) {
                            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    }


                });

                popup.show();//showing popup menu
            }
        });


        //---------------------start of save share live locationb
        CardView btn = (CardView) findViewById(R.id.SSLocation);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, btn);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu2, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()== R.id.saveLL){
                            if (!tvLocality.getText().equals("Searching...")) {
                                Intent intent=new Intent(MainActivity.this, SavedLocationActivity.class);
                                startActivity(intent);
                            } else{
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Searching Location")
                                        .setMessage("Please wait for searching location or check your location permission")
                                        .setCancelable(false)
                                        .setPositiveButton("OK",null)
                                        .show();
                            }

                        }
                        else if (item.getItemId() == R.id.shareLL) {
                            if (!tvLocality.getText().equals("Searching...")) {
                                Intent shareintent = new Intent();
                                shareintent.setAction(Intent.ACTION_SEND);
                                shareintent.putExtra(Intent.EXTRA_TEXT, "Shared from Yuvti Location\n\n "+locality+ "\n\nAdject Point\nhttp://maps.google.com/maps?q=loc:"+latti+","+longi);
                                shareintent.setType("text/plain");
                                startActivity(Intent.createChooser(shareintent, "Share vai"));
                            } else{
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Searching Location")
                                        .setMessage("Please wait for searching location or check your location permission")
                                        .setCancelable(false)
                                        .setPositiveButton("OK",null)
                                        .show();
                            }
                        }
                        return true;
                    }


                });

                popup.show();//showing popup menu
            }
        });

        //---------------------end of save share live locationb





    }
    private void openActivitySetReminder()
    {
        Intent intent=new Intent(this, SetReminderActivity.class);
        startActivity(intent);
    }
    private void openActivitySavedLocation()
    {
        Intent intent=new Intent(this, FatchDataActivity.class);
        startActivity(intent);
    }
    private void locationEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Enable GPS Service")
                    .setMessage("We need your GPS location for take care of you")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

         /*   tvCity.setText(addresses.get(0).getLocality()); tvState.setText(addresses.get(0).getAdminArea());
              tvCountry.setText(addresses.get(0).getCountryName()); tvPin.setText(addresses.get(0).getPostalCode()); */

            tvLocality.setText(addresses.get(0).getAddressLine(0));
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            pin = addresses.get(0).getPostalCode();
            locality = addresses.get(0).getAddressLine(0);
            longi = location.getLongitude();
            latti = location.getLatitude();

        } catch (Exception e) {
        }
    }

    public static String getAddressesLocality() {
        return locality;
    }

    public static String getAddressesPin() {
        return pin;
    }

    public static String getAddressesCountry() {
        return country;
    }

    public static String getAddressesState() {
        return state;
    }

    public static String getAddressesCity() {
        return city;
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
