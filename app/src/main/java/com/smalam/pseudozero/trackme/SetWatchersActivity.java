package com.smalam.pseudozero.trackme;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import apputils.HandyFunctions;
import config.Config;
import databaseHelpers.RequestHandler;
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

                sendRequest(userName,watcherName, SetWatchersActivity.this);
                TalkToDB.addWatchers(userName,watcherName,SetWatchersActivity.this);
            }
        });
    }

    public static void sendRequest(final String userName, final String watcherName, final Activity activity)
    {


        class AddWatcher extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(activity,"Registering...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(activity, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_USER_NAME,userName);
                params.put(Config.KEY_WATCHER,watcherName);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_NOTIFICATION_SERVER_SEND_REQUEST, params);
                return res;
            }
        }

        AddWatcher ae = new AddWatcher();
        ae.execute();
    }
}
