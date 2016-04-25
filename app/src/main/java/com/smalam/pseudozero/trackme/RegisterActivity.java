package com.smalam.pseudozero.trackme;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import apputils.HandyFunctions;
import config.Config;
import databaseHelpers.RequestHandler;
import databaseHelpers.TalkToDB;
import databaseHelpers.UserNameGenarators;
import gcmFiles.MyGCMRegistrationIntentService;


public class RegisterActivity extends AppCompatActivity
{
    Button registerButton;
    EditText usernameEDRegister,passwordEDRegister,phoneNumberEDRegister;
    String rawUsername,password,phoneNumber,dialogBoxMessage,userName;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.register_button_register);
        usernameEDRegister = (EditText) findViewById(R.id.username_edit_text_register);
        passwordEDRegister = (EditText) findViewById(R.id.password_edit_text_register);
        phoneNumberEDRegister = (EditText) findViewById(R.id.phone_number_edit_text_register);

        builder = new AlertDialog.Builder(this);

        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id)
            {
                HandyFunctions.writeToSharedPreferencesBoolean(Config.SHARED_PREF_NAME,Config.LOGGED_IN_SHARED_PREF,true,getApplicationContext());
                //TalkToDB.addUser(HandyFunctions.readFromSharedPreferencesString(Config.SHARED_PREF_NAME,Config.USER_SHARED_PREF,getApplicationContext()),password,phoneNumber,RegisterActivity.this);

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String s= sharedPreferences.getString(Config.USER_SHARED_PREF, "cool");
                startRegistrationService(true,true,s);

                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                TalkToDB.editUserName(rawUsername,s,RegisterActivity.this);
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                rawUsername = usernameEDRegister.getText().toString().trim();
                password = passwordEDRegister.getText().toString().trim();
                phoneNumber = phoneNumberEDRegister.getText().toString().trim();
                //dialogBoxMessage = getResources().getString(R.string.registration_warning);

                TalkToDB.addUser(rawUsername, password, phoneNumber, RegisterActivity.this);
                getRawUsername(rawUsername);
            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }

    public void getRawUsername(final String rawUserName)
    {
        class GetUser extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                readRawUsername(s);

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_USER, rawUserName);
                return s;
            }
        }
        GetUser ge = new GetUser();
        ge.execute();
    }

    private void readRawUsername(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            int userID = c.getInt(Config.TAG_USER_ID);

            HandyFunctions.getLongToast(userID+"",getApplicationContext());

            userName = rawUsername+ UserNameGenarators.oddOrEven(userID)+UserNameGenarators.randomCharactor()+userID;

            HandyFunctions.writeToSharedPreferencesString(Config.SHARED_PREF_NAME,Config.USER_SHARED_PREF,userName,this);
            HandyFunctions.writeToSharedPreferencesString(Config.SHARED_PREF_NAME,Config.USER_PASSWORD_PREF,password,this);

            SharedPreferences sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Config.USER_SHARED_PREF, userName);
            editor.commit();

            builder.setMessage("Username: "+userName+"\n"+"Password: "+password)
                    .setTitle(R.string.please_save_these_credentials);
            AlertDialog dialog = builder.create();
            dialog.show();


        }

        catch (JSONException e)
        {
            e.printStackTrace();
            HandyFunctions.getLongToast("mr.anderson surprise to see me",getApplicationContext());
        }
    }

    public void startRegistrationService(boolean reg, boolean tkr,String userName)
    {

        if (Config.checkPlayServices(this)) {
            //mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
            //mRegistrationProgressBar.setVisibility(View.VISIBLE);
            //TextView tv = (TextView) findViewById(R.id.informationTextView);
            //if (reg) tv.setText(R.string.registering_message);
            //else tv.setText(R.string.unregistering_message);
            Toast.makeText(this, "Background service started...", Toast.LENGTH_LONG).show();
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, MyGCMRegistrationIntentService.class);
            intent.putExtra("register", reg);
            intent.putExtra("tokenRefreshed", tkr);
            intent.putExtra("userName",userName);
            startService(intent);
        }

    }

}
