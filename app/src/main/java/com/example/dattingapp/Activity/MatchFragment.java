package com.example.dattingapp.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dattingapp.Adapter.MatchAdapter;
import com.example.dattingapp.DTO.GetmatcModel;
import com.example.dattingapp.DTO.GetmatchRespone;
import com.example.dattingapp.DTO.RegisterResponse;
import com.example.dattingapp.DTO.UserRequest;
import com.example.dattingapp.DTO.ResponseModel;
import com.example.dattingapp.Models.Match;
import com.example.dattingapp.Models.User;
import com.example.dattingapp.R;
import com.example.dattingapp.common.RetrofitClient;
import com.example.dattingapp.service.APIService;
import com.example.dattingapp.utils.SharedPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchFragment extends Fragment {

    private RecyclerView recyclerViewMatch;
    public List<GetmatcModel> matchList;
    MatchAdapter matchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_match, container, false);
        Mapping(view);
        SetListener();

        AddData();

        SharedPreference userShare = SharedPreference.getInstance(getContext());
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        UserRequest UserRequest = new UserRequest();
        UserRequest.userID = userShare.GetUser().userID;
        apiService.getmatch(UserRequest).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body().isError){
                    Toast.makeText(getActivity(), "Error: "+ response.body().message,Toast.LENGTH_SHORT).show();
                    return;
                }
                Type type = new TypeToken<GetmatchRespone>(){}.getType();
                 GetmatchRespone getmatchRespone =  new Gson().fromJson(new Gson().toJson(response.body().data),type);
                 // sau khi parse xong

                matchList = getmatchRespone.match;
                matchAdapter = new MatchAdapter(getContext(), matchList);
                recyclerViewMatch.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager =
                        new GridLayoutManager(getContext().getApplicationContext(),2);
                recyclerViewMatch.setLayoutManager(layoutManager);
                recyclerViewMatch.setAdapter(matchAdapter);
                matchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });

        return  view;
    }

    private void AddData() {

    }

    private void SetListener() {

    }

    private void Mapping(View view) {
        recyclerViewMatch = view.findViewById(R.id.recyclerViewMatch);
    }
}