package com.example.jerma.lab1;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.example.jerma.lab1.ChatDatabaseHelper.KEY_ID;
import static com.example.jerma.lab1.ChatDatabaseHelper.KEY_MESSAGE;
import static com.example.jerma.lab1.ChatDatabaseHelper.TABLE_NAME;

public class ChatWindow extends Activity {

    EditText chatRoom;
    ListView lv;
    Button send;
    ArrayList<String> list;
    ChatDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor curse;

    ChatAdapter messageAdapter;

    TextView message;
    Boolean isTablet;
    MessageFragment mf;
    Bundle info;
    View view;
    ChatAdapter ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);





        chatRoom = (EditText)findViewById(R.id.chatRoom);
        lv = (ListView)findViewById(R.id.lv);
        send = (Button)findViewById(R.id.send);
        list = new ArrayList<>();

        //equivalent of df
        mf = new MessageFragment();

        isTablet = findViewById(R.id.frame) != null;
        info = new Bundle();


        dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase(); //opens database
        curse = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_MESSAGE}, null, null, null, null, null);
        while(curse.moveToNext()){
            String s = curse.getString(curse.getColumnIndexOrThrow(KEY_MESSAGE));
            Log.i("message", "which string" + s);
            list.add(s);
        }

        for(int i = 0; i< list.size(); i++){
            Log.i("message", "list " + i + ": " +  list.get(i) + ".");
        }


       // final ChatAdapter messageAdapter = new ChatAdapter(this, 0);
        messageAdapter = new ChatAdapter(this, 0);
        lv.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();

        //list.add(curse.getString(curse.getColumnCount()));

        //int test = curse.getColumnIndex(KEY_MESSAGE);
        //Log.i("message", "which string" + curse.getString(test));




        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(chatRoom.getText().toString());
                Log.i("ISTHEREASTRING", "STRING " + chatRoom.getText().toString());
                //log.i("ISTHEREASTRING", "STRING " + list.get().toString());


                chatRoom.setText("");

                ContentValues values = new ContentValues();
                //values.put(KEY_MESSAGE, chatRoom.getText().toString());
                //values.put(KEY_MESSAGE, chatRoom.getText().toString());
                //db.insert(TABLE_NAME, KEY_MESSAGE, values);

                values.put(KEY_MESSAGE, list.get(list.size()-1));
                db.insert(TABLE_NAME, KEY_MESSAGE, values);

               //updates the listview
                messageAdapter.notifyDataSetChanged();



            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //you need to somehow get the long id variable which is the Database id of the message that you click on.
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isTablet){
                    info.putString("Key", lv.getItemAtPosition(position).toString());
                    info.putLong("Key2", id);
                    info.putBoolean("isTablet", true);
                    mf.setArguments(info);
                    //this is where you start the transaction
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, mf);
                    ft.addToBackStack("A string name");
                    getItemID(position);
                    ft.commit();
                }else{
                    info.putString("Key", lv.getItemAtPosition(position).toString());
                    info.putLong("Key2", id);

                    //start new activity for the phone
                    //opens EmptyFragment which opens MessageFragment which loads the layout it wants
                    Intent phoneIntent = new Intent(ChatWindow.this, EmptyFragmentActivity.class);
                    //stores the bundle object
                    phoneIntent.putExtras(info);
                    startActivityForResult(phoneIntent, 0);
                    //startActivityForResult(phoneIntent, 1);
                }

            }
        });

        //list.set(curse.getColumnIndex("message"), lv.getItemAtPosition(curse.getColumnIndex("message")).toString());
       // list.set(curse.getColumnCount(), lv.getItemAtPosition(curse.getColumnIndex("message")).toString());
       // messageAdapter.getView(curse.getColumnCount(), null, null);

                Log.i("COLUMNS", "CURSOR COLUMNS = " + curse.getColumnCount());
        Log.i("ROWS", "CURSOR ROWS = " + curse.getCount());
        Log.i("LIST", "LISTSIZE = " + list.size());

       for(int i = 0; i < curse.getColumnCount(); i++) {
           Log.i("ChatWindow", curse.getColumnName(i));
       }
           //???
           //while(!curse.isAfterLast() ){
           // Log.i("ChatWindow", "SQL MESSAGE:" + curse.getString(curse.getColumnIndex("message")));

       }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Long deleteThis;
        int deleteMe;
        String deleteString;
        Bundle extras = data.getExtras();
        //MessageFragment frag = new MessageFragment();
        //frag.getChildFragmentManager().findFragmentById(R.id.frame);
       // Log.i("WORKING?", "FRAGMENT HERE? " + frag);

       /* if (frag != null) {
            deleteMe = extras.getInt("deleteMe");
            list.remove(deleteMe);
        } else {*/


            Log.i("allvalues", "THE VALUES ARE " + requestCode + ", " + resultCode + ", " + data);
            Log.i("LISTSIZE", "THE SIZE OF THE LIST IS: " + list.size());
            Log.i("Closer", "THIS HAS BEEN CALLED");


            if (extras == null) {
                deleteThis = null;
                Log.i("null", "DELETETHISISNULLIGUESS");
            } else {
                //deleteThis = extras.getLong("deleteMe");
                deleteMe = extras.getInt("deleteMe");
                deleteString = extras.getString("deleteString");
                // Log.i("value", "THE VALUE OF THE ID THAT YOU WANT TO DELETE IS " + deleteThis);
                Log.i("value", "THE VALUE OF THE ID THAT YOU WANT TO DELETE IS " + deleteMe);
                Log.i("string", "THE STRING OF THE ID THAT YOU WANT TO DELETE IS " + deleteString);

                list.remove(deleteMe);
                //db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + (deleteMe + 1));
                db.delete(TABLE_NAME, KEY_MESSAGE + " = ?", new String[]{deleteString});
                messageAdapter.notifyDataSetChanged();
                //lv.updateViewLayout(View.inflate(this,  0, null), null);
                //lv.removeViewAt(0);
                Log.i("LISTSIZE", "THE SIZE OF THE LIST IS: " + list.size());
                // lv.updateViewLayout();
            }
        }
    //}




    protected void onResume(){
        super.onResume();
        Log.i("RESUME", "RESUME IS CALLED");
        Log.i("LISTSIZE", "THE SIZE OF THE LIST IS: " + list.size());
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        int j;
        String string;
        if (bundle == null){
            j = 0;
        }else {

            j = bundle.getInt("deleteMe");
            string = bundle.getString("deleteString");
            Log.i("string", "THE STRING OF THE ID THAT YOU WANT TO DELETE IS " + string + " ID is " + j);
            Log.i("value", "THE Value OF THE ID THAT YOU WANT TO DELETE IS " + j);
            //list.add(curse.getString(curse.getColumnCount()));
           // list.add(
            //Log.i("LISTSIZE", "THE SIZE OF THE LIST IS: " + list.size());
            //Log.i("LISTSIZE", "THE SIZE OF THE LIST IS: " + list.get(curse.getColumnCount()).toString());

            //Log.i("LISTSIZE", curse.getString(j));
           // db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + (j + 1));
            list.remove(j);
            db.delete(TABLE_NAME, KEY_MESSAGE + " = ?", new String[]{string});
            messageAdapter.notifyDataSetChanged();
            //Log.i("LISTSIZE", "THE SIZE OF THE LIST IS: " + list.size());
        }


    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx,int resource){
            super(ctx, resource);
        }

        public int getCount(){
            return list.size();
    }

        public String getItem(int position){
            return list.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;

            if(position%2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            }else{
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
             message = (TextView)result.findViewById(R.id.messageText);
            message.setText(getItem(position));//THIS IS BREAKING IT
            return result;
        }

    }

    protected void onDestroy(){
        super.onDestroy();
        dbHelper.close();

        Log.i("ChatWindow", "In onDestroy()");
    }

    //DOUBLE CHECK
    public long getItemID(int position){
        curse.moveToPosition(position);
        return curse.getPosition();
    }



}


