package com.example.dattingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dattingapp.Activity.ChatMessageActivity;
import com.example.dattingapp.DTO.ChatModel;
import com.example.dattingapp.DTO.GetmatcModel;
import com.example.dattingapp.R;

import java.util.List;

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<ChatModel> avatarList;

    private Context context;

    public ChatAdapter(Context context, List<ChatModel> avatarList) {
        this.avatarList = avatarList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
            itemView.setOnClickListener(new View.OnClickListener(){

                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), ChatMessageActivity.class);
                    v.getContext().startActivity(intent);
                }
            });

        }

    }


    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_avatar, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Glide.with(context).load(avatarList.get(position).imageUrl).into(holder.imageViewAvatar);

    }

    @Override
    public int getItemCount() {
        return avatarList.size();
    }



}

