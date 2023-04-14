package com.example.dattingapp.Models;

public class ChatItem {
    private String mName;
    private String mMessage;

    public ChatItem(String name, String message){
        mName = name;
        mMessage = message;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmName() {
        return mName;
    }

    public String getmMessage() {
        return mMessage;
    }
}
