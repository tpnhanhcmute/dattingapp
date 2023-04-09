package com.example.dattingapp.Models;

import android.net.Uri;
import android.widget.ImageButton;

import com.google.android.material.imageview.ShapeableImageView;

public class UploadImageModel {
    public UploadImageModel(ShapeableImageView shapeableImageView, ImageButton cancelUpload) {
        this.shapeableImageView = shapeableImageView;
        CancelUpload = cancelUpload;
    }

    public ShapeableImageView shapeableImageView;
    public ImageButton CancelUpload;
    public Uri uri;
}
