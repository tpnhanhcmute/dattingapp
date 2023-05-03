package com.example.dattingapp.service;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dattingapp.R;

public class NotificationService extends Service {
    private NotificationManager mNotificationManager;
    private final String CHANNEL_ID = "default_channel";
    private final int NOTIFICATION_ID = 001;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
