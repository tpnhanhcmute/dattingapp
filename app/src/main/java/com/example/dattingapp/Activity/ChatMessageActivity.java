package com.example.dattingapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattingapp.Adapter.MessageContentAdapter;
import com.example.dattingapp.DTO.MessageResponse;
import com.example.dattingapp.DTO.ResponseModel;
import com.example.dattingapp.DTO.SendMessageRequest;
import com.example.dattingapp.Models.MessageContent;
import com.example.dattingapp.Models.User;
import com.example.dattingapp.R;
import com.example.dattingapp.common.Const;
import com.example.dattingapp.common.RetrofitClient;
import com.example.dattingapp.observerpattern.MessageObserver;
import com.example.dattingapp.observerpattern.MessageObserverImpl;
import com.example.dattingapp.observerpattern.Observer;
import com.example.dattingapp.observerpattern.Subject;
import com.example.dattingapp.service.APIService;
import com.example.dattingapp.utils.MessageManager;
import com.example.dattingapp.utils.SharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatMessageActivity extends AppCompatActivity implements Observer {

    TextView textViewName;
    RecyclerView rcMessage;
    MessageContentAdapter messageContentAdapter;
    List<MessageContent> messageContentList;
    ImageButton buttonSendMessage;
    ImageView backButton;
    EditText editTextMessage;
    String messageID;
    String userID;
    String userName;
    private int previousHeight = 0;

    MessageObserverImpl messageObserver = new MessageObserverImpl() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void update(Object object) {
            DataSnapshot dataSnapshot = (DataSnapshot) object;
            messageContentList.clear();

            for (DataSnapshot child : dataSnapshot.getChildren()) {
                try {
                    MessageContent content = child.getValue(MessageContent.class);
                    if (content instanceof MessageContent) {
                        messageContentList.add((MessageContent) content);
                    }
                } catch (DatabaseException e) {

                }
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy, hh:mm:ss a");
            List<MessageContent> newList = new ArrayList<>();
            for(int i=0;i < messageContentList.size();i++)
            {
                if(i < messageContentList.size()-1){
                    try {
                        Date date = dateFormat.parse(messageContentList.get(i).date);
                        Date nextDate = dateFormat.parse(messageContentList.get(i+1).date);
                        SimpleDateFormat dateFormatHH = new SimpleDateFormat("M:dd:yyyy");
                        String formattedDateCurrent = dateFormatHH.format(date);
                        String nextDateFormat = dateFormatHH.format(nextDate);
                        if(i==0 || !(formattedDateCurrent.equals(nextDateFormat))) {
                            MessageContent messageContent = new MessageContent();
                            messageContent.senderID = "";
                            if(i ==0){
                                messageContent.date = messageContentList.get(i).date;
                            }else {
                                messageContent.date = messageContentList.get(i+1).date;
                            }
                            newList.add(messageContent);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                newList.add(messageContentList.get(i));
            };

            messageContentList.clear();
            messageContentList.addAll(newList);
            messageContentAdapter.notifyDataSetChanged();
            rcMessage.scrollToPosition(messageContentList.size() - 1);
        }

        @Override
        public void notify(Object object) {

        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        messageID =getIntent().getStringExtra(Const.MESSAGEID);
        userID = getIntent().getStringExtra(Const.USERID);
        userName = getIntent().getStringExtra(Const.USERNAME);
        Mapping();
        SetData();
        textViewName.setText(userName);

        SetOnClickListener();

        //----------------------------------Register event receiver message-------------------------
        MessageManager.getInstance().RegisterOnMessage(messageID,messageObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MessageManager.getInstance().unregister(this);
        MessageManager.getInstance().UnRegisterOnMessage(messageID, messageObserver);
        finish();
    }

    private void SetOnClickListener() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ViewTreeObserver vto = getWindow().getDecorView().getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                int currentHeight = rcMessage.getHeight();
                if (previousHeight != 0 && currentHeight < previousHeight) {
                    // Keyboard is showing
                    rcMessage.smoothScrollToPosition(messageContentList.size()-1);
                }
                previousHeight = currentHeight;
            }
        });


        User user = SharedPreference.getInstance(this).GetUser();
        user.userID = userID;
        SharedPreference.getInstance(this).SetUser(user);
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageContent = editTextMessage.getText().toString();
                if(TextUtils.isEmpty(messageContent)){
                    return;
                }
                editTextMessage.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextMessage.getWindowToken(), 0);

                SendMessageRequest request = new SendMessageRequest();
                request.userID = userID;
                request.messageID = messageID;
                request.content = messageContent;

                MessageContent content = new MessageContent();
                content.senderID = request.userID;
                content.content = request.content;
                content.isSending = true;
                messageContentList.add(content);
                messageContentAdapter.notifyDataSetChanged();
                rcMessage.scrollToPosition(messageContentList.size()-1);

                APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
                apiService.chat(request).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if(response.body().isError){
                            return;
                        }
                        messageContentList.remove(content);
                        messageContentAdapter.notifyDataSetChanged();
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
        textViewName = findViewById(R.id.textViewName);
    }

    //------------------------------------Observer-----------------------------------------------//
    @Override
    public void update(Object object) {
        Log.d("Test", object.toString());
    }

    @Override
    public void setSubject(Subject sub) {

    }
}