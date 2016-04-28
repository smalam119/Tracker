package com.smalam.pseudozero.trackme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import apputils.HandyFunctions;
import config.Config;
import databaseHelpers.TalkToDB;
import gcmFiles.MyGCMRegistrationIntentService;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener
{
    TextView usernameTV;
    String userName;
    Button unregisterButton,myWatchersButton,requestsButton;
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


        myWatchersButton = (Button) findViewById(R.id.my_watchers_btn_profile);
        myWatchersButton.setOnClickListener(this);

        requestsButton = (Button) findViewById(R.id.requests_btn_profile);
        requestsButton.setOnClickListener(this);

        unregisterButton = (Button) findViewById(R.id.unreg_btn_profile);
        unregisterButton.setOnClickListener(this);


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

    protected void showDialog()
    {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Choose an option?")
                .setPositiveButton("My Requests", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        goToRequests("0","no");
                    }
                })

                .setNegativeButton("Request", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1)
                    {

                    }
                })
                .show();

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.my_watchers_btn_profile :

                goToRequests("1","yes");

                break;

            case R.id.requests_btn_profile :

                showDialog();

                break;

            case R.id.unreg_btn_profile:

                startRegistrationService(false,false);
                TalkToDB.deleteUser(userName,ProfileActivity.this); //not deleting from both users and watchers if watcher is empty
                returnToLogin();

                break;

        }
    }

    public void goToRequests(String type,String onlyDelete)
    {
        Intent i = new Intent(this,MyWatchersActivity.class);
        i.putExtra(MyWatchersActivity.TYPE,type);
        i.putExtra(MyWatchersActivity.ITEM_OPTION_TYPE,onlyDelete);
        startActivity(i);
    }


}
