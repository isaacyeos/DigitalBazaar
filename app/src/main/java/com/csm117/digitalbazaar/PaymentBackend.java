package com.csm117.digitalbazaar;

import android.os.AsyncTask;
import android.provider.Settings;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

import java.util.Map;
import java.util.HashMap;

public class PaymentBackend {
    private class ChargeParams {
        String tokenId;
        int amount;     // in cents
    }
    private class ChargeTask extends AsyncTask<ChargeParams, Void, Boolean> {
        protected Boolean doInBackground(ChargeParams... params) {
            String tok = params[0].tokenId;
            int amount = params[0].amount;

            // Create a Customer:
            Map<String, Object> customerParams = new HashMap<String, Object>();
            customerParams.put("email", "cxv@g.ucla.edu");
            customerParams.put("source", tok);
            String customerId;
            try {
                Customer customer = Customer.create(customerParams);
                customerId = customer.getId();

                // Charge the Customer instead of the card:
                Map<String, Object> chargeParams = new HashMap<String, Object>();
                chargeParams.put("amount", amount);
                chargeParams.put("currency", "usd");
                chargeParams.put("customer", customerId);
                Charge charge = Charge.create(chargeParams);
            } catch (Exception e) {
                return false;
            }

            // Also save him to database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference payment = database.getReference();
            PaymentInformation p = new PaymentInformation(customerId, "someone");
            payment.child("payment").child(p.id).setValue(p);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                notifyFrontEnd("Payment success!");
            } else {
                notifyFrontEnd("Payment declined!");
            }
        }
    }

    private class NewCustomerTask extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... tokenId) {
            String tok = tokenId[0];

            // Create a Customer:
            Map<String, Object> customerParams = new HashMap<String, Object>();
            customerParams.put("email", "cxv@g.ucla.edu");
            customerParams.put("source", tok);
            String customerId;
            try {
                Customer customer = Customer.create(customerParams);
                customerId = customer.getId();

            } catch (Exception e) {
                return false;
            }

            // Save him to database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference payment = database.getReference();
            PaymentInformation p = new PaymentInformation(customerId, tok);
            String user = Globals.getInstance().userId;
            payment.child("accounts").child(user).child("payments").setValue(p);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                notifyFrontEnd("Save success!");
            } else {
                notifyFrontEnd("Save failed!");
            }
        }
    }

    PaymentFrontEnd frontEnd;

    PaymentBackend(PaymentFrontEnd fe) {
        frontEnd = fe;

        // Set your secret key: remember to change this to your live secret key in production
        // See your keys here: https://dashboard.stripe.com/account/apikeys
        Stripe.apiKey = "sk_test_6qfgvcOlBqCvFaakv1URDLgF";
    }

    private void notifyFrontEnd(CharSequence text) {
        frontEnd.notify(text);
    }

    public void newCustomer(String tokenId) {
        new NewCustomerTask().execute(tokenId);
    }

    public void charge(int amount, String tokenId) {
        // Check params
        if (amount<100) {
            notifyFrontEnd("Transaction amount too small, minimum 100 cents");
            return;
        }

        ChargeParams params = new ChargeParams();
        params.amount = amount;
        params.tokenId = tokenId;
        new ChargeTask().execute(params);
    }

}
