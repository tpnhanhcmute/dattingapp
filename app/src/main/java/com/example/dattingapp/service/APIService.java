package com.example.dattingapp.service;

import com.example.dattingapp.DTO.AuthenticationRequest;
import com.example.dattingapp.Models.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface APIService {
    @POST("authentication")
    Call<ResponseModel> authentication(@Body AuthenticationRequest request);
}
