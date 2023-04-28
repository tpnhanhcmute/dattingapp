package com.example.dattingapp.service;

import com.example.dattingapp.DTO.AuthenticationRequest;
import com.example.dattingapp.DTO.FillProfileRequest;
import com.example.dattingapp.DTO.GetmatchRequest;
import com.example.dattingapp.DTO.LoginRequest;
import com.example.dattingapp.DTO.RegisterRequest;
import com.example.dattingapp.DTO.ResponseModel;
import com.example.dattingapp.DTO.UploadImageRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {
    @POST("authentication")
    Call<ResponseModel> authentication(@Body AuthenticationRequest request);

    @POST("users/register")
    Call<ResponseModel> register(@Body RegisterRequest request);

    @POST("users/login")
    Call<ResponseModel> login(@Body LoginRequest request);

    @POST("users/update")
    Call<ResponseModel> update(@Body FillProfileRequest request);
    @POST("image/upload")
    Call<ResponseModel> uploadImage(@Body UploadImageRequest request);
    @POST("users/getmatch")
    Call<ResponseModel> getmatch(@Body GetmatchRequest request);
}
