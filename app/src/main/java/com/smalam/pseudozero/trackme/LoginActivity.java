package com.smalam.pseudozero.trackme;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import apputils.HandyFunctions;
import config.Config;
import databaseHelpers.RequestHandler;

public class LoginActivity extends AppCompatActivity
{
    EditText usernameETLogin, passwordETLogin;
    Button loginButtonLogin, registerButtonLogin;
    private boolean loggedIn;
    String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameETLogin = (EditText) findViewById(R.id.username_edit_text_login);
        passwordETLogin = (EditText) findViewById(R.id.password_edit_text_login);
        loginButtonLogin = (Button) findViewById(R.id.login_button_login);
        registerButtonLogin = (Button) findViewById(R.id.register_button_login);

        loginButtonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                username = usernameETLogin.getText().toString().trim();
                password = passwordETLogin.getText().toString().trim();
                if(!username.isEmpty() && !password.isEmpty())
                {
                    loginUser(LoginActivity.this, username,password);

                }
                else
                {
                    HandyFunctions.getLongToast("Enter both username and password", LoginActivity.this);
                }

            }
        });

        registerButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    protected void onResume()
    {
        super.onResume();

        loggedIn = HandyFunctions.readFromSharedPreferencesBoolean(Config.SHARED_PREF_NAME,Config.LOGGED_IN_SHARED_PREF,this);

        HandyFunctions.getLongToast(loggedIn+"",getApplicationContext());

        //If we will get true
        if(loggedIn)
        {
            //We will start the Profile Activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }

    public void loginUser(final Activity activity, final String userName, final String givenPassword)
    {
        class GetUser extends AsyncTask<Void,Void,String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                loading = ProgressDialog.show(activity,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();
                matchPassword(activity, s, givenPassword);
            }

            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_USER, userName);
                return s;
            }
        }
        GetUser ge = new GetUser();
        ge.execute();
    }

    private void matchPassword(Context context, String json, final String givenPassword)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String password = c.getString(Config.TAG_PASSWORD);

            if(password.equals("null"))
            {
                Toast.makeText(context, "No user found", Toast.LENGTH_LONG).show();
            }
            else if(givenPassword.equals(password))
            {
                HandyFunctions.writeToSharedPreferencesBoolean(Config.SHARED_PREF_NAME,Config.LOGGED_IN_SHARED_PREF,true,this);
                HandyFunctions.writeToSharedPreferencesString(Config.SHARED_PREF_NAME,Config.USER_SHARED_PREF,username,this);
                HandyFunctions.writeToSharedPreferencesString(Config.SHARED_PREF_NAME,Config.USER_PASSWORD_PREF,password,this);

                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(context, "wrong password", Toast.LENGTH_LONG).show();
            }


        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
