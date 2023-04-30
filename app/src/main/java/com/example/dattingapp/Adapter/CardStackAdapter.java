package com.example.dattingapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattingapp.DTO.DiscoverModel;
import com.example.dattingapp.Models.ItemModel;
import com.example.dattingapp.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private List<DiscoverModel> items;
    private Context context;

    public CardStackAdapter(List<DiscoverModel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_swipe_support, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView image;
        TextView nama, usia, kota;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            nama = itemView.findViewById(R.id.item_name);
            usia = itemView.findViewById(R.id.item_age);
            kota = itemView.findViewById(R.id.item_city);
        }

        void setData(DiscoverModel data) {
            Picasso.get()
                    .load(data.getImage())
                    .fit()
                    .centerCrop()
                    .into(image);
            nama.setText(data.fullName);
//            usia.setText(data.getUsia());
//            kota.setText(data.getKota());
        }
    }

    public List<DiscoverModel> getItems() {
        return items;
    }

    public void setItems(List<DiscoverModel> items) {
        this.items = items;
    }
    public  void AddItem(List<DiscoverModel> items){
        for (DiscoverModel item :items){
            this.items.add(item);
        }
    }
}
