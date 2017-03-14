package com.csm117.digitalbazaar;

import java.util.Date;

/**
 * Created by yeo on 3/14/17.
 */

public class ChatBubble {


    private String userID;
    private String userName;

    public ChatBubble(String userID) {
        this.userID = userID;
//        this.userName = userName;

    }

    public ChatBubble(){

    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}

