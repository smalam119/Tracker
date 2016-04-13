package com.smalam.pseudozero.trackme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import apputils.HandyFunctions;
import config.Config;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    TextView loggedInMessage;
    Button watchMeButton,setWatchersButton,requestButton,logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loggedInMessage = (TextView) findViewById(R.id.logged_in_message_text_viewMa);
        loggedInMessage.setText("syd is logged in");

        watchMeButton = (Button) findViewById(R.id.watch_me_button_main_activity);
        watchMeButton.setOnClickListener(this);
        setWatchersButton = (Button) findViewById(R.id.set_watchers_button_main_activity);
        setWatchersButton.setOnClickListener(this);
        requestButton = (Button) findViewById(R.id.requests_button_main_activity);
        requestButton.setOnClickListener(this);
        logOutButton = (Button) findViewById(R.id.log_out_button_main_activity);
        logOutButton.setOnClickListener(this);

        String s = HandyFunctions.readFromSharedPreferences(Config.SHARED_PREF_NAME,Config.LOGGEDIN_SHARED_PREF,this)+"";

        HandyFunctions.getLongToast(s,this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.watch_me_button_main_activity :
                break;

            case R.id.set_watchers_button_main_activity :
                break;

            case R.id.requests_button_main_activity :
                break;

            case R.id.log_out_button_main_activity :
                HandyFunctions.writeToSharedPreferencesBoolean(Config.SHARED_PREF_NAME, Config.LOGGEDIN_SHARED_PREF, false, this);
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
                break;
        }
    }
}
