package com.example.dattingapp.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dattingapp.Adapter.ChatAdapter;
import com.example.dattingapp.Adapter.ChatItemAdapter;
import com.example.dattingapp.Models.ChatItem;
import com.example.dattingapp.R;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private RecyclerView recyclerView;
    private ChatAdapter avatarAdapter;
    private List<Integer> avatarList;

    private  RecyclerView mRecyclerView;

    private ChatItemAdapter mAdapter;

    private List<ChatItem> chatList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        // Tạo danh sách các ảnh avatar
        avatarList = new ArrayList<>();
        avatarList.add(R.drawable.baseline_tag_faces_24);
        avatarList.add(R.drawable.baseline_tag_faces_24);
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


        chatList.add(new ChatItem("Alice", "Hello!"));
        chatList.add(new ChatItem("Bob", "Hi Alice!"));
        chatList.add(new ChatItem("Alice", "How are you?"));
        chatList.add(new ChatItem("Bob", "I'm fine. Thanks for asking."));
        chatList.add(new ChatItem("Bob", "I'm fine. Thanks for asking."));
        chatList.add(new ChatItem("Bob", "I'm fine. Thanks for asking."));
        chatList.add(new ChatItem("Bob", "I'm fine. Thanks for asking."));
        chatList.add(new ChatItem("Bob", "I'm fine. Thanks for asking."));
        chatList.add(new ChatItem("Bob", "I'm fine. Thanks for asking."));
        chatList.add(new ChatItem("Bob", "I'm fine. Thanks for asking."));
        chatList.add(new ChatItem("Bob", "I'm fine. Thanks for asking."));
        chatList.add(new ChatItem("Bob", "I'm fine. Thanks for asking."));
        chatList.add(new ChatItem("Bob", "I'm fine. Thanks for asking."));
        chatList.add(new ChatItem("Bob", "I'm fine. Thanks for asking."));
        chatList.add(new ChatItem("Bob", "I'm fine. Thanks for asking."));
        chatList.add(new ChatItem("Bob", "I'm fine. Thanks for asking."));
        chatList.add(new ChatItem("Bob", "I'm fine. Thanks for asking."));
        chatList.add(new ChatItem("Bob", "I'm fine. Thanks for asking."));


        mRecyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mAdapter = new ChatItemAdapter(chatList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return  view;
    }
}