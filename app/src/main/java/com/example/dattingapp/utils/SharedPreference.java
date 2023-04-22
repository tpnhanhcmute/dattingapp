package com.example.dattingapp.utils;

public class SharedPreference {
    private  static  SharedPreference _instance;



    public SharedPreference getInstance(){
        if(_instance == null) _instance = new SharedPreference();
        return _instance;
    }
}
