package com.csm117.digitalbazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the GoToPayment button */
    public void clickButtonGoToPayment(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
    }
}
