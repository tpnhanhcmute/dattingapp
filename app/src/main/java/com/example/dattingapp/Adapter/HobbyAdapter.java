package com.example.dattingapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattingapp.Activity.MainActivity;
import com.example.dattingapp.R;
import com.example.dattingapp.utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class HobbyAdapter  extends RecyclerView.Adapter<HobbyAdapter.ViewHolder>{
    Context context;
    List<String> listHobby;
    List<String> hobbySelected;
    public  List<String> GetHobbySellected(){
        return hobbySelected;
    }
    public HobbyAdapter(Context context, List<String> listHobby) {
        this.listHobby = listHobby;
        this.context = context;
        hobbySelected= new ArrayList<>();
        this.hobbySelected = SharedPreference.getInstance(context).GetUser().hobby==null?new ArrayList<String>():SharedPreference.getInstance(context).GetUser().hobby;
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
        holder.hobby = listHobby.get(position);
        holder.SetSelected();
    }

    @Override
    public int getItemCount() {
        return listHobby==null?0:listHobby.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public  String hobby;
        public  Boolean isSelected;
        public TextView tvHobby;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHobby= itemView.findViewById(R.id.tvHobby);
            isSelected = true;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSelected) {
                        tvHobby.setBackgroundResource(R.drawable.sellect_hobby);
                        tvHobby.setTextColor(context.getResources().getColor(R.color.white));
                        hobbySelected.add(hobby);
                    }
                    else {
                        tvHobby.setBackgroundResource(R.drawable.light_violet_hobby);
                        tvHobby.setTextColor(context.getResources().getColor(R.color.light_black));
                        hobbySelected.remove(hobby);
                    }
                    isSelected = !isSelected;
                }
            });
        }
        public  void SetSelected(){
            if(hobbySelected.contains((hobby))){
                isSelected =false;
                tvHobby.setBackgroundResource(R.drawable.sellect_hobby);
                tvHobby.setTextColor(context.getResources().getColor(R.color.white));
            }
        }
    }
}
