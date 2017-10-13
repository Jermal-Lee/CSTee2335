package com.example.jerma.lab1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    EditText chatRoom;
    ListView lv;
    Button send;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        chatRoom = (EditText)findViewById(R.id.chatRoom);
        lv = (ListView)findViewById(R.id.lv);
        send = (Button)findViewById(R.id.send);
        list = new ArrayList<>();

         final ChatAdapter messageAdapter = new ChatAdapter(this, 0);
        lv.setAdapter(messageAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(chatRoom.getText().toString());
                messageAdapter.notifyDataSetChanged();
                chatRoom.setText("");
            }
        });
    }

    protected void onResume(){
        super.onResume();
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
            TextView message = (TextView)result.findViewById(R.id.messageText);
            message.setText(getItem(position));//THIS IS BREAKING IT
            return result;
        }

    }




}
