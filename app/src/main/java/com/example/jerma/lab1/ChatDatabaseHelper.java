package com.example.jerma.lab1;

import android.app.ActionBar;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jerma on 2017-10-15.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "Messages.db";
    public static final String TABLE_NAME = "TableName";
    public static final String KEY_ID = "id"; //column name
    public static final String KEY_MESSAGE = "message"; //column name
    private static final int VERSION_NUM = 8;
    SQLiteDatabase db;

    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
        db = this.getWritableDatabase();
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " TEXT)");

        Log.i("ChatDatabaseHelper" , "Calling onCreate");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);


    }



}
