package com.csm117.digitalbazaar;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.stripe.android.*;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import com.google.firebase.database.*;


public class PaymentActivity extends AppCompatActivity {
    PaymentBackend paymentBackend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentBackend = new PaymentBackend(this);
    }

    public void showMessage(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void testProcess(View view) {
        // Get form info
        EditText et = (EditText) findViewById(R.id.creditcardNumberEdit);
        String cardNumber = et.getText().toString();
        et = (EditText) findViewById(R.id.expirationMonthEdit);
        int cardExpMonth = Integer.parseInt(et.getText().toString());
        et = (EditText) findViewById(R.id.expirationYearEdit);
        int cardExpYear = Integer.parseInt(et.getText().toString());
        et = (EditText) findViewById(R.id.ccvEdit);
        String cardCCV = et.getText().toString();

        Card card = new Card(cardNumber, cardExpMonth, cardExpYear, cardCCV);
        if (!card.validateCard()) {
            // Show errors
            showMessage("Invalid Card!");
        }

        Stripe stripe = new Stripe();
        try {
            stripe.setDefaultPublishableKey("pk_test_PRUasoC2c2VrLqBR4WV1tFwS");
        } catch (Exception e) {
            showMessage("Error connecting to network!");
        }
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        // Send token to your server
                        paymentBackend.charge(token.getId());
                    }

                    public void onError(Exception error) {
                        // Show localized error message
                        showMessage("Error processing credit card!");
                    }
                }
        );
    }

    public void testSave(View view) {
        // Get form info
        EditText et = (EditText) findViewById(R.id.creditcardNumberEdit);
        String cardNumber = et.getText().toString();
        et = (EditText) findViewById(R.id.expirationMonthEdit);
        int cardExpMonth = Integer.parseInt(et.getText().toString());
        et = (EditText) findViewById(R.id.expirationYearEdit);
        int cardExpYear = Integer.parseInt(et.getText().toString());
        et = (EditText) findViewById(R.id.ccvEdit);
        String cardCCV = et.getText().toString();

        Card card = new Card(cardNumber, cardExpMonth, cardExpYear, cardCCV);
        if (!card.validateCard()) {
            // Show errors
            showMessage("Invalid Card!");
        }

        Stripe stripe = new Stripe();
        try {
            stripe.setDefaultPublishableKey("pk_test_PRUasoC2c2VrLqBR4WV1tFwS");
        } catch (Exception e) {
            showMessage("Error connecting to network!");
        }
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        // Send token to your server
                        paymentBackend.newCustomer(token.getId());
                    }

                    public void onError(Exception error) {
                        // Show localized error message
                        showMessage("Error processing credit card!");
                    }
                }
        );
    }
}
