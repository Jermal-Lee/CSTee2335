package com.example.jerma.lab1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {

    CheckBox checkBox;
    ImageButton cam;
    Switch nin;
    protected static final String ACTIVITY_NAME = "ListItems Activity";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        nin = (Switch) findViewById(R.id.nin);
        nin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), R.string.switchToastOn, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.switchToastOff, Toast.LENGTH_LONG).show();
                }
            }
        });


        cam = (ImageButton) findViewById(R.id.cam);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });

            checkBoxListener();
        }



    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cam.setImageBitmap(imageBitmap);
        }
    }

    /*public void checkBoxListener(){
        checkBox = (CheckBox)findViewById(R.id.checkBox);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
// 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("do you want to close this app?") //Add a dialog message to strings.xml


                        .setTitle("hi")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent resultIntent = new Intent(ListItemsActivity.this, StartActivity.class  );
                                resultIntent.putExtra("Response", "Here is my response");
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

    });





}*/

    public void checkBoxListener(){
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
               builder.setTitle(R.string.dialog_title);
                builder.setMessage(R.string.dialog_message);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent resultIntent = new Intent(  );
                        resultIntent.putExtra("Response", "Here is my response");
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();

                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                AlertDialog alert = builder.create();
                alert.show();

    }
});

}}