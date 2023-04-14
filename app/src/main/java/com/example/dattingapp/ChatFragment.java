package com.example.dattingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dattingapp.Adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private RecyclerView recyclerView;
    private ChatAdapter avatarAdapter;
    private List<Integer> avatarList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_chat, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);

        // Tạo danh sách các ảnh avatar
        avatarList = new ArrayList<>();
        avatarList.add(R.drawable.profile2);
        avatarList.add(R.drawable.profile2);
        avatarList.add(R.drawable.profile2);
        avatarList.add(R.drawable.profile2);
        avatarList.add(R.drawable.profile2);
        avatarList.add(R.drawable.profile2);
        avatarList.add(R.drawable.profile2);
        avatarList.add(R.drawable.profile2);

        // Tạo Adapter và kết nối với RecyclerView
        avatarAdapter = new ChatAdapter(avatarList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(avatarAdapter);
        return  view;
    }
}