package com.example.dattingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Choose_Signin_Signup extends AppCompatActivity {

    private Button mSignin , mSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mSignin = (Button) findViewById(R.id.btn_signin);
        mSignup = (Button) findViewById(R.id.btn_signup);

        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Choose_Signin_Signup.this,SigninActivity.class);
                startActivity(i);
            }
        });

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Choose_Signin_Signup.this,SignupActivity.class);
                startActivity(i);
            }
        });
    }
}