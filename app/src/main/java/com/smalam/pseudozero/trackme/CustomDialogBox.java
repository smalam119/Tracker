package com.smalam.pseudozero.trackme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import apputils.HandyFunctions;
import config.Config;

public class CustomDialogBox extends Dialog implements View.OnClickListener
{
    public CustomDialogBox(Context context)
    {
        super(context);
    }

    public Activity c;
    public Dialog d;
    public Button okayButton;
    public EditText passwordET;
    public String password,givenPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alart_dialogbox);

        okayButton = (Button) findViewById(R.id.okay_button_dialog);
        okayButton.setOnClickListener(this);
        passwordET = (EditText) findViewById(R.id.password_dialog);


    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.okay_button_dialog:

                password = HandyFunctions.readFromSharedPreferencesString(Config.SHARED_PREF_NAME,Config.USER_PASSWORD_PREF,getContext());
                givenPassword = passwordET.getText().toString().trim();
                HandyFunctions.getLongToast(password,getContext());
                if(password.equals(givenPassword))
                {
                    HandyFunctions.getLongToast("okay",getContext());
                }
                break;
            default:
                break;
        }
        dismiss();
    }
}
