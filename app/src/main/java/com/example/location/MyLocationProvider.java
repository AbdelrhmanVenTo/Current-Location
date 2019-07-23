package com.example.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import java.util.List;

public class MyLocationProvider {

    LocationManager locationManager;
    Location location;
    boolean canGetLocation;
    Context context;


    public MyLocationProvider(Context context) {
        this.context = context;
        location=null;
        locationManager =(LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
    }

    public boolean isGpsEnabled(){
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }



    @SuppressWarnings("MissingPermission")
    public Location getUserLocation(){
        String provider=null;

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            provider=LocationManager.GPS_PROVIDER;
        else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            provider=LocationManager.NETWORK_PROVIDER;
        if(provider==null){
            canGetLocation=false;
            location=null;
            return null;
        }


        location= locationManager.getLastKnownLocation(provider);

        if(location==null)
            location=getBestLastKnownLocation();

        return location;
    }

    @SuppressWarnings("MissingPermission")
    private Location getBestLastKnownLocation() {
        List<String>providers =locationManager.getProviders(true);
        Location bestLocation=null;
        for(String provider : providers){
            Location l=locationManager.getLastKnownLocation(provider);
            if(bestLocation==null&&l !=null){
                bestLocation=l;
                continue;
            }else if(bestLocation!=null&& l!=null){
                if(l.getAccuracy()<bestLocation.getAccuracy())
                    bestLocation=l;
            }
        }
        return bestLocation;

    }

}