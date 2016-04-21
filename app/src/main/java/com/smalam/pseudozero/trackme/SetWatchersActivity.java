package com.smalam.pseudozero.trackme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import apputils.HandyFunctions;
import config.Config;
import databaseHelpers.TalkToDB;

public class SetWatchersActivity extends AppCompatActivity
{

    EditText watchersNameEditTextET;
    Button addWatchersButton;
    String userName,watcherName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_watchers);

        userName = HandyFunctions.readFromSharedPreferencesString(Config.SHARED_PREF_NAME,Config.USER_SHARED_PREF,getApplicationContext());

        watchersNameEditTextET = (EditText) findViewById(R.id.add_watchers_edit_text_set_watchers);
        addWatchersButton = (Button) findViewById(R.id.add_watchers_button_set_watchers);
        addWatchersButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                watcherName = watchersNameEditTextET.getText().toString();

                TalkToDB.addWatchers(userName,watcherName,SetWatchersActivity.this);

                HandyFunctions.writeToSharedPreferencesString(Config.SHARED_PREF_NAME,Config.WATCHER_SHARED_PREF,watcherName,getApplicationContext());
            }
        });
    }
}
