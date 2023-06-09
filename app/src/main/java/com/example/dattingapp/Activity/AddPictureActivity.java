package com.example.dattingapp.Activity;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dattingapp.DTO.GetImageResponse;
import com.example.dattingapp.DTO.ResponseModel;
import com.example.dattingapp.DTO.UploadImageRequest;
import com.example.dattingapp.Models.UploadImageModel;
import com.example.dattingapp.Models.User;
import com.example.dattingapp.R;
import com.example.dattingapp.common.CustomProgressDialog;
import com.example.dattingapp.common.RetrofitClient;
import com.example.dattingapp.service.APIService;
import com.example.dattingapp.service.FireStoreService;
import com.example.dattingapp.utils.SharedPreference;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPictureActivity extends AppCompatActivity {

    public static final int MY_REQUEST_CODE = 100;
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

    Button buttonContinue;
    ImageView imageViewBack;
    RelativeLayout relativeLayoutChoose;
    Uri mUri;
    HashMap<RelativeLayout, UploadImageModel> viewMapping = new HashMap<>();

    CustomProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);
        progressDialog = new CustomProgressDialog(this);
        Mapping();
        SetListener();
        BindingData();
    }
    private void BindingData() {
        List<String> imageUrls = SharedPreference.getInstance(this).GetImageList();
        int index= 0;
        int count = imageUrls == null?-1:imageUrls.size();

        for(Map.Entry<RelativeLayout, UploadImageModel>entry: viewMapping.entrySet()){
            if(index < count && imageUrls != null){

                String url = imageUrls.get(index);
                entry.getValue().url = url;
                entry.getValue().uri = null;
                Glide.with(this).load(url).into(entry.getValue().shapeableImageView);
                entry.getValue().CancelUpload.setVisibility(View.VISIBLE);
            }else {
                entry.getValue().CancelUpload.setVisibility(View.GONE);
            }
            index++;
        }
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
                    entry.getValue().url = "";
                    entry.getValue().uri = null;
                }
            });
        }
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Uri> uris = new ArrayList<Uri>();
                List<String> urls = new ArrayList<>();
                for(Map.Entry<RelativeLayout, UploadImageModel> entry :viewMapping.entrySet()){
                    if(entry.getValue().uri != null){
                        uris.add(entry.getValue().uri);
                    }
                    if(entry.getValue().url != "" && entry.getValue().url != null){
                        urls.add(entry.getValue().url);
                    }
                }
                progressDialog.show();
                if(uris.size() <= 0){

                    UpLoadImage(urls);
                    return;
                }
                FireStoreService.getInstance().UploadImage(uris, new OnSuccessListener<List<String>>() {
                    @Override
                    public void onSuccess(List<String> listUrls) {

                        Log.d("UploadImage", String.valueOf(listUrls.size()));
                        listUrls.addAll(urls);
                        UpLoadImage(listUrls);
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }
    private  void UpLoadImage(List<String> urls){

        User user = SharedPreference.getInstance(AddPictureActivity.this).GetUser();
        UploadImageRequest request = new UploadImageRequest();
        request.userID = user.userID;
        request.listImage = urls;
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.uploadImage(request).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                progressDialog.dismiss();
                if(response.body().isError) {
                    Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreference.getInstance(AddPictureActivity.this).SetListImage(urls);
                Intent intent = new Intent(AddPictureActivity.this, FillProfileActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
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

        buttonContinue= findViewById(R.id.buttonContinue);

        viewMapping.put(relativeLayout1,uploadImageModel1);
        viewMapping.put(relativeLayout2,uploadImageModel2);
        viewMapping.put(relativeLayout3,uploadImageModel3);
        viewMapping.put(relativeLayout4,uploadImageModel4);
        imageViewBack = findViewById(R.id.imageViewBack);

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
                        viewMapping.get(relativeLayoutChoose).url = "";
                        Log.d("IMAGE",uri.getPath());
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

    @Override
    protected void onPause() {
        super.onPause();
    }
}
