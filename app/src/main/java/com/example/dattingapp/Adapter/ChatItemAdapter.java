package com.example.dattingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dattingapp.Activity.ChatMessageActivity;
import com.example.dattingapp.DTO.ChatModel;
import com.example.dattingapp.DTO.GetmatcModel;
import com.example.dattingapp.Models.ChatItem;
import com.example.dattingapp.R;

import java.util.List;

public class ChatItemAdapter extends RecyclerView.Adapter<ChatItemAdapter.ChatViewHolder> {
    private List<ChatModel> mChatList;

    private Context context;

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView textMessage;

        public ImageView imageViewAvatar;
        public ChatViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
//            textMessage = itemView.findViewById(R.id.text_message);
            imageViewAvatar = itemView.findViewById(R.id.imageviewav);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ChatMessageActivity.class);
                    v.getContext().startActivity(intent);
                }
            });

        }
    }

    public ChatItemAdapter(Context context,List<ChatModel> chatList) {
        this.context = context;
        this.mChatList = chatList;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        ChatViewHolder viewHolder = new ChatViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatModel currentItem = mChatList.get(position);
        holder.textName.setText(currentItem.fullName);
        Glide.with(context).load(mChatList.get(position).imageUrl).into(holder.imageViewAvatar);

        //holder.textMessage.setText(currentItem.getmMessage());
    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }
}
