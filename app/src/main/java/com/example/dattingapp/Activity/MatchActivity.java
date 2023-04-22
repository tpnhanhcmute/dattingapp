package com.example.dattingapp.Activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dattingapp.R;

public class MatchActivity  extends AppCompatActivity {
    ImageView imageViewIsMatch;
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Mapping();
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_and_scale);
        imageViewIsMatch.startAnimation(animation);
    }

    private void Mapping() {
        imageViewIsMatch = findViewById(R.id.imageViewIsAmatch);
    }
}
