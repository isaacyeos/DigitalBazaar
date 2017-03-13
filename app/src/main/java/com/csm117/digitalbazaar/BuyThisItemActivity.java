package com.csm117.digitalbazaar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BuyThisItemActivity extends AppCompatActivity implements PaymentFrontEnd {
    DatabaseReference paymentsReference;
    BuyThisItemActivity me;
    PaymentBackend paymentBackend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_this_item);
        me = this;
        paymentBackend = new PaymentBackend(this);

        // Find out if this user already have payment info
        final String userId = getIntent().getExtras().getString("userID");
        String path = "payments/";
        paymentsReference = FirebaseDatabase.getInstance().getReference(path);

        // Get price info
        String amountString = getIntent().getExtras().getString("amount");
        int amount = Integer.parseInt(amountString);

        paymentsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren() && dataSnapshot.hasChild(userId)) {
                    // Has payment info, buy
                    finish();
                    return;
                } else {
                    // No payment info, redirect to register payment info
                    Intent intent = new Intent(me, RegisterPaymentInfo.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void notify(CharSequence text, boolean success) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        if (success) {
            finish();
            return;
        }
    }

    public void buy(View view) {

    }
}
