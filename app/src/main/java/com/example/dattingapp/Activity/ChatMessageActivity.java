package com.example.dattingapp.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattingapp.Adapter.MessageContentAdapter;
import com.example.dattingapp.Models.MessageContent;
import com.example.dattingapp.R;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageActivity extends AppCompatActivity {

    RecyclerView rcMessage;
    MessageContentAdapter messageContentAdapter;
    List<MessageContent> messageContentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_message);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("localUser","nhan");
        editor.commit();
        Mapping();
        SetData();

        ImageView backButton = (ImageView) findViewById(R.id.imageViewBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void SetData() {
        messageContentList = new ArrayList<>();
        MessageContent messageContent = new MessageContent();
        messageContent.sender = "heo";
        MessageContent messageContent1 = new MessageContent();
        messageContent1.sender="nhan";
        messageContentList.add(messageContent1);
        messageContentList.add(messageContent1);
        messageContentList.add(messageContent);
        messageContentList.add(messageContent);
        messageContentList.add(messageContent1);
        messageContentList.add(messageContent);
        messageContentList.add(messageContent1);
        messageContentList.add(messageContent);



        messageContentAdapter = new MessageContentAdapter(this, messageContentList);
        rcMessage.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.VERTICAL,
                        false);
        rcMessage.setLayoutManager(layoutManager);
        rcMessage.setAdapter(messageContentAdapter);
        rcMessage.scrollToPosition(messageContentList.size()-1);
        messageContentAdapter.notifyDataSetChanged();

    }

    private void Mapping() {
        rcMessage = findViewById(R.id.recyclerViewMessage);
    }

}