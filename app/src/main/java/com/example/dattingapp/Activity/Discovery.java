package com.example.dattingapp.Activity;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.dattingapp.Adapter.HobbyAdapter;
import com.example.dattingapp.Models.Filter;
import com.example.dattingapp.Models.User;
import com.example.dattingapp.R;
import com.example.dattingapp.utils.SharedPreference;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

public class Discovery extends AppCompatActivity {
    private Button btnBack;

    private Spinner spnGender;

    private TextView textViewagemin;
    private TextView textViewagemax;
    private TextView textViewDistance;

    private Button btninagemax;
    private Button btndeagemax;
    private Button btninagemin;
    private Button btndeagemin;

    private Button btnindis;
    private Button btndedis;


    List<String> listGender;

    Filter filter = new Filter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        spnGender = findViewById(R.id.spinnerGender);
        SharedPreference userShare = SharedPreference.getInstance(getApplicationContext());
        filter = userShare.GetFilter();

        SetData();
        BindingData();
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
                //filter.distance = Double.parseDouble(textViewDistance.getText());
                filter.minAge = Integer.parseInt((String) textViewagemin.getText());
                filter.maxAge = Integer.parseInt((String) textViewagemax.getText());
                filter.distance = Double.parseDouble((String) textViewDistance.getText())*1000000;


                userShare.SetFilter(filter);
                onBackPressed();
            }
        });
        SwitchCompat mySwitch = findViewById(R.id.switch1);
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

    }

    @SuppressLint("SuspiciousIndentation")
    private void BindingData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                listGender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(adapter);
        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedHandler(parent, view, position, id);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void onItemSelectedHandler(AdapterView<?> parent, View view, int position, long id) {
        Adapter adapter = parent.getAdapter();
        String gender = (String)adapter.getItem(position);
        Toast.makeText(getApplicationContext(), "Selected Gender: " + gender ,Toast.LENGTH_SHORT).show();
//--------------------------------------------Request Fill Profile--------------------------------------------//
    }

    private void SetData() {
        listGender = new ArrayList<>();
        listGender.add("Male");
        listGender.add("Female");
        listGender.add("None");
    }


    private void setclick(Button decre, Button incre, TextView text){
        incre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = Integer.parseInt(text.getText().toString());
                text.setText(String.valueOf(currentValue + 1));
            }
        });

        decre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = Integer.parseInt(text.getText().toString());
                if (currentValue > 0) {
                    text.setText(String.valueOf(currentValue - 1));
                }
            }
        });
    }

    private void setclickDou(Button decre, Button incre, TextView text){
        incre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double currentValue = Double.parseDouble(text.getText().toString());
                text.setText(String.valueOf(currentValue + 1));
            }
        });

        decre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double currentValue = Double.parseDouble(text.getText().toString());
                if (currentValue > 0) {
                    text.setText(String.valueOf(currentValue - 1));
                }
            }
        });
    }

}


