package com.smalam.pseudozero.trackme;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import config.Config;
import databaseHelpers.RequestHandler;

public class WatchersMapsActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;
    Marker marker;
    public Handler handler;
    public Runnable runnable;
    public boolean isInDanger = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchers_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        handler = new Handler();
        runnable = new MyRunnable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        //getWatchedsLocation(WatchersMapsActivity.this);
        watchedsLocationUpdate();
    }

    private void goToLocation(double lat, double lng, float zoom)
    {
        LatLng latLng = new LatLng(lat,lng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,zoom);
        mMap.moveCamera(cameraUpdate);
        setMarker(getLocation(latLng),lat,lng);
    }

    private void setMarker(String locality, double lat, double lng)
    {

        if (marker != null) {
            marker.remove();
        }

        if(isInDanger == false)
        {

            MarkerOptions options = new MarkerOptions()
                    .title(locality)
                    .position(new LatLng(lat, lng))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            marker = mMap.addMarker(options);
        }

        if(isInDanger == true)
        {

            MarkerOptions options = new MarkerOptions()
                    .title(locality)
                    .position(new LatLng(lat, lng))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            marker = mMap.addMarker(options);
        }




    }

    private String getLocation(LatLng latLng)
    {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = null;
            addressList = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            Address add = addressList.get(0);
            return add.getLocality();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getWatchedsLocation(final Activity activity)
    {
        class GetUser extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(activity,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                readLatLng(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_WATCHED_LOCATION, "sydCool0f108");
                return s;
            }
        }
        GetUser ge = new GetUser();
        ge.execute();
    }

    private void readLatLng(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String lat = c.getString(Config.TAG_CURRENT_LAT_);
            String lng = c.getString(Config.TAG_CURRENT_LNG_);
            String isInDangerFromDb = c.getString(Config.TAG_IS_IN_DANGER);

            if(isInDangerFromDb.equalsIgnoreCase("true"))
            {
                isInDanger = true;
            }

            Toast.makeText(getApplicationContext(),lat+" "+lng+" "+isInDanger+"",Toast.LENGTH_LONG).show();

            goToLocation( Double.parseDouble(lat),  Double.parseDouble(lng),15.0f);
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public class MyRunnable implements Runnable
    {

        @Override
        public void run()
        {
            //HandyFunctions.getShortToast("its working",getApplicationContext());
            getWatchedsLocation(WatchersMapsActivity.this);
            handler.postDelayed(runnable,5000);
        }
    }

    public void watchedsLocationUpdate()
    {
        runnable.run();
    }
}
