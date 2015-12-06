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


    public AudioPlayer(Context context) {
        this.context = context;
    }

    public long getCurrentAudioId(Cursor cursor) {
        int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        long thisId = cursor.getLong(idColumn);
        String thisTitle = cursor.getString(titleColumn);
        Log.i(TAG + " thisId", String.valueOf(thisId));
        Log.i(TAG + " thisTitle", thisTitle);
        return thisId;
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
            playNewAudio(cursor);
        }
    }

    public void playNext(Cursor cursor) {
        if (cursor == null) {
            Toast.makeText(context, "The cursor is null", Toast.LENGTH_SHORT).show();
        } else if (cursor.isLast()) {
            Toast.makeText(context, "This is the last audio.", Toast.LENGTH_SHORT).show();
        } else {
            cursor.moveToNext();
            playNewAudio(cursor);
        }
    }

    public void playNewAudio(Cursor cursor) {
        long thisId = getCurrentAudioId(cursor);
        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, thisId);
        Log.i(TAG + " contentUri", contentUri.toString());
        try {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(context.getApplicationContext(), contentUri);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void play(Cursor cursor) {
//                String urlString = "http://music.baidutt.com/up/kwcackwa/cmypus.mp3";
//                mediaPlayer.setDataSource(urlString);
        //需要判断datasource是否存在
        if (!mediaPlayer.isPlaying()) {
            int totalDuration = mediaPlayer.getDuration();
            if (totalDuration != -1) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                if (currentPosition > 0 && currentPosition < totalDuration) {
                    mediaPlayer.start();
                } else {
                    playNewAudio(cursor);//initialized and prepared and start
                }
            }
        } else {
            playPause();
        }
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
