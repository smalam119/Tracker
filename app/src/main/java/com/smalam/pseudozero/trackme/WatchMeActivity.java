package com.smalam.pseudozero.trackme;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class WatchMeActivity extends FragmentActivity implements LocationSource {

    private GoogleMap mMap;
    private OnLocationChangedListener onLocationChangedListener;
    private android.location.LocationListener locationListener;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new MyLocationListener();
        if (locationManager != null) {
            boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkIsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (gpsIsEnabled) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 2000L, 5F, locationListener);
            } else if (networkIsEnabled) {
                locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 2000L, 5F, locationListener);
            } else {
                //Show an error dialog that GPS is disabled...
            }
        } else {
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
                onLocationChangedListener.onLocationChanged( location );

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()),18f));

                Toast.makeText(getApplicationContext(),"Location Changed",Toast.LENGTH_LONG).show();
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
