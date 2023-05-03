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
import android.widget.EditText;
import android.widget.ImageView;
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
    private ImageView imageViewBack;

    private Spinner spnGender;
    private EditText editTextMinAge;
    private EditText editTextMaxAge;
    private EditText editTextDistance;

    List<String> listGender;

    Filter filter = new Filter();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        spnGender = findViewById(R.id.spinnerGender);
        SharedPreference userShare = SharedPreference.getInstance(getApplicationContext());
        filter = userShare.GetFilter();

        SetData();
        BindingData();

        imageViewBack = findViewById(R.id.imageViewBack);
        editTextMinAge = findViewById(R.id.editTextMinAge);
        editTextMaxAge = findViewById(R.id.editTextMaxAge);
        editTextDistance = findViewById(R.id.editTextDistance);

        editTextMinAge.setText(String.valueOf(filter.maxAge));
        editTextMaxAge.setText(String.valueOf(filter.minAge));
        editTextDistance.setText(String.valueOf(filter.distance));
        int index = 0;
        for (int i=0;i<listGender.size();i++){
            if(listGender.get(i).equals(filter.gender))
            {
                index = i;
                break;
            }
        }
        spnGender.setSelection(index);


        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter.minAge = Integer.parseInt(editTextMinAge.getText().toString());
                filter.maxAge = Integer.parseInt(editTextMaxAge.getText().toString());
                filter.distance = (Double.parseDouble((editTextDistance.getText().toString())));
                filter.gender = spnGender.getSelectedItem().toString();
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

//--------------------------------------------Request Fill Profile--------------------------------------------//
    }

    private void SetData() {
        listGender = new ArrayList<>();
        listGender.add("Male");
        listGender.add("Female");
        listGender.add("Both");
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}


