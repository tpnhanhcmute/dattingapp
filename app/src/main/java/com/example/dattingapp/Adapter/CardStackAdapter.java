package com.example.dattingapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattingapp.Activity.ProfileActivity;
import com.example.dattingapp.DTO.DiscoverModel;
import com.example.dattingapp.DTO.GetmatcModel;
import com.example.dattingapp.R;
import com.example.dattingapp.common.Const;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setData(items.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                DiscoverModel model =items.get(position);
                GetmatcModel getmatcModel = new GetmatcModel();
                getmatcModel.userID = model.userID;
                getmatcModel.fullName = model.fullName;
                getmatcModel.distance = model.distance;
                getmatcModel.age = model.age;
                getmatcModel.imageUrl = model.imageUrl.size() ==0?null:model.imageUrl.get(0);
                getmatcModel.hobby = model.hobby;
                getmatcModel.locationName = model.locationName;
                intent.putExtra(Const.USER, getmatcModel);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView image;
        TextView nama, age, city;
        RecyclerView rcHobby;
        DiscoverModel discoverModel;
        HobbyAdapter hobbyAdapter;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            nama = itemView.findViewById(R.id.item_name);
            age = itemView.findViewById(R.id.item_age);
            city = itemView.findViewById(R.id.item_city);
            rcHobby = itemView.findViewById(R.id.rcHobby);
        }

        void setData(DiscoverModel data) {

            discoverModel = data;
            if(data.getImage()!=""&& data.getImage()!= null){
                Picasso.get()
                        .load(data.getImage())
                        .fit()
                        .centerCrop()
                        .into(image);
            }
            nama.setText(data.fullName);
            age.setText(data.age.toString());
            city.setText(data.locationName);

            hobbyAdapter = new HobbyAdapter(itemView.getContext(), discoverModel.hobby,true);
            rcHobby.setHasFixedSize(true);

            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(itemView.getContext());
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            rcHobby.setLayoutManager(layoutManager);
            rcHobby.setAdapter(hobbyAdapter);
            hobbyAdapter.notifyDataSetChanged();
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
