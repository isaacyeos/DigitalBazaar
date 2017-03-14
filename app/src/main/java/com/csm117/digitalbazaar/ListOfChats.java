package com.csm117.digitalbazaar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;



public class ListOfChats extends AppCompatActivity {

    private String currentUserId;
    private String chatPath;

    private FirebaseListAdapter<ChatBubble> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_chats);
        currentUserId = getIntent().getExtras().getString("userID");
        chatPath = "accounts/" + currentUserId + "/conversations";
        displayAllChats();
    }

    public void clickButtonGoToChat(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ChatMainActivity.class);
        String curUser = getIntent().getExtras().getString("userID");
        intent.putExtra("userID", curUser);
//        Button temp = (Button) findViewById(R.id.chat);
        TextView temp = (TextView) findViewById(R.id.chat);
        String otherUser = temp.getText().toString();
        intent.putExtra("otheruserID", otherUser);
        startActivity(intent);
    }

    private void displayAllChats() {
        ListView listOfChats = (ListView)findViewById(R.id.list_of_chats);
        adapter = new FirebaseListAdapter<ChatBubble>(this, ChatBubble.class,
                R.layout.chat, FirebaseDatabase.getInstance().getReference(chatPath)) {
            @Override
            protected void populateView(View v, ChatBubble model, int position) {
                // Get references to the views of message.xml
//                Button temp = (Button)v.findViewById(R.id.chat);
                TextView temp = (TextView)v.findViewById(R.id.chat);
                temp.setText(model.getUserID());
            }
        };

        listOfChats.setAdapter(adapter);
    }
}
