package com.smalam.pseudozero.trackme;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import apputils.HandyFunctions;
import config.Config;
import databaseHelpers.TalkToDB;

public class NotificationActivity extends Activity implements View.OnClickListener
{
    TextView tv;
    public static final String DATA = "requester";
    String requester;
    Button acceptButton;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        userName = HandyFunctions.readFromSharedPreferencesString(Config.SHARED_PREF_NAME,Config.USER_SHARED_PREF,getApplicationContext());

        acceptButton = (Button) findViewById(R.id.accept_button_notification);
        acceptButton.setOnClickListener(this);

        requester = String.valueOf(getIntent().getExtras().get(DATA));

        tv = (TextView) findViewById(R.id.data);
        tv.setText(requester + " has send you a request to be a watcher");




    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.accept_button_notification :
                TalkToDB.addWatchers(requester,userName,NotificationActivity.this);
        }
    }
}