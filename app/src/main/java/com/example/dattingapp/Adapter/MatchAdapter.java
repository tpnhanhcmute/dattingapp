package com.example.dattingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattingapp.Models.Match;
import com.example.dattingapp.Activity.ProfileActivity;
import com.example.dattingapp.Models.User;
import com.example.dattingapp.R;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MyViewHolder>{
    private Context context;
    public Match matchList = new Match();

    public MatchAdapter(Context context, Match matchList) {
        this.context = context;
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_match, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = matchList.userList.get(position).fullName;
        holder.textView.setText(name);
    }

    @Override
    public int getItemCount() {
        return matchList== null?0:matchList.userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public  String id;
        TextView textView = itemView.findViewById(R.id.textViewName);
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
