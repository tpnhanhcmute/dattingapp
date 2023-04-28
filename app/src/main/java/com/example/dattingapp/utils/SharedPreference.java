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
    public  static  final String AVATARURL="AvatarUrl";
    private  static Context ctx;
    private  static  SharedPreference _instance;

    public static SharedPreference getInstance(Context context){
        ctx =context;
        if(_instance == null) _instance = new SharedPreference();
        return _instance;
    }
    public  void SetAvatarUrl(String url){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString(AVATARURL, url);
        editor.apply();
    }
    public  String GetAvatarUrl(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String url =  sharedPreferences.getString(AVATARURL,"");
        if(url=="") return null;
        return url;
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

    public  void SetUserS(String x){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString(USERKEY, x);
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

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USERKEY, null) != null;
    }



}
