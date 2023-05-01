package com.example.dattingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dattingapp.Models.Filter;
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
    public  static  final String LISTIMAGE="ListImage";

    public  static  final String FILTER ="filter";

    private  static Context ctx;
    private  static  SharedPreference _instance;

    public static SharedPreference getInstance(Context context){
        ctx =context;
        if(_instance == null) _instance = new SharedPreference();
        return _instance;
    }
    public  void SetListImage(List<String> listUrl){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        String listImageString = new Gson().toJson(listUrl);
        editor.putString(LISTIMAGE, listImageString);
        editor.apply();
    }
    public  List<String> GetImageList(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String listUrlString =  sharedPreferences.getString(LISTIMAGE,"");
        if(listUrlString=="") return null;
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> listUrl = new Gson().fromJson(listUrlString, type);
        return listUrl;
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

    public  void SetFilter(Filter filter){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String filterJson = new Gson().toJson(filter);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString(FILTER, filterJson);
        editor.apply();
    }

    public Filter GetFilter(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String filterJson = sharedPreferences.getString(FILTER,"");
        Filter defaultFilter = new Filter();
        defaultFilter.maxAge = 150;
        defaultFilter.minAge =0;
        defaultFilter.gender = "Both";
        defaultFilter.distance = Double.MAX_VALUE;
        if(filterJson == ""){
            return defaultFilter;
        }
        Type type = new TypeToken<Filter>(){}.getType();
        Filter filter = new Gson().fromJson(filterJson,type);
        if(filter.distance!= 0) defaultFilter.distance = filter.distance;
        if(filter.gender != "") defaultFilter.gender = filter.gender;
        if(filter.minAge >0) defaultFilter.minAge = filter.minAge;
        if(filter.maxAge <150) defaultFilter.maxAge =filter.maxAge;

        return defaultFilter;
    }



}
