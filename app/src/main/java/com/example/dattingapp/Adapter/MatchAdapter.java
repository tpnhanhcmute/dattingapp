package com.example.dattingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dattingapp.DTO.GetmatcModel;
import com.example.dattingapp.Models.Match;
import com.example.dattingapp.Activity.ProfileActivity;
import com.example.dattingapp.Models.User;
import com.example.dattingapp.R;import com.google.android.material.imageview.ShapeableImageView;

import com.example.dattingapp.common.Const;
import java.io.Serializable;
import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MyViewHolder>{
    private Context context;
    public List<GetmatcModel> matchList ;



    public MatchAdapter(Context context, List<GetmatcModel> matchList) {
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
        String name = matchList.get(position).fullName;
        int age = matchList.get(position).age;
        holder.textView.setText(name);        holder.atexView.setText(Integer.toString(age));
        Glide.with(context).load(matchList.get(position).imageUrl).into(holder.shapeableImageView);
        holder.getMatchModel = matchList.get(position);    }

    @Override
    public int getItemCount() {
        return matchList== null?0:matchList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public  String id;
        TextView textView ;
        TextView atexView;

        public ShapeableImageView shapeableImageView;        public GetmatcModel getMatchModel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            atexView = itemView.findViewById(R.id.textViewNameOld);
            textView = itemView.findViewById(R.id.textViewName);
            shapeableImageView = itemView.findViewById(R.id.shapeableImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                    intent.putExtra(Const.USER, getMatchModel);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
