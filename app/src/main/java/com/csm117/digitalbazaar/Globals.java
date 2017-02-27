package com.csm117.digitalbazaar;

public class Globals {
    private static Globals instance;

    // Global variable
    public int data;

    // Restrict the constructor from being instantiated
    private Globals(){}

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }
}
