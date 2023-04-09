package com.example.dattingapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dattingapp.Models.UploadImageModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddPictureActivity extends AppCompatActivity {

    public  static  final  int MY_REQUEST_CODE = 100;
    public static String[] storge_permissions = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storge_permissions_33 =
            {
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.READ_MEDIA_AUDIO,
                    android.Manifest.permission.READ_MEDIA_VIDEO
            };

    RelativeLayout relativeLayout1;
    RelativeLayout relativeLayout2;
    RelativeLayout relativeLayout3;
    RelativeLayout relativeLayout4;

    ShapeableImageView shapeableImageView1;
    ShapeableImageView shapeableImageView2;
    ShapeableImageView shapeableImageView3;
    ShapeableImageView shapeableImageView4;

    ImageButton imageButtonCancel1;
    ImageButton imageButtonCancel2;
    ImageButton imageButtonCancel3;
    ImageButton imageButtonCancel4;

    RelativeLayout relativeLayoutChoose;
    Uri mUri;

    HashMap< RelativeLayout, UploadImageModel> viewMapping = new HashMap<>();

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);
        Mapping();
        SetListener();
        SetStatusCancelButton();
    }

    private void SetStatusCancelButton() {
        for(Map.Entry<RelativeLayout, UploadImageModel> entry :viewMapping.entrySet()) {
            ShapeableImageView imageView = entry.getValue().shapeableImageView;
            Drawable drawable = imageView.getDrawable();
            if (drawable == null) {
                // Drawable rỗng
                entry.getValue().CancelUpload.setVisibility(View.GONE);
                Log.d("TAG", "myDrawable is empty");
            } else {
                entry.getValue().CancelUpload.setVisibility(View.VISIBLE);
                // Drawable không rỗng
                Log.d("TAG", "myDrawable is not empty");
            }
        }
    }

    private  void SetListener(){
        for(Map.Entry<RelativeLayout, UploadImageModel> entry :viewMapping.entrySet()){

            entry.getKey().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    relativeLayoutChoose = entry.getKey();
                    CheckPermission();
                }
            });
            entry.getValue().CancelUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    entry.getValue().shapeableImageView.setImageDrawable(null);
                    SetStatusCancelButton();
                    entry.getValue().uri = null;
                }
            });
        }
    }
    private  void Mapping(){
        relativeLayout1 = findViewById(R.id.relativeLayout1);
        relativeLayout2 = findViewById(R.id.relativeLayout2);
        relativeLayout3 = findViewById(R.id.relativeLayout3);
        relativeLayout4 = findViewById(R.id.relativeLayout4);

        shapeableImageView1 = findViewById(R.id.shapeableImageView1);
        shapeableImageView2 = findViewById(R.id.shapeableImageView2);
        shapeableImageView3 = findViewById(R.id.shapeableImageView3);
        shapeableImageView4 = findViewById(R.id.shapeableImageView4);

        imageButtonCancel1 = findViewById(R.id.imageButtonCancel1);
        imageButtonCancel2 = findViewById(R.id.imageButtonCancel2);
        imageButtonCancel3 = findViewById(R.id.imageButtonCancel3);
        imageButtonCancel4 = findViewById(R.id.imageButtonCancel4);

        UploadImageModel uploadImageModel1 = new UploadImageModel(shapeableImageView1,imageButtonCancel1);
        UploadImageModel uploadImageModel2 = new UploadImageModel(shapeableImageView2,imageButtonCancel2);
        UploadImageModel uploadImageModel3 = new UploadImageModel(shapeableImageView3,imageButtonCancel3);
        UploadImageModel uploadImageModel4 = new UploadImageModel(shapeableImageView4,imageButtonCancel4);

        viewMapping.put(relativeLayout1,uploadImageModel1);
        viewMapping.put(relativeLayout2,uploadImageModel2);
        viewMapping.put(relativeLayout3,uploadImageModel3);
        viewMapping.put(relativeLayout4,uploadImageModel4);
    }
    private void CheckPermission() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else {
            requestPermissions(permissions(),MY_REQUEST_CODE);
        }
    }
    public static String[] permissions() {
        String[] p;
        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.TIRAMISU){
            p = storge_permissions_33;
        }else {
            p= storge_permissions;
        }
        return p;
    }
    private  void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent,"Sellect Picture"));
    }
    private ActivityResultLauncher<Intent>
            mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e("TAG", "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        viewMapping.get(relativeLayoutChoose).uri = uri;
                        try {
                            Bitmap bitmap =
                                    MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            viewMapping.get(relativeLayoutChoose).shapeableImageView.setImageBitmap(bitmap);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            SetStatusCancelButton();
                        }
                    }
                }
            }
    );

}
