package com.csm117.digitalbazaar;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by kirkzhang on 3/1/17.
 */
@IgnoreExtraProperties
public class PostInfo {
    public String token;
    public String owner;

    public PostInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(PaymentInformation.class)
    }

    public PostInfo(String token, String owner) {
        this.token = token;
        this.owner = owner;
    }
}
