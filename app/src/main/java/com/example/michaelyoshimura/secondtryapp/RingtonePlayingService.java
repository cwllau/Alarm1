package com.example.michaelyoshimura.secondtryapp;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Michael Yoshimura on 11/11/2016.
 */
public class RingtonePlayingService extends Service {

    MediaPlayer media_player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        media_player = MediaPlayer.create(this, R.raw.loud);
        media_player.start();

        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {

        // Tell the user we stopped.
        Toast.makeText(this, "On Destroy", Toast.LENGTH_SHORT).show();
    }



}
