package com.example.dattingapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dattingapp.DTO.AuthenticationRequest;
import com.example.dattingapp.DTO.ResponseModel;
import com.example.dattingapp.R;
import com.example.dattingapp.common.Const;
import com.example.dattingapp.common.RetrofitClient;
import com.example.dattingapp.service.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtp extends AppCompatActivity {
    private EditText pin1EditText, pin2EditText, pin3EditText, pin4EditText;
    private Button continueButton;
    private String otp = "";
    private  String userID="";
    @Override
   protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_verity_otp);

       pin1EditText = findViewById(R.id.pin1);
       pin2EditText = findViewById(R.id.pin2);
       pin3EditText = findViewById(R.id.pin3);
       pin4EditText = findViewById(R.id.pin4);
       continueButton = findViewById(R.id.buttonContinue);

       pin1EditText.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               if (s.length() == 1) {
                   pin2EditText.requestFocus();
               }
           }

           @Override
           public void afterTextChanged(Editable s) {}
       });

       pin2EditText.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               if (s.length() == 1) {
                   pin3EditText.requestFocus();
               }
           }

           @Override
           public void afterTextChanged(Editable s) {}
       });

       pin3EditText.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               if (s.length() == 1) {
                   pin4EditText.requestFocus();
               }
           }

           @Override
           public void afterTextChanged(Editable s) {}
       });

       pin4EditText.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {}

           @Override
           public void afterTextChanged(Editable s) {}
       });

       continueButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String pin1  = pin1EditText.getText().toString();
               String pin2 = pin2EditText.getText().toString();
               String pin3 = pin3EditText.getText().toString();
               String pin4 = pin4EditText.getText().toString();

               if(pin1 == "" || pin2 == "" || pin3 == "" || pin4 == "")
               {
                   Toast.makeText(getApplicationContext(), "Please fill all pin ", Toast.LENGTH_SHORT );
                   return;
               }
               String otp = getIntent().getStringExtra(Const.OTP);
               if(!(pin1+pin2+pin3+pin4).equals(otp)){
                   Toast.makeText(getApplicationContext(), "Authentication failure", Toast.LENGTH_SHORT);
                   return;
               }
               AuthenticationRequest authenticationRequest = new AuthenticationRequest();
               authenticationRequest.email = getIntent().getStringExtra(Const.EMAIL);
               APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
               apiService.authentication(authenticationRequest).enqueue(new Callback<ResponseModel>() {
                   @Override
                   public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if(response.body().isError){
                            Toast.makeText(getApplicationContext(), response.body().message, Toast.LENGTH_SHORT).show();
                            return;
                        }
                            Toast.makeText(getApplicationContext(), response.body().message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(VerifyOtp.this, SigninActivity.class);
                            startActivity(intent);

                   }
                   @Override
                   public void onFailure(Call<ResponseModel> call, Throwable t) {
                       Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               });
           }
       });
   }

    @Override
    protected void onPause() {
        super.onPause();
    }
}



