package com.example.jerma.lab1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    MenuItem item1;
    EditText text;
    String defaultText;
    LayoutInflater inflater;
    //View dialogView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        defaultText = "You clicked item 1";
        text = (EditText)findViewById(R.id.menuText);
       // text.setText("" + defaultText);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hello There", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu (Menu m){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.action_one:
                //Toast toast1 = Toast.makeText(this, "Action One ", Toast.LENGTH_LONG);
                //toast1.show();
               // Snackbar.make(this.findViewById(R.id.myToolbar), text.getText().toString(), Snackbar.LENGTH_LONG) .setAction("Action", null).show();
                Snackbar.make(this.findViewById(R.id.myToolbar), defaultText, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                break;
            case R.id.action_two:
               // Toast toast2 = Toast.makeText(this, "Action Two ", Toast.LENGTH_LONG);
                //toast2.show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("You clicked item 2");
                // Add the buttons
                builder.setPositiveButton("Do you want to go back?", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog1 = builder.create();
                dialog1.show();

                break;
            case R.id.action_three:
               // Toast toast3 = Toast.makeText(this, "Action Three ", Toast.LENGTH_LONG);
                //toast3.show();

                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                // Get the layout inflater
               LayoutInflater inflater = this.getLayoutInflater();
              View dialogView = inflater.inflate(R.layout.menu_item_3, null);
                text = dialogView.findViewById(R.id.menuText);

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder3.setView(dialogView);
                        // Add action buttons
                        builder3.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if(text == null){
                                    defaultText = "";
                                    Log.i("message", "WARNING: 'text' is null.");
                                } else {
                                    defaultText = text.getText().toString();
                                    String myString = text.getText().toString();
                                    Log.i("EditText", "VALUE " + text.getText().toString());
                                    Log.i("myString", "VALUE " + myString);
                                    Log.i("defaultText", "VALUE " + defaultText);
                                }
                             Log.i("message", "VALUE " + defaultText);
                               // text.setText(string);

                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog3 = builder3.create();
                dialog3.show();

                break;
            case R.id.about:
                break;
        }
        return true;
    }

}
