package com.example.dattingapp.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.dattingapp.Adapter.ImagePagerAdapter;
import com.example.dattingapp.R;

public class ProfileActivity extends AppCompatActivity {

    private int[] images = {R.drawable.profile,R.drawable.profile2};

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_profile);
        ViewPager viewPager = findViewById(R.id.viewPager);
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, images);
        viewPager.setAdapter(adapter);
    }
}
