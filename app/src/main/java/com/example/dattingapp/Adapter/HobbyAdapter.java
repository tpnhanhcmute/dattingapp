package com.example.dattingapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattingapp.R;

import java.util.ArrayList;
import java.util.List;

public class HobbyAdapter  extends RecyclerView.Adapter<HobbyAdapter.ViewHolder>{
    Context context;
    List<String> listHobby;
    List<String> hobbySelected;
    public HobbyAdapter(Context context, List<String> listHobby) {
        this.listHobby = listHobby;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_hobby, parent, false);
        ViewHolder viewHolder =  new HobbyAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvHobby.setText(listHobby.get(position));
    }

    @Override
    public int getItemCount() {
        return listHobby==null?0:listHobby.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public  Boolean isSelected= false;
        public TextView tvHobby;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            isSelected= false;
            tvHobby= itemView.findViewById(R.id.tvHobby);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSelected) {
                        tvHobby.setBackgroundResource(R.drawable.sellect_hobby);
                        tvHobby.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    else {
                        tvHobby.setBackgroundResource(R.drawable.light_violet_hobby);
                        tvHobby.setTextColor(context.getResources().getColor(R.color.black));
                    }
                    isSelected = !isSelected;
                }
            });
        }
    }
}
