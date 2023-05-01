package com.example.dattingapp.service;

import com.example.dattingapp.Activity.ChatMessageActivity;
import com.example.dattingapp.DTO.AuthenticationRequest;
import com.example.dattingapp.DTO.DiscorverRequest;
import com.example.dattingapp.DTO.FillProfileRequest;
import com.example.dattingapp.DTO.LikeRequest;
import com.example.dattingapp.DTO.SendMessageRequest;
import com.example.dattingapp.DTO.UpdateLocationRequest;
import com.example.dattingapp.DTO.UserRequest;
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

    @POST("images/upload")
    Call<ResponseModel> uploadImage(@Body UploadImageRequest request);

    @POST("users/getmatch")
    Call<ResponseModel> getmatch(@Body UserRequest request);

    @POST("images/getImages")
    Call<ResponseModel> getImages(@Body UserRequest request);

    @POST("location/update")
    Call<ResponseModel> updateLocation(@Body UpdateLocationRequest request);

    @POST("users/chat")
    Call<ResponseModel> chat(@Body SendMessageRequest request);

    @POST("users/discorver")
    Call<ResponseModel> getUserDiscorver(@Body DiscorverRequest request);

    @POST("users/like")
    Call<ResponseModel> like(@Body LikeRequest request);

    @POST("users/getUser")
    Call<ResponseModel> getProfile(@Body UserRequest request);


}
