package com.example.location;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.location.Base.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION_CODE = 500;
    MyLocationProvider myLocationProvider;
    Location currentLocation;
    TextView getUserLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUserLocation = findViewById(R.id.textViewLocation);


        if(isLocationPermissionsGranted()){
            //call function
            showUserLocation();
        }else {
            requestLocationPermission();
        }
    }


    private void requestLocationPermission() {
        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.+
            showConfirmationMessageInt(R.string.warning,
                    R.string.message_request_location_permission,
                    R.string.ok, new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            ActivityCompat.requestPermissions(activity,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION_CODE);
                        }
                    });

        } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION_CODE);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //call your function
                    showUserLocation();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(activity, "Cannot Access Your Location", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    public void showUserLocation(){

        String languageToLoad = "ar"; // your language
        if(myLocationProvider==null)
            myLocationProvider=new MyLocationProvider(activity);
        if(!myLocationProvider.isGpsEnabled()){
            showConfirmationMessageInt(R.string.warning, R.string.please_enable_gps, R.string.ok, new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    //open Settings to enable Gps
                    //    Intent intent=Intent
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            return;
        }

        currentLocation=myLocationProvider.getUserLocation();

        Geocoder geocoder;
        List<Address> addresses = new ArrayList();
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            Locale mLocale = new Locale(languageToLoad);
            Locale.setDefault(mLocale);
            geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String city = addresses.get(0).getLocality();
        String country = addresses.get(0).getCountryName();



        if(currentLocation==null){
            showMessageInt(R.string.warning,R.string.cannot_access_location);
        }else {
            getUserLocation.setText(country+" - "+city);

            }
        }




    public boolean isLocationPermissionsGranted(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

}
