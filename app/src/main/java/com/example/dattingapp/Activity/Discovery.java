package com.example.dattingapp.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import com.example.dattingapp.R;

public class Discovery extends AppCompatActivity {
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        btnBack = findViewById(R.id.btnBack);
        btndeagemax = findViewById(R.id.decrementButtonmax);
        btndeagemin = findViewById(R.id.decrementButtonmin);
        btninagemax = findViewById(R.id.incrementmax);
        btninagemin = findViewById(R.id.incrementButtonmin);
        btnindis = findViewById(R.id.incrementButtondistance);
        btndedis = findViewById(R.id.decrementButtondistance);


        textViewagemax = findViewById(R.id.agemax);
        textViewagemin = findViewById(R.id.agemin);
        textViewDistance = findViewById(R.id.distance);


        textViewagemax.setText(String.valueOf(filter.maxAge));
        textViewagemin.setText(String.valueOf(filter.minAge));
        textViewDistance.setText(String.valueOf(filter.distance/1000000));

        setclick(btndeagemax, btninagemax, textViewagemax);
        setclick(btndeagemin, btninagemin, textViewagemin);
        setclickDou(btndedis,btnindis,textViewDistance);
///hhahahha

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
