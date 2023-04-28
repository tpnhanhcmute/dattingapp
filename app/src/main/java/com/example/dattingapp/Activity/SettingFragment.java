package com.example.dattingapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.dattingapp.R;

public class SettingFragment extends Fragment {

    private Button buttonPersonalizeInfo;
    private  Button buttonDiscorverSetting;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);



    }

    private void SetListener() {
        buttonPersonalizeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),FillProfileActivity.class);
                startActivity(intent);
            }
        });
        buttonDiscorverSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Discovery.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        buttonPersonalizeInfo =view.findViewById(R.id.buttonContinue);
        buttonDiscorverSetting = view.findViewById(R.id.buttonDiscorverSetting);
        SetListener();
        return view;
    }
}
