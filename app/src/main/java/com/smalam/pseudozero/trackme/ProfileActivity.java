package com.smalam.pseudozero.trackme;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import apputils.HandyFunctions;
import config.Config;
import databaseHelpers.RequestHandler;
import databaseHelpers.TalkToDB;
import gcmFiles.MyGCMRegistrationIntentService;

public class ProfileActivity extends AppCompatActivity implements ListView.OnItemClickListener
{
    TextView usernameTV;
    ListView watcherListView;
    private String JSON_STRING;
    String userName;
    Button unregisterButton;
    private ProgressBar mRegistrationProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        userName = HandyFunctions.readFromSharedPreferencesString(Config.SHARED_PREF_NAME,Config.USER_SHARED_PREF,this);
        usernameTV = (TextView) findViewById(R.id.username_text_view_profile);
        usernameTV.setText(userName);
        unregisterButton = (Button) findViewById(R.id.unreg_btn_profile);
        unregisterButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startRegistrationService(false,false);
                TalkToDB.deleteUser(userName,ProfileActivity.this); //not deleting from both users and watchers if watcher is empty
                returnToLogin();
            }
        });
        watcherListView = (ListView) findViewById(R.id.watchers_list_view_profile);
        watcherListView.setOnItemClickListener(this);
        getAllWatchers();
    }

    private void getAllWatchers(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ProfileActivity.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showWatchers();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_ALL_WATCHERS,userName);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showWatchers(){
        JSONObject jsonObject = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = null;
                try {
                    jo = result.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String isAccepted = jo.getString(Config.TAG_IS_ACCEPTED);
                String watcherName = jo.getString(Config.TAG_WATCHER_NAME);

                if(isAccepted.equals("1"))
                {
                    list.add(watcherName);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> simpleAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list);

        watcherListView.setAdapter(simpleAdapter);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        String watcherName = parent.getItemAtPosition(position).toString();

        Toast.makeText(this,watcherName,Toast.LENGTH_LONG).show();

        exitByBackKey(watcherName);
    }

    protected void exitByBackKey(String watcherName)
    {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to delete " + watcherName +" as a watcher ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1)
                    {

                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1)
                    {

                    }
                })
                .show();

    }

    public void startRegistrationService(boolean reg, boolean tkr)
    {

        if (Config.checkPlayServices(this)) {
            Toast.makeText(this, "Background service started...", Toast.LENGTH_LONG).show();
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, MyGCMRegistrationIntentService.class);
            intent.putExtra("register", reg);
            intent.putExtra("tokenRefreshed", tkr);
            startService(intent);
        }

    }

    public void returnToLogin()
    {
        HandyFunctions.writeToSharedPreferencesBoolean(Config.SHARED_PREF_NAME, Config.LOGGED_IN_SHARED_PREF, false, this);
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }
}
