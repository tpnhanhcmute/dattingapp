package com.example.dattingapp.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dattingapp.Adapter.MatchAdapter;
import com.example.dattingapp.DTO.GetmatchRequest;
import com.example.dattingapp.DTO.GetmatchRespone;
import com.example.dattingapp.DTO.ResponseModel;
import com.example.dattingapp.Models.Match;
import com.example.dattingapp.Models.User;
import com.example.dattingapp.R;
import com.example.dattingapp.common.RetrofitClient;
import com.example.dattingapp.service.APIService;
import com.example.dattingapp.utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchFragment extends Fragment {

    private RecyclerView recyclerViewMatch;
    List<Match> matchList;
    MatchAdapter matchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_match, container, false);
        SharedPreference userShare = SharedPreference.getInstance(getContext());
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        GetmatchRequest getmatchRequest = new GetmatchRequest();
        getmatchRequest.id = userShare.GetUser().userID;

        apiService.getmatch(getmatchRequest).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body().isError){
                    Toast.makeText(getActivity(), "Error: "+ response.body().message,Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Toast.makeText(getActivity(), "hello",Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });




//        Mapping(view);
//        SetListener();
//        AddData();
        return  view;
    }

    private void AddData() {
        matchList = new ArrayList<>();
        matchList.add(new Match());


        matchAdapter = new MatchAdapter(this.getContext(), matchList);
        recyclerViewMatch.setHasFixedSize(true);



        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(getContext().getApplicationContext(),2);
        recyclerViewMatch.setLayoutManager(layoutManager);
        recyclerViewMatch.setAdapter(matchAdapter);

        //recyclerViewMatch.scrollToPosition(messageList.size()-1);
        matchAdapter.notifyDataSetChanged();
    }

    private void SetListener() {

    }

    private void Mapping(View view) {
        recyclerViewMatch = view.findViewById(R.id.recyclerViewMatch);
    }
}