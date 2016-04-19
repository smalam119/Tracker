package com.smalam.pseudozero.trackme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import apputils.HandyFunctions;
import config.Config;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    TextView loggedInMessage;
    Button watchMeButton,setWatchersButton,requestButton,profileButton,logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String userName = HandyFunctions.readFromSharedPreferencesString(Config.SHARED_PREF_NAME,Config.USER_SHARED_PREF,this);

        loggedInMessage = (TextView) findViewById(R.id.logged_in_message_text_viewMa);
        loggedInMessage.setText(userName+" is logged in");

        watchMeButton = (Button) findViewById(R.id.watch_me_button_main_activity);
        watchMeButton.setOnClickListener(this);
        setWatchersButton = (Button) findViewById(R.id.set_watchers_button_main_activity);
        setWatchersButton.setOnClickListener(this);
        requestButton = (Button) findViewById(R.id.requests_button_main_activity);
        requestButton.setOnClickListener(this);
        profileButton = (Button) findViewById(R.id.profile_button_main_activity);
        profileButton.setOnClickListener(this);
        logOutButton = (Button) findViewById(R.id.log_out_button_main_activity);
        logOutButton.setOnClickListener(this);

        String s = HandyFunctions.readFromSharedPreferencesBoolean(Config.SHARED_PREF_NAME,Config.LOGGEDIN_SHARED_PREF,this)+"";

        HandyFunctions.getLongToast(s,this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        //finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        exitByBackKey();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.watch_me_button_main_activity :

                goToWatchMe();

                break;

            case R.id.set_watchers_button_main_activity :

                goToWatchers();

                break;

            case R.id.requests_button_main_activity :
                break;

            case R.id.profile_button_main_activity:
                break;

            case R.id.log_out_button_main_activity :
               returnToLogin();
                break;
        }
    }

    protected void exitByBackKey()
    {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();
                        //close();


                    }
                })

                .setNeutralButton("Log Out", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        returnToLogin();
                    }
                })


                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }

    public void returnToLogin()
    {
        HandyFunctions.writeToSharedPreferencesBoolean(Config.SHARED_PREF_NAME, Config.LOGGEDIN_SHARED_PREF, false, this);
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }

    public void goToWatchMe()
    {
        Intent i = new Intent(this,WatchMeActivity.class);
        startActivity(i);
    }

    public void goToWatchers()
    {
        Intent i = new Intent(this,WatchersMapsActivity.class);
        startActivity(i);
    }
}
