package com.csm117.digitalbazaar;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.graphics.Bitmap;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



import java.util.ArrayList;

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
//        ArrayList<String> Users = getIntent().getExtras().getStringArrayList("userIDs");
//        intent.putStringArrayListExtra("userIDs", Users);
        String curUser = getIntent().getStringExtra("userIDs");
        intent.putExtra("userID", curUser);
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
                ImageView image = (ImageView) v.findViewById(R.id.postImageText);
                TextView Destrc = (TextView) v.findViewById(R.id.postDescription);
                String imageText = model.image;
                title.setText(model.title);
                price.setText(model.price);
                Destrc.setText(model.description);
               byte[] imageBytes = Base64.decode(imageText,Base64.DEFAULT);
                Bitmap DecodeImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
                image.setImageBitmap(DecodeImage);
                //  image.setImage("IMAGE HERE");
            }
        };

        listOfPosts.setAdapter(adapter);
    }




}
