package com.example.jerma.lab1;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class EmptyFragmentActivity extends Activity {

    MessageFragment fragment = new MessageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_message_details);

       //Intent intent = getIntent();
       //Bundle bundle = intent.getExtras();
       //String value = bundle.getString("Key");


       fragment.setArguments(getIntent().getExtras());


        //LOAD THE FRAGMENT (THAT IS DISPLAYING EMPTYLAYOUT)INTO THE FRAMELAYOUT
       FragmentTransaction ft = getFragmentManager().beginTransaction();
       //messageFrame comes from messageDetails layout
       ft.add(R.id.messageFrame, fragment);
       ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("DESTROY", "THIS HAS BEEN DESTROYED");
    }
}
