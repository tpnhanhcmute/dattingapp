package com.example.dattingapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.dattingapp.DTO.ResponseModel;
import com.example.dattingapp.DTO.UserRequest;
import com.example.dattingapp.R;
import com.example.dattingapp.common.CustomProgressDialog;
import com.example.dattingapp.common.RetrofitClient;
import com.example.dattingapp.service.APIService;
import com.example.dattingapp.utils.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingFragment extends Fragment {

    private Button buttonPersonalizeInfo;
    private  Button buttonDiscorverSetting;
    private  Button buttonLogout;
    private CustomProgressDialog progressDialog;
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
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });
    }

    private void LogOut() {
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        UserRequest userRequest = new UserRequest();
        userRequest.userID = SharedPreference.getInstance(getContext()).GetUser().userID;
        progressDialog.show();
        apiService.logout(userRequest).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                progressDialog.dismiss();
                if(!response.isSuccessful() || response.body().isError) return;

                SharedPreference.getInstance(getContext()).SetUser(null);
                SharedPreference.getInstance(getContext()).SetListImage(null);

                Intent i = getContext().getPackageManager().getLaunchIntentForPackage( getContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressDialog.dismiss();
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
        buttonLogout = view.findViewById(R.id.buttonLogout);
        SetListener();
        progressDialog = new CustomProgressDialog(getContext());
        return view;
    }
}
