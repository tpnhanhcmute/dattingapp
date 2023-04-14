package com.example.dattingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;

public class Discovery extends ProfileActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        SwitchCompat mySwitch = findViewById(R.id.switch1);
        SwitchCompat mySwitch2 = findViewById(R.id.switch2);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Nếu nút Switch được bật, thay đổi màu trackTint sang màu khác
                    mySwitch.getTrackDrawable().setTint(getResources().getColor(R.color.light_violet));
                } else {
                    // Nếu nút Switch được tắt, trả lại màu trackTint về giá trị ban đầu
                    mySwitch.getTrackDrawable().setTint(getResources().getColor(R.color.gray));
                }
            }
        });

        mySwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Nếu nút Switch được bật, thay đổi màu trackTint sang màu khác
                    mySwitch2.getTrackDrawable().setTint(getResources().getColor(R.color.light_violet));
                } else {
                    // Nếu nút Switch được tắt, trả lại màu trackTint về giá trị ban đầu
                    mySwitch2.getTrackDrawable().setTint(getResources().getColor(R.color.gray));
                }
            }
        });

    }
}
