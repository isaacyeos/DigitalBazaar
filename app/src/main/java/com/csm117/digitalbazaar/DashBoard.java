package com.csm117.digitalbazaar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        findViewById(R.id.Logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent();
                setResult(RESULT_OK, logout);
                finish();
            }
        });
    }
    /** Called when the user clicks the GoToPayment button */
    public void clickButtonGoToMain(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MainFunc.class);
        startActivity(intent);
    }
    /** Called when the user clicks the GoToPayment button */
    public void clickButtonGoToLogin(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    /** Called when the user clicks the GoToPayment button */
    public void clickButtonGoToPayment(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
    }

}
