package com.csm117.digitalbazaar;

import android.os.AsyncTask;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;

import java.util.Map;
import java.util.HashMap;

public class PaymentBackend {
    private class ChargeTask extends AsyncTask<String, Void, Boolean> {
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

                // Charge the Customer instead of the card:
                Map<String, Object> chargeParams = new HashMap<String, Object>();
                chargeParams.put("amount", 100);
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
                informFrontEnd("Payment success!");
            } else {
                informFrontEnd("Payment declined!");
            }
        }
    }

    private class ChargeCustomer extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... customerIds) {
            String customerId = customerIds[0];
            try {
                // Charge the Customer instead of the card:
                Map<String, Object> chargeParams = new HashMap<String, Object>();
                chargeParams.put("amount", 100);
                chargeParams.put("currency", "usd");
                chargeParams.put("customer", customerId);
                Charge charge = Charge.create(chargeParams);

            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                informFrontEnd("Payment success!");
            } else {
                informFrontEnd("Payment declined!");
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
            payment.child("payment").child(p.id).setValue(p);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                informFrontEnd("Save success!");
            } else {
                informFrontEnd("Save failed!");
            }
        }
    }

    PaymentActivity frontEnd;

    PaymentBackend(PaymentActivity fe) {
        frontEnd = fe;

        // Set your secret key: remember to change this to your live secret key in production
        // See your keys here: https://dashboard.stripe.com/account/apikeys
        Stripe.apiKey = "sk_test_6qfgvcOlBqCvFaakv1URDLgF";
    }

    private void informFrontEnd(CharSequence text) {
        frontEnd.showMessage(text);
    }

    public void newCustomer(String tokenId) {
        new NewCustomerTask().execute(tokenId);
    }

    public void charge(String tokenId) {
        new ChargeTask().execute(tokenId);
    }

}