package com.csm117.digitalbazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainFunc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_func);
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
}
