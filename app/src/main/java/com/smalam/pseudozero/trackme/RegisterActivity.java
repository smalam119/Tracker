package com.smalam.pseudozero.trackme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import databaseHelpers.TalkToDB;


public class RegisterActivity extends AppCompatActivity
{
    Button registerButton;
    EditText usernameEDRegister,passwordEDRegister,phoneNumberEDRegister;
    String username,password,phoneNumber,dialogBoxMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.register_button_register);
        usernameEDRegister = (EditText) findViewById(R.id.username_edit_text_register);
        passwordEDRegister = (EditText) findViewById(R.id.password_edit_text_register);
        phoneNumberEDRegister = (EditText) findViewById(R.id.phone_number_edit_text_register);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id)
            {
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = usernameEDRegister.getText().toString().trim();
                password = passwordEDRegister.getText().toString().trim();
                phoneNumber = phoneNumberEDRegister.getText().toString().trim();
                //dialogBoxMessage = getResources().getString(R.string.registration_warning);

                builder.setMessage("Your..."+"\n"+"\n"+"User Name: "+username+"\n"+"Password: "+password)
                .setTitle(R.string.register_with_phone_number);

                final AlertDialog dialog = builder.create();

                TalkToDB.addUser(username, password, phoneNumber, RegisterActivity.this);

                dialog.show();
            }
        });
    }



}
