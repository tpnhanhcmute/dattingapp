package com.example.dattingapp.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dattingapp.ChatMessageActivity;
import com.example.dattingapp.MainActivity;
import com.example.dattingapp.R;

import java.util.List;

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<Integer> avatarList;

    public ChatAdapter(List<Integer> avatarList) {
        this.avatarList = avatarList;
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

        holder.imageViewAvatar.setImageResource(avatarList.get(position));

    }

    @Override
    public int getItemCount() {
        return avatarList.size();
    }



}

