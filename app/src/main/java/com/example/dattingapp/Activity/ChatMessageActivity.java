package com.example.dattingapp.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattingapp.Adapter.MessageContentAdapter;
import com.example.dattingapp.DTO.ResponseModel;
import com.example.dattingapp.DTO.SendMessageRequest;
import com.example.dattingapp.Models.MessageContent;
import com.example.dattingapp.R;
import com.example.dattingapp.common.RetrofitClient;
import com.example.dattingapp.service.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatMessageActivity extends AppCompatActivity {

    RecyclerView rcMessage;
    MessageContentAdapter messageContentAdapter;
    List<MessageContent> messageContentList;
    ImageButton buttonSendMessage;
    ImageView backButton;
    EditText editTextMessage;

    String messageID;
    String userID;
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
        SetOnClickListener();
    }

    private void SetOnClickListener() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        messageID ="-NU1UaDhjx_n9PMJ-Gvg";
        userID = "81CjEd2CEkgvXrVjGIb7";
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageContent = editTextMessage.getText().toString();
                if(TextUtils.isEmpty(messageContent)){
                    return;
                }
                editTextMessage.setText("");
                editTextMessage.setFocusable(false);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextMessage.getWindowToken(), 0);

                SendMessageRequest request = new SendMessageRequest();
                request.userID = userID;
                request.messageID = messageID;
                request.content = messageContent;
                APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
                apiService.chat(request).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if(response.body().isError){
                            return;
                        }
                        Toast.makeText(ChatMessageActivity.this,response.message(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
        buttonSendMessage = findViewById(R.id.buttonSendMessage);
        backButton = (ImageView) findViewById(R.id.imageViewBack);
        editTextMessage = findViewById(R.id.editTextMessage);
    }

}