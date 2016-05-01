package com.smalam.pseudozero.trackme;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import apputils.HandyFunctions;
import config.Config;
import databaseHelpers.TalkToDB;

public class WatchMeActivity extends FragmentActivity implements LocationSource {

    private GoogleMap mMap;
    private OnLocationChangedListener onLocationChangedListener;
    private android.location.LocationListener locationListener;
    private LocationManager locationManager;
    String userName;
    Button inDangerButton,outOfDangerButton,trackMeButton;
    public boolean isInDanger = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_me);

        userName = HandyFunctions.readFromSharedPreferencesString(Config.SHARED_PREF_NAME,Config.USER_SHARED_PREF,getApplicationContext());

        trackMeButton = (Button) findViewById(R.id.track_me);
        trackMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                TalkToDB.sendTrackRequest(userName,"a0j126",WatchMeActivity.this);
            }
        });

        inDangerButton = (Button) findViewById(R.id.in_danger);
        inDangerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                isInDanger = true;
            }
        });

        outOfDangerButton = (Button) findViewById(R.id.out_of_danger);
        outOfDangerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                isInDanger = false;

                CustomDialogBox customDialogBox = new CustomDialogBox(WatchMeActivity.this);
                customDialogBox.show();
            }
        });


        Toast.makeText(getApplicationContext(),userName,Toast.LENGTH_LONG).show();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new MyLocationListener();
        if (locationManager != null) {
            boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkIsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (gpsIsEnabled)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 2000L, 5F, locationListener);
            }
            else if (networkIsEnabled)
            {
                locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 2000L, 5F, locationListener);
            }
            else
            {
                //Show an error dialog that GPS is disabled...
            }
        }
        else
        {
            //Show some generic error dialog because something must have gone wrong with location manager.
        }

        setUpMapIfNeeded();

    }

    @Override
    public void onPause() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(locationListener);
        }

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        setUpMapIfNeeded();

        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        this.onLocationChangedListener = null;
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.

            if (mMap != null) {
                setUpMap();
            }

            //This is how you register the LocationSource
            mMap.setLocationSource(this);
        }
    }

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    class MyLocationListener implements android.location.LocationListener
    {
        @Override
        public void onLocationChanged(Location location)
        {
            if( onLocationChangedListener != null )
            {
                onLocationChangedListener.onLocationChanged(location);

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()),18f));

                //TalkToDB.updateLocation(userName,"123456789","987654321",WatchMeActivity.this);

                Toast.makeText(getApplicationContext(),"Location Changed",Toast.LENGTH_LONG).show();

                if(isInDanger)
                {
                    TalkToDB.updateLocation(userName, location.getLatitude() + "", location.getLongitude() + "","true", WatchMeActivity.this);
                }

                if(!isInDanger)
                {
                    TalkToDB.updateLocation(userName, location.getLatitude() + "", location.getLongitude() + "","false", WatchMeActivity.this);
                }
            }
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "provider disabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "provider enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "status changed", Toast.LENGTH_SHORT).show();
        }
    }
}
