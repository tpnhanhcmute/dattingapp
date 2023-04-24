package com.example.dattingapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dattingapp.DTO.RegisterRequest;
import com.example.dattingapp.DTO.RegisterResponse;
import com.example.dattingapp.DTO.ResponseModel;
import com.example.dattingapp.Models.Location;
import com.example.dattingapp.R;
import com.example.dattingapp.common.Const;
import com.example.dattingapp.common.RetrofitClient;
import com.example.dattingapp.service.APIService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private TextView txtSignin;
    private  ImageButton signupBack;
    private Button continueButton;
    private EditText  editTextEmail;
    private  EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Mapping();
        SetListener();
    }
    private void  Mapping(){
        txtSignin = (TextView)findViewById(R.id.txtSignin);
        signupBack = findViewById(R.id.signupBack);
        continueButton = findViewById(R.id.continueButton);
        editTextPassword = findViewById(R.id.editTextPassWord);
        editTextEmail = findViewById(R.id.editTextEmail);
    }
    private  void SetListener(){
        txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this,SigninActivity.class);
                startActivity(i);
            }
        });

        signupBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = editTextEmail.getText().toString();
                final String password = editTextPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    editTextEmail.setError("Please enter your email");
                    editTextEmail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    editTextPassword.setError("Pleas enter your password");
                    editTextPassword.requestFocus();
                    return;
                }

                APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
                RegisterRequest request  = new RegisterRequest();
                request.email = email;
                request.password = password;
                apiService.register(request).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if(response.body().isError){
                            Toast.makeText(getApplicationContext(), response.body().message, Toast.LENGTH_SHORT);
                            return;
                        }
                        Type type = new TypeToken<RegisterResponse>(){}.getType();
                        RegisterResponse registerResponse =  new Gson().fromJson(response.body().data.toString(),type);
                        Intent intent = new Intent(SignupActivity.this, VerifyOtp.class);
                        intent.putExtra(Const.OTP,registerResponse.otp);
                        intent.putExtra(Const.EMAIL, registerResponse.email);
                        startActivity(intent);
                    }
                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

                    }
                });


            }
        });
    }
}