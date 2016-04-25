package gcmFiles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smalam.pseudozero.trackme.R;

import java.util.HashMap;

import apputils.HandyFunctions;
import config.Config;
import databaseHelpers.RequestHandler;

public class MainActivityTest extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivityTest";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;
    Button add;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        userName = HandyFunctions.readFromSharedPreferencesString(Config.SHARED_PREF_NAME,Config.USER_SHARED_PREF,this);

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);

        Button rtbtn = (Button) findViewById(R.id.ret_btn);
        Button unreg_btn = (Button) findViewById(R.id.unreg_btn);
        rtbtn.setOnClickListener(this);
        unreg_btn.setOnClickListener(this);

        //GCM related part

        mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(Config.SENT_TOKEN_TO_SERVER, false);
                String tkmsg = "\nRegistration ID from GCM: " +
                        sharedPreferences.getString(Config.REG_ID, "N/A");
                tkmsg = getString(R.string.gcm_send_message) + tkmsg;
                if (!intent.getExtras().getBoolean("register"))
                    tkmsg = getString(R.string.gcm_unregister_message);
                tkmsg = intent.getStringExtra("prefix") + tkmsg;
                if (sentToken) {
                    mInformationTextView.setText(tkmsg);
                } else {
                    mInformationTextView.setText(getString(R.string.token_error_message) + tkmsg);
                }
            }
        };
        mInformationTextView = (TextView) findViewById(R.id.informationTextView);

        startRegistrationService(true, false);
        //GCM related part end

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ret_btn) {
            startRegistrationService(true, true);
        }
        if (view.getId() == R.id.unreg_btn) {
            //try to unregister
            startRegistrationService(false, false);
        }
        if (view.getId() == R.id.add)
        {
            sendRequest(userName,userName, MainActivityTest.this);
        }

    }

    public void startRegistrationService(boolean reg, boolean tkr)
    {

        if (Config.checkPlayServices(this)) {
            mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
            mRegistrationProgressBar.setVisibility(View.VISIBLE);
            TextView tv = (TextView) findViewById(R.id.informationTextView);
            if (reg) tv.setText(R.string.registering_message);
            else tv.setText(R.string.unregistering_message);
            Toast.makeText(this, "Background service started...", Toast.LENGTH_LONG).show();
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, MyGCMRegistrationIntentService.class);
            intent.putExtra("register", reg);
            intent.putExtra("tokenRefreshed", tkr);
            startService(intent);
        }

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
