package com.example.dattingapp.common;

import android.app.ProgressDialog;
import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoaderInterceptor implements Interceptor {

    private ProgressDialog mProgressDialog;

    public LoaderInterceptor(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // Hiển thị loader
        mProgressDialog.show();

        Request request = chain.request();
        Response response = chain.proceed(request);
        // Ẩn loader
        mProgressDialog.dismiss();
        return response;
    }
}