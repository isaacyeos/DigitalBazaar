package com.csm117.digitalbazaar;

import android.os.AsyncTask;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;

import java.util.Map;
import java.util.HashMap;

public class StripeBackend {
    private class ChargeTask extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... tokenId) {
            String tok = tokenId[0];

            // Set your secret key: remember to change this to your live secret key in production
            // See your keys here: https://dashboard.stripe.com/account/apikeys
            Stripe.apiKey = "sk_test_6qfgvcOlBqCvFaakv1URDLgF";

            // Create a Customer:
            Map<String, Object> customerParams = new HashMap<String, Object>();
            customerParams.put("email", "cxv@g.ucla.edu");
            customerParams.put("source", tok);
            try {
                System.out.println("Token id is: "+tok);
                Customer customer = Customer.create(customerParams);
                System.out.println("Customer id is: "+customer.getId());

                // Charge the Customer instead of the card:
                Map<String, Object> chargeParams = new HashMap<String, Object>();
                chargeParams.put("amount", 100);
                chargeParams.put("currency", "usd");
                chargeParams.put("customer", customer.getId());
                Charge charge = Charge.create(chargeParams);
            } catch (Exception e) {
                System.out.println(e);
            }

            // Success
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            informFrontEnd("Payment Success!");
        }
    }

    PaymentActivity frontEnd;
    StripeBackend(PaymentActivity fe) {
        frontEnd = fe;
    }

    private void informFrontEnd(CharSequence text) {
        frontEnd.showMessage(text);
    }

    public void charge(String tokenId) {
        new ChargeTask().execute(tokenId);
    }

}
