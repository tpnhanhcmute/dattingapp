package com.example.dattingapp.DTO;

import java.util.List;

public class DiscoverModel {
    public String getUserID() {
        return userID;
    }

    public  String userID;
    public  String fullName;
    public Integer age;
    public List<String> hobby;
    public Double distance;
    public String locationName;
    public List<String> imageUrl;
    public  String getImage(){
        return imageUrl == null ?"":imageUrl.size()>0?imageUrl.get(0):"";
    }

}
