package com.example.dattingapp.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.dattingapp.DTO.GetImageResponse;
import com.example.dattingapp.DTO.LoginRequest;
import com.example.dattingapp.DTO.LoginResponse;
import com.example.dattingapp.DTO.ResponseModel;
import com.example.dattingapp.DTO.UpdateLocationRequest;
import com.example.dattingapp.DTO.UserRequest;
import com.example.dattingapp.Models.User;
import com.example.dattingapp.R;
import com.example.dattingapp.common.RetrofitClient;
import com.example.dattingapp.observerpattern.Observer;
import com.example.dattingapp.observerpattern.Subject;
import com.example.dattingapp.service.APIService;
import com.example.dattingapp.utils.MessageManager;
import com.example.dattingapp.utils.SharedPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity implements Observer {

    private TextView txtSignup;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button continueButton;
    private  ImageButton imageButtonHintPassword;
    private  boolean isHintPassword =true;
    private static final int REQUEST_LOCATION_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        txtSignup = (TextView) findViewById(R.id.txtSignup);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassWord);
        continueButton = findViewById(R.id.buttonContinue);
        imageButtonHintPassword = findViewById(R.id.imageButtonHintPassword);
        MessageManager.getInstance().register(this);

        imageButtonHintPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isHintPassword){
                    editTextPassword.setTransformationMethod(null);
                    editTextPassword.setSelection(editTextPassword.getText().toString().length());
                    imageButtonHintPassword.setImageResource(R.drawable.ic_outline_remove_red_eye_24_active);
                }
                else {
                    editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
                    editTextPassword.setSelection(editTextPassword.getText().toString().length());
                    imageButtonHintPassword.setImageResource(R.drawable.ic_outline_remove_red_eye_24);
                }
                isHintPassword= !isHintPassword;
            }
        });

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SigninActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });

        ImageButton signinBack = findViewById(R.id.signinBack);

        signinBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    editTextEmail.setError("Please enter your email");
                    editTextEmail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    editTextPassword.setError("Pleasenter your password");
                    editTextPassword.requestFocus();
                    return;
                }

                APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.email = email;
                loginRequest.password = password;
                apiService.login(loginRequest).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if(!response.isSuccessful()) return;
                        if(response.body().isError){
                            Toast.makeText(getApplicationContext(), "Error: "+ response.body().message,Toast.LENGTH_SHORT).show();
                            return;
                        }
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                        Type type = new TypeToken<LoginResponse>(){}.getType();
                        LoginResponse loginResponse =  new Gson().fromJson(new Gson().toJson(response.body().data),type);
                        //Add builder
                        User user = loginResponse.user;
                        user.userID = loginResponse.id;
                        SharedPreference.getInstance(getApplicationContext()).SetUser(user);
                        GetLocation(user,apiService);
                        GetImage(user, apiService);
                        if(user.isFirstLogin){
                            Intent intent = new Intent(SigninActivity.this, FillProfileActivity.class);
                            startActivity(intent);
                            return;
                        }

                        Toast.makeText(getApplicationContext(), response.body().message,Toast.LENGTH_SHORT);

                        Intent intent = new Intent(SigninActivity.this, MainActivity.class);

                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void GetLocation(User user, APIService apiService) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                com.example.dattingapp.Models.Location loc = new com.example.dattingapp.Models.Location();
                loc.lat = location.getLatitude();
                loc.lng = location.getLongitude();
                SharedPreference.getInstance(getApplicationContext()).SetLocation(loc);
                UpdateLocationRequest updateLocationRequest = new UpdateLocationRequest();
                updateLocationRequest.userID = user.userID;
                updateLocationRequest.lat = loc.lat;
                updateLocationRequest.lng = loc.lng;
                apiService.updateLocation(updateLocationRequest).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.body().isError) {
                            finish();
                            return;
                        }
                        Type type = new TypeToken<com.example.dattingapp.Models.Location>() {
                        }.getType();
                        com.example.dattingapp.Models.Location getLocationResponse = new Gson().fromJson(new Gson().toJson(response.body().data), type);
                        SharedPreference.getInstance(getApplicationContext()).SetLocation(getLocationResponse);
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

                    }
                });
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                User user = SharedPreference.getInstance(getApplicationContext()).GetUser();
                APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
                GetLocation(user,apiService);
            } else {
                finish();
            }
        }
    }
    private void GetImage(User user, APIService apiService) {
        UserRequest userRequest = new UserRequest();
        userRequest.userID = user.userID;

        apiService.getImages(userRequest).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(!response.isSuccessful()) return;
                if(response.body().isError){
                    return;
                }
                Type type = new TypeToken<GetImageResponse>(){}.getType();
                GetImageResponse getImageResponse =  new Gson().fromJson(new Gson().toJson(response.body().data),type);

                SharedPreference.getInstance(getApplicationContext()).SetListImage(getImageResponse.listImage);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MessageManager.getInstance().unregister(this);
    }

    @Override
    public void update(Object object) {

    }

    @Override
    public void setSubject(Subject sub) {

    }
}