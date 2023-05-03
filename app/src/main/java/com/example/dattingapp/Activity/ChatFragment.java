package com.example.dattingapp.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dattingapp.Adapter.ChatAdapter;
import com.example.dattingapp.Adapter.ChatItemAdapter;
import com.example.dattingapp.DTO.ChatModel;
import com.example.dattingapp.DTO.GetChatRespone;
import com.example.dattingapp.DTO.GetmatcModel;
import com.example.dattingapp.DTO.GetmatchRespone;
import com.example.dattingapp.DTO.ResponseModel;
import com.example.dattingapp.DTO.UserRequest;
import com.example.dattingapp.Models.ChatItem;
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

public class ChatFragment extends Fragment {
    private RecyclerView recyclerView;
    private ChatAdapter avatarAdapter;


    private  RecyclerView mRecyclerView;

    private ChatItemAdapter mAdapter;



    public List<ChatModel> ChatList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        SharedPreference userShare = SharedPreference.getInstance(getContext());
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        UserRequest UserRequest = new UserRequest();
        UserRequest.userID = userShare.GetUser().userID;
        apiService.getConver(UserRequest).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(!response.isSuccessful()) return;
                if (response.body().isError) {
                    Toast.makeText(getActivity(), "Error: " + response.body().message, Toast.LENGTH_SHORT).show();
                    return;
                }
                Type type = new TypeToken<GetChatRespone>() {}.getType();
                Gson gson = new Gson();
                GetChatRespone getChatRespone = new Gson().fromJson(new Gson().toJson(response.body().data), type);
                // sau khi parse xong
                ChatList = getChatRespone.conver;

                avatarAdapter = new ChatAdapter(getContext(),ChatList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(avatarAdapter);


                mRecyclerView = view.findViewById(R.id.recyclerView);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                mAdapter = new ChatItemAdapter(getContext(),ChatList);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });

        return  view;
    }
}