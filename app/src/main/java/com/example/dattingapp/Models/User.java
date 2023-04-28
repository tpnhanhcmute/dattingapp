package com.example.dattingapp.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    public  String userID;
    public  String phoneNumber;
    public  String email;
    public  String fullName;
    public  String career;
    public String dateOfBirth;
    public  Integer age;
    public List<String> hobby;
    public  String gender;
    public  boolean isFirstLogin;
    public  String occupation;

    public User(){
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }
}
