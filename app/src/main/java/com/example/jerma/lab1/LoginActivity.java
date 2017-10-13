package com.example.jerma.lab1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.TextView;


public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME = "Login Activity";
    EditText userLogin;
    //TextView textView;
    Button loginButton;
    //Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.i(ACTIVITY_NAME, "In onCreate()");

        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("default", Context.MODE_PRIVATE);
                SharedPreferences.Editor editR = sharedPref.edit();
                editR.putString("username", userLogin.getText().toString());
                String name = sharedPref.getString("default", "defaultEmail@default.com");
                editR.commit();

                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
        //for debugging
       /*button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("default", Context.MODE_PRIVATE);
                String name = sharedPref.getString("username", "defaultEmail@default.com");
                userLogin.setText(name + " GJ");
            }
        });*/

        userLogin = (EditText)findViewById(R.id.userLogin);
        SharedPreferences sharedPref = getSharedPreferences("default", Context.MODE_PRIVATE);

        // SharedPreferences.Editor editR = sharedPref.edit();
        //editR.putString("username", userLogin.getText().toString());

        String name = sharedPref.getString("default", "defaultEmail@default.com");
        userLogin.setText(name);


    }

    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }


    /*public void saveInfo(View view){
            SharedPreferences sharedPref = getSharedPreferences("default", Context.MODE_PRIVATE);
            SharedPreferences.Editor editR = sharedPref.edit();
            editR.putString("username", userLogin.getText().toString());
            //String name = sharedPref.getString("default", "defaultEmail@default.com");
            editR.apply();
        }


    public void displayInfo(View view){
        SharedPreferences sharedPref = getSharedPreferences("default", Context.MODE_PRIVATE);
        String name = sharedPref.getString("username", "defaultEmail@default.com");
        userLogin.setText(name + "GJ");
    }*/
}


