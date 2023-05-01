package com.example.dattingapp.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {

    private Context context;
    private List<String> imagesUrl;

    public ImagePagerAdapter(Context context, List<String> images) {
        this.context = context;
        this.imagesUrl = images;
    }

    @Override
    public int getCount() {
        return imagesUrl.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);

        String image = imagesUrl.get(position);
        Glide.with(context).load(image).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
