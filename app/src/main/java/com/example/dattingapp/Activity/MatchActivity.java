package com.example.dattingapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dattingapp.R;
import com.example.dattingapp.common.Const;
import com.google.android.material.imageview.ShapeableImageView;

public class MatchActivity  extends AppCompatActivity {
    ImageView imageViewIsMatch;
    private String userID;
    private  String imageUrl;
    private  String userName;
    private  String messageID;
    Button buttonChatNow;
    Button buttonKeepSwipt;
    ShapeableImageView shapeableImageView;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Mapping();

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_and_scale);
        imageViewIsMatch.startAnimation(animation);
        userID = getIntent().getStringExtra(Const.USERID);
        userName = getIntent().getStringExtra(Const.USERNAME);
        imageUrl = getIntent().getStringExtra(Const.IMAGEURL);
        messageID = getIntent().getStringExtra(Const.MESSAGEID);

        Glide.with(this).load(imageUrl).into(shapeableImageView);
        SetOnCLickListener();


    }
    private  void SetOnCLickListener(){
        buttonKeepSwipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        buttonChatNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchActivity.this, ChatMessageActivity.class);
                intent.putExtra(Const.MESSAGEID, messageID);
                intent.putExtra(Const.USERID, userID);
                intent.putExtra(Const.USERNAME, userName);
                startActivity(intent);
            }
        });
    }

    private void Mapping() {
        imageViewIsMatch = findViewById(R.id.imageViewIsAmatch);
        buttonChatNow= findViewById(R.id.buttonChatNow);
        buttonKeepSwipt = findViewById(R.id.buttonKeepSwipt);
        shapeableImageView = findViewById(R.id.shapeableImageView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
