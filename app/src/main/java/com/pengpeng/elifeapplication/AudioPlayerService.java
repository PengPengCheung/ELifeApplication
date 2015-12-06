package com.pengpeng.elifeapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class AudioPlayerService extends Service implements MediaPlayer.OnPreparedListener{
    private MediaPlayer mediaPlayer = new MediaPlayer();

    public AudioPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }
}
