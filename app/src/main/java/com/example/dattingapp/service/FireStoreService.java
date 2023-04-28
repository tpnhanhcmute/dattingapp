package com.example.dattingapp.service;
import android.net.Uri;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;


public class FireStoreService extends Application {

    public  static String TAG = "UPLOAD IMAGE";
    public static FireStoreService _instance;
    public static  FireStoreService getInstance(){
        if(_instance == null) _instance = new FireStoreService();
        return _instance;
    }
    public void UploadImage(List<Uri> files, OnSuccessListener<List<String>> successListener, OnFailureListener failureListener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://datingapp-56f26.appspot.com");
        List<String> urls = new ArrayList<>();
        int fileCount = files.size();
        for ( Uri file:files) {
            StorageReference imageRef = storageRef.child("images/" + file.getLastPathSegment());
            UploadTask uploadTask = imageRef.putFile(file);
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // Update progress indicator
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    Log.d(TAG, "Upload is " + progress + "% done");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        urls.add(uri.toString());
                        if (urls.size() == fileCount) {
                            successListener.onSuccess(urls);
                        }
                    });
                }
            }).addOnFailureListener(failureListener);

        }

    }


}
