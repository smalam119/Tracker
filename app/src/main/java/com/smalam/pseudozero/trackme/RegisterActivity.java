package com.smalam.pseudozero.trackme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegisterActivity extends AppCompatActivity
{
    Button registerButton;
    EditText usernameEDRegister,passwordEDRegister;
    String username,password,dialogBoxMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.register_button_register);
        usernameEDRegister = (EditText) findViewById(R.id.username_edit_text_register);
        passwordEDRegister = (EditText) findViewById(R.id.password_edit_text_register);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id)
            {
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                username = usernameEDRegister.getText().toString();
                password = passwordEDRegister.getText().toString();
                dialogBoxMessage = getResources().getString(R.string.registration_warning);

                builder.setMessage(dialogBoxMessage+"\n"+"\n"+"User Name: "+username+"\n"+"Password: "+password)
                        .setTitle(R.string.register_with_phone_number);

                final AlertDialog dialog = builder.create();

                dialog.show();
            }
        });
    }

}
