package com.example.dattingapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dattingapp.Models.ChatItem;
import com.example.dattingapp.R;

import java.util.List;

public class ChatItemAdapter extends RecyclerView.Adapter<ChatItemAdapter.ChatViewHolder> {
    private List<ChatItem> mChatList;

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView textMessage;

        public ChatViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            textMessage = itemView.findViewById(R.id.text_message);
        }
    }

    public ChatItemAdapter(List<ChatItem> chatList) {
        mChatList = chatList;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        ChatViewHolder viewHolder = new ChatViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatItem currentItem = mChatList.get(position);
        holder.textName.setText(currentItem.getmName());
        holder.textMessage.setText(currentItem.getmMessage());
    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }
}
