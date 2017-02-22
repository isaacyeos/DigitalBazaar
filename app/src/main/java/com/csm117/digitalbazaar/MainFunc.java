package com.csm117.digitalbazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
}
