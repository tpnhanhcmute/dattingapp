package com.example.dattingapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class VerifyOtp extends AppCompatActivity {
    private EditText pin1EditText, pin2EditText, pin3EditText, pin4EditText;

    @Override
   protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_verity_otp);

       pin1EditText = findViewById(R.id.pin1);
       pin2EditText = findViewById(R.id.pin2);
       pin3EditText = findViewById(R.id.pin3);
       pin4EditText = findViewById(R.id.pin4);

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
   }
}



