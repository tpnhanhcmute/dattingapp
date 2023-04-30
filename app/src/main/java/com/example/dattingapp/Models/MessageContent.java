package com.example.dattingapp.Models;

import java.util.Objects;

public class MessageContent {
    public  String senderID;
    public  String content;
    public  String date;
    public  boolean isSending = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageContent that = (MessageContent) o;
        return Objects.equals(senderID, that.senderID) && Objects.equals(content, that.content) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderID, content, date);
    }
}
