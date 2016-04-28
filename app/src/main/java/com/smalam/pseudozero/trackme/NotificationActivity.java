package com.smalam.pseudozero.trackme;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import apputils.HandyFunctions;
import config.Config;
import databaseHelpers.TalkToDB;

public class NotificationActivity extends Activity implements View.OnClickListener
{
    TextView tv;
    public static final String DATA = "requester";
    String requester;
    Button acceptButton,cancelButton;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        userName = HandyFunctions.readFromSharedPreferencesString(Config.SHARED_PREF_NAME,Config.USER_SHARED_PREF,getApplicationContext());

        acceptButton = (Button) findViewById(R.id.accept_button_notification);
        acceptButton.setOnClickListener(this);

        cancelButton = (Button) findViewById(R.id.cancel_button_notification);
        cancelButton.setOnClickListener(this);

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
                TalkToDB.acceptWatchers(requester,userName,NotificationActivity.this,"1");
                Toast.makeText(this,requester + userName,Toast.LENGTH_LONG).show();
                break;

            case R.id.cancel_button_notification :
                TalkToDB.declineRequest(requester,userName,NotificationActivity.this);
                break;

            case R.id.leave_it_pending_button_notification:
                //
                break;
        }
    }
}