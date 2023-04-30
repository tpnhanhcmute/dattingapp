package com.example.dattingapp.Adapter;

import androidx.recyclerview.widget.DiffUtil;

import com.example.dattingapp.DTO.DiscoverModel;
import com.example.dattingapp.Models.ItemModel;

import java.util.List;

public class CardStackCallback extends DiffUtil.Callback {

    private List<DiscoverModel> old, baru;

    public CardStackCallback(List<DiscoverModel> old, List<DiscoverModel> baru) {
        this.old = old;
        this.baru = baru;
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return baru.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition).getImage() == baru.get(newItemPosition).getImage();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition) == baru.get(newItemPosition);
    }
}
