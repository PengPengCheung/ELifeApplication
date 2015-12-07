package com.pengpeng.elifeapplication;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by pengpeng on 15-12-6.
 */
public class AudioPlayer {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Context context;
    private static final String TAG = "AudioPlayer";
    private boolean prepared = false;
    private boolean paused = false;


    public AudioPlayer(Context context) {
        this.context = context;
    }

    public boolean isPrepared(){
        return this.prepared;
    }

    public boolean isPaused(){
        return this.paused;
    }

    public int getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return -1;
    }

    public int getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }

        return -1;
    }

    public void seekTo(int progress) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(progress);
        }

    }

    public long getCurrentAudioId(Cursor cursor) {
        int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        long thisId = cursor.getLong(idColumn);
        Log.i(TAG + " thisId", String.valueOf(thisId));
        return thisId;
    }

    public String getCurrentAudioTitle(Cursor cursor) {
        int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        String thisTitle = cursor.getString(titleColumn);
        Log.i(TAG + " thisTitle", thisTitle);
        return thisTitle;
    }

    public Uri getCurrentAudioUri(Cursor cursor){
        long thisId = getCurrentAudioId(cursor);
        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, thisId);
        Log.i(TAG + " contentUri", contentUri.toString());
        return contentUri;
    }

    //只能设置单曲循环
    public void playLoop() {//只对某一次的音频设置循环而不能对所有音频设置循环，而且设置了一次之后，播放其他音频，这个音频再回听的时候就不是循环的了
        if (!mediaPlayer.isLooping()) {
            mediaPlayer.setLooping(true);
        }
    }

    public void playPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            this.paused = true;
        } else {
            Toast.makeText(context, "The mediaPlayer is not playing!", Toast.LENGTH_SHORT).show();
        }
    }

    public void playPrevious(Cursor cursor) {
        if (cursor == null) {
            Toast.makeText(context, "The cursor is null", Toast.LENGTH_SHORT).show();
        } else if (cursor.isFirst()) {
            Toast.makeText(context, "This is the first audio.", Toast.LENGTH_SHORT).show();
        } else {
            cursor.moveToPrevious();
            playPrepared(cursor);
            start();
        }
    }

    public void playNext(Cursor cursor) {
        if (cursor == null) {
            Toast.makeText(context, "The cursor is null", Toast.LENGTH_SHORT).show();
        } else if (cursor.isLast()) {
            Toast.makeText(context, "This is the last audio.", Toast.LENGTH_SHORT).show();
        } else {
            cursor.moveToNext();
            playPrepared(cursor);
            start();
        }
    }

    public void playPrepared(Cursor cursor) {
        Uri contentUri = getCurrentAudioUri(cursor);
        try {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(context.getApplicationContext(), contentUri);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
                prepared = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void play(Cursor cursor) {
//                String urlString = "http://music.baidutt.com/up/kwcackwa/cmypus.mp3";
//                mediaPlayer.setDataSource(urlString);
        //需要判断datasource是否存在
//        int totalDuration = mediaPlayer.getDuration();
//        if (totalDuration != -1) {
//            int currentPosition = mediaPlayer.getCurrentPosition();
//            if (currentPosition > 0 && currentPosition < totalDuration) {
//                mediaPlayer.start();
//            } else {
//                playPrepared(cursor);
//                start();//initialized and prepared and start
//            }
//        }
        if(isPaused()){
            start();
            paused = false;
        }else{
            playPrepared(cursor);
            start();
        }
    }
    

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}
