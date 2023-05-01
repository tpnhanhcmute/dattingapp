package com.example.dattingapp.DTO;

import java.io.Serializable;
import java.util.List;

public class GetmatcModel implements Serializable {
    public  String userID;
    public  String fullName;
    public int age;
    public List<String> hobby;
    public Integer distance;
    public String locationName;
    public String imageUrl;
}
