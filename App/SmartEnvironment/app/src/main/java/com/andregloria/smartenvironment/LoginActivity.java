package com.andregloria.smartenvironment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.andregloria.smartenvironment.utils.CheckConnection;
import com.andregloria.smartenvironment.utils.TestLogin;

public class LoginActivity extends Activity {

    Button buttonLogin, guestButton;
    TextView registerScreen, recoveryScreen;
    ImageButton aboutButton;
    EditText pass;
    AutoCompleteTextView email=null;
    private Toast toast;
    CheckConnection connection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.activity_login);
        registerScreen = (TextView) findViewById(R.id.signUp);
        email = (AutoCompleteTextView) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        recoveryScreen = (TextView) findViewById(R.id.passres);
        buttonLogin = (Button) findViewById(R.id.login);
        SharedPreferences sharedPrefs = getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPrefs.contains("username") && sharedPrefs.contains("password")){
            //new TestLogin(LoginActivity.this, sharedPrefs.getString("username",""), sharedPrefs.getString("password","")).execute();
            email.setText(sharedPrefs.getString("username",""));
            pass.setText(sharedPrefs.getString("password",""));
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check Connection
                connection = new CheckConnection(getApplicationContext());
                if(connection.isConnected()) {
                    String userText = email.getText().toString();
                    String passText = pass.getText().toString();

                    if(userText.equals("") || passText.equals("")){
                        if (toast == null || toast.getView().getWindowVisibility() != View.VISIBLE) {
                            toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_insert_all_fields), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }else {
                        new TestLogin(LoginActivity.this, userText, passText).execute();
                    }
                }else{

                    if (toast == null || toast.getView().getWindowVisibility() != View.VISIBLE) {
                        toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.noInternetConnection), Toast.LENGTH_LONG);
                        toast.show();
                    }

                }
            }
        });

        /*
        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);

            }
        });

        recoveryScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), ForgotActivity.class);
                startActivity(i);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(i);
            }
        }); */
    }
}

