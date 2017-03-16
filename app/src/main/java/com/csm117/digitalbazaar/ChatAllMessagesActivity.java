package com.csm117.digitalbazaar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.util.Log;
import android.support.design.widget.FloatingActionButton;
import android.view.*;
import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ValueEventListener;

import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;


public class ChatAllMessagesActivity extends AppCompatActivity {
    private FirebaseListAdapter<ChatMessage> adapter;
    private String currentUserId;
    private String chatPath;
    private static String curUser;
//    private String[] conversationIds = new String[100];
    private ArrayList<String> conversationIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_all_messages);
        Log.d("tag", "Inside on-create for ChatMainActivity.");

        Log.d("yolo", "helloooo");

        //get currentUserId and otherUserid
        curUser = getIntent().getExtras().getString("userID");

        currentUserId = curUser;
        char[] chars = currentUserId.toCharArray();
        Arrays.sort(chars);

        // Will be used to find relative conversations
        String sortedUserId = new String(chars);


        // Create new chat thread for the two users. Store thread id in each user's account info
//        String currentUserPath = "accounts/" + currentUserId + "/conversations/" + conversationId;
//        FirebaseDatabase.getInstance()
//                .getReference(currentUserPath)
//                .setValue(new Date().getTime());

        //chatPath = "messages/" + conversationId;

//        FloatingActionButton fab =
//                (FloatingActionButton)findViewById(R.id.fab);

        Log.d("yolo", currentUserId);

        getAllConversations();
//        displayAllMessages();


//        }

    }

    private void getAllConversations() {
        String conversationsPath = "accounts/" + currentUserId + "/conversations/";
        FirebaseDatabase.getInstance().getReference(conversationsPath).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("yolo", "lol4");
                Map<String, String> value = (Map<String, String>) dataSnapshot.getValue();

                Log.d("yolo", "lol5");
                Log.d("yolo", dataSnapshot.toString());



                for (Map.Entry<String, String> entry: value.entrySet()) {
                    conversationIds.add(entry.getKey());
                    Log.d("yolo", entry.getKey());
                }

                Log.d("yolo", conversationIds.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("yolo", "lol6");
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }

    private void displayAllMessages() {
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);


        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference(chatPath)) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }


}
