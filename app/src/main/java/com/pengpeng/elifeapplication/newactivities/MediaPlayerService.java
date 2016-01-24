package com.pengpeng.elifeapplication.newactivities;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.pengpeng.elifeapplication.R;

public class MediaPlayerService extends Service implements MediaPlayer.OnPreparedListener {
    public static final int NOTIFICATION_ID = 1;


    public MediaPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String songName = intent.getStringExtra("songName");
        Log.i("songName ", songName);
        Intent openIntent = new Intent(getApplicationContext(), NetworkAudioPlayerActivity.class);
        openIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//上一个被打开的活动会被终结掉，实现没有两个相同的活动被同时打开。
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, openIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification();
        notification.tickerText = "This is the tickerText.";
        notification.icon = R.drawable.abc_ic_voice_search;
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.setLatestEventInfo(getApplicationContext(), "PengPlayer", "Now Playing: " + songName, pendingIntent);
        startForeground(NOTIFICATION_ID, notification);//开始前台服务，同时将notification和id绑定
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }
}
