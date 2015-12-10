package com.pengpeng.elifeapplication;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.pengpeng.elifeapplication.utils.Tools;

import java.io.IOException;
import java.util.Random;

/**
 * Created by pengpeng on 15-12-8.
 */
public class NetworkAudioPlayer extends MediaPlayer implements IAudioPlayer{

    private Context context;
    private boolean paused = false;
    String urlString1 = "http://sc1.111ttt.com/2015/1/12/08/105081507558.mp3";
    String urlString2 = "http://sc1.111ttt.com/2015/1/12/08/105082233058.mp3";
//    String urlString3 = "http://music.baidutt.com/up/kwcackwp/kuuwa.mp3";
    String urlString3 = "http://sc1.111ttt.com/2015/1/12/09/105090933364.mp3";
    public NetworkAudioPlayer(Context context){
        this.context = context;
    }

    private String getSource(){
        String[] strs = new String[3];
        strs[0] = urlString1;
        strs[1] = urlString2;
        strs[2] = urlString3;
        Random random = new Random();
        return strs[random.nextInt(3)];
    }


    public void setPaused(boolean paused) {
        this.paused = paused;
    }


    @Override
    public boolean isPaused() {
        return this.paused;
    }

    @Override
    public void playPause() {
        if(isPlaying()){
            pause();
            paused = true;
        }
    }

    @Override
    public void playPrevious() {
        if(Tools.isNetworkAvailable(context)){
            playPrepared();
        }
    }

    @Override
    public void playNext() {
        if(Tools.isNetworkAvailable(context)){
            playPrepared();
        }
    }

    @Override
    public void playPrepared() {
        if(Tools.isNetworkAvailable(context)){
            try {
                reset();
                setDataSource(getSource());
                setAudioStreamType(AudioManager.STREAM_MUSIC);
                prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void play() {
        if (isPaused()) {
            start();
            paused = false;
        } else {
            playPrepared();
        }
    }
}
