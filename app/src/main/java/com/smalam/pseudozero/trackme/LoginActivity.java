package com.smalam.pseudozero.trackme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import apputils.HandyFunctions;
import databaseHelpers.TalkToDB;

public class LoginActivity extends AppCompatActivity
{
    EditText usernameETLogin, passwordETLogin;
    Button loginButtonLogin, registerButtonLogin;

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
                String username = usernameETLogin.getText().toString().trim();
                String password = passwordETLogin.getText().toString().trim();
                if(!username.isEmpty() && !password.isEmpty())
                {
                    TalkToDB.loginUser(LoginActivity.this, username,password);
                }
                else
                {
                    HandyFunctions.getLongToast("Enter both username and password",LoginActivity.this);
                }

            }
        });

        registerButtonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
            }
        });
    }

}
