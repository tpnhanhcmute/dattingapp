package com.example.dattingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dattingapp.Models.Location;
import com.example.dattingapp.Models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SharedPreference {
    public  static  final  String SHARED_PREF_NAME = "DattingApp";
    public static final   String USERKEY = "User";
    public static final String LOCATIONKEY="Location";
    private  static Context ctx;
    private  static  SharedPreference _instance;

    public SharedPreference getInstance(){
        if(_instance == null) _instance = new SharedPreference();
        return _instance;
    }

    public  User GetUser(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String user =  sharedPreferences.getString(USERKEY,"");
        if(user=="") return null;
        Type type = new TypeToken<User>(){}.getType(); //
        return new Gson().fromJson(user, type);
    }
    public  void SetUser(User user){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String userString = new Gson().toJson(user);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString(USERKEY, userString);
        editor.apply();
    }
    public Location GetLocation(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String location =  sharedPreferences.getString(LOCATIONKEY,"");
        if(location=="") return null;
        Type type = new TypeToken<Location>(){}.getType();
        return new Gson().fromJson(location, type);
    }
    public  void SetLocation(Location location){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String locationString = new Gson().toJson(location);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString(LOCATIONKEY, locationString);
        editor.apply();
    }
}
