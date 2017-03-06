package com.csm117.digitalbazaar;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.csm117.digitalbazaar.R.id.post;


public class MainFunc extends AppCompatActivity {
    //final FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference ref = database.getReference("https://digitalbazaar-f496d.firebaseio.com/");
    ArrayList<String> PostArray=new ArrayList<>();
    // ListView lv;
    ArrayList<PostInfo> allPosts;

    private FirebaseListAdapter<PostInfo> adapter;

    //ArrayAdapter<String> adapter;
    //Firebase fire;
    //final static String URL = "https://digitalbazaar-f496d.firebaseio.com/posts";
    private TextView testTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_func);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        testTextView = (TextView) findViewById(R.id.textDisplay);
//
//        testTextView.setText("Inside here");


        displayAllPosts();

        System.out.println("IN MAINFUNC");
        // Firebase.setAndroidContext(this);
        //fire = new Firebase(URL);
        //lv = (ListView) findViewById(R.id.list);
       /* FirebaseDatabase.getInstance()
                        .getReference()
                        .child("posts")
                        .child("Book")*/
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("posts");

        System.out.println("AFTER FIREBASE REF INST");
// Attach a listener to read the data at our posts reference
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//                testTextView.setText("ERRORRRRR");
//
//            }
//        });

    }

    /** Called when the user clicks the GoToPosintg button */
    public void clickButtonGoToPosting(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Posting.class);
        startActivity(intent);
    }

    public void clickButtonGoToSetting(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, setting.class);
        startActivity(intent);
    }

    public void clickButtonGoToMessages(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ChatMainActivity.class);
        startActivity(intent);
    }

    private void displayAllPosts() {
        ListView listOfPosts = (ListView)findViewById(R.id.list_of_posts);

        adapter = new FirebaseListAdapter<PostInfo>(this, PostInfo.class,
                R.layout.post, FirebaseDatabase.getInstance().getReference("posts")) {
            @Override
            protected void populateView(View v, PostInfo model, int position) {
                // Get references to the views of message.xml
                TextView title = (TextView)v.findViewById(R.id.postTitle);
                TextView price = (TextView)v.findViewById(R.id.postPrice);
                TextView image = (TextView)v.findViewById(R.id.postImageText);

                title.setText(model.title);
                price.setText(model.price);
                image.setText("IMAGE HERE");
            }
        };

        listOfPosts.setAdapter(adapter);
    }




}
