package com.example.jerma.lab1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by jerma on 2017-12-01.
 */

public class MessageFragment extends Fragment {

TextView message;
TextView ID;
Button delete;
String messageBundle;
Long id;
Activity parent;
Boolean isTablet;
int convert;
ChatWindow cw;

    public MessageFragment(){

}


    //public MessageFragment(ChatWindow cw){

   // }

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        Log.i("ONCREATE?", "FRAGMENT ON CREATE CALLED");


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        parent = activity;

        Log.i("WHICH ACTIVITY?", "THE ACTIVITY IS: " + parent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       //opens up the fragment layout and attatch reference to layout you want to load with this fragment
        //this currently inflates the layout with the 2 text views and the button
        View view = inflater.inflate(R.layout.activity_empty_fragment, container, false);

        if(getArguments() != null){
             messageBundle = getArguments().getString("Key");
             id = getArguments().getLong("Key2");
             convert = id.intValue();
             isTablet = getArguments().getBoolean("isTablet");

        }

        message = view.findViewById(R.id.emptyMessage);
        message.setText("The message you clicked is: " + messageBundle);

        ID = view.findViewById(R.id.emptyID);
        ID.setText("The ID of the message you clicked is: " + id);






        //also include buttonOnClickEvent
        delete = view.findViewById(R.id.emptyButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTablet){
                    Intent intent = new Intent(parent, ChatWindow.class);
                    intent.putExtra("deleteMe", convert);
                    intent.putExtra("deleteString", messageBundle);
                    getActivity().startActivity(intent);
                    //parent.setResult(0, intent);
                    //getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
                    //getActivity().startActivity(intent);
                    //cw.getIntent(intent);

                }else {
                    Intent intent = new Intent(parent, ChatWindow.class);
                    // intent.putExtra("deleteMe", id);
                    intent.putExtra("deleteMe", convert);
                    intent.putExtra("deleteString", messageBundle);
                    //Bundle bundle = new Bundle();
                    //bundle.putLong("deleteMe", id);

                    parent.setResult(0, intent);
                    parent.finishActivity(0);
                    parent.finish();
                    parent.getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
                }



            }
        });


        return view;
    }


}

