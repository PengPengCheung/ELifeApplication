package com.pengpeng.elifeapplication;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MediaPlayerActivity extends ActionBarActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private static final String TAG = "MediaPlayer";
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Cursor cursor;
    private Button playButton;
    private Button preButton;
    private Button nextButton;
    private Button loopButton;
    private static final String PLAY = "Play";
    private static final String PAUSE = "Pause";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        initView();
        cursor = getCursor();
        setListener();
    }

    private long getCurrentAudioId(Cursor cursor) {
        int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        long thisId = cursor.getLong(idColumn);
        String thisTitle = cursor.getString(titleColumn);
        Log.i(TAG + " thisId", String.valueOf(thisId));
        Log.i(TAG + " thisTitle", thisTitle);
        return thisId;
    }

    private Cursor getCursor() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Log.i(TAG + " EXTCON_URI", uri.toString());
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            Log.i(TAG, "--->Query failed!");
            return null;
        } else if (!cursor.moveToFirst()) {
            Log.i(TAG, "--->No Media!");
            cursor.close();
            return null;
        } else {
            return cursor;
        }
    }

    private void setListener() {
        playButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        preButton.setOnClickListener(this);
        loopButton.setOnClickListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
    }

    private void initView() {
        playButton = (Button) findViewById(R.id.play_btn);
        nextButton = (Button) findViewById(R.id.next_btn);
        preButton = (Button) findViewById(R.id.previous_btn);
        loopButton = (Button) findViewById(R.id.loop_btn);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.release();
        Log.i(TAG + " onStop", mediaPlayer.toString());
        mediaPlayer = null;
        cursor.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_btn:
                play(cursor);
                break;
            case R.id.next_btn:
                playNext(cursor);
                break;
            case R.id.previous_btn:
                playPrevious(cursor);
                break;
            case R.id.loop_btn:
                playLoop();
                break;
        }
    }

    //只能设置单曲循环
    private void playLoop() {//只对某一次的音频设置循环而不能对所有音频设置循环，而且设置了一次之后，播放其他音频，这个音频再回听的时候就不是循环的了
        if (!mediaPlayer.isLooping()) {
            mediaPlayer.setLooping(true);
        }
    }

    private void playPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playButton.setText(PLAY);
        } else {
            Toast.makeText(MediaPlayerActivity.this, "The mediaPlayer is not playing!", Toast.LENGTH_SHORT).show();
        }
    }

    private void playPrevious(Cursor cursor) {
        if (cursor == null) {
            Toast.makeText(MediaPlayerActivity.this, "The cursor is null", Toast.LENGTH_SHORT).show();
        } else if (cursor.isFirst()) {
            Toast.makeText(MediaPlayerActivity.this, "This is the first audio.", Toast.LENGTH_SHORT).show();
        } else {
            cursor.moveToPrevious();
            playNewAudio(cursor);
        }
    }

    private void playNext(Cursor cursor) {
        if (cursor == null) {
            Toast.makeText(MediaPlayerActivity.this, "The cursor is null", Toast.LENGTH_SHORT).show();
        } else if (cursor.isLast()) {
            Toast.makeText(MediaPlayerActivity.this, "This is the last audio.", Toast.LENGTH_SHORT).show();
        } else {
            cursor.moveToNext();
            playNewAudio(cursor);
        }
    }

    private void playNewAudio(Cursor cursor) {
        long thisId = getCurrentAudioId(cursor);
        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, thisId);
        Log.i(TAG + " contentUri", contentUri.toString());
        try {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(getApplicationContext(), contentUri);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
                mediaPlayer.start();
                playButton.setText(PAUSE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void play(Cursor cursor) {
//                String urlString = "http://music.baidutt.com/up/kwcackwa/cmypus.mp3";
//                mediaPlayer.setDataSource(urlString);
        //需要判断datasource是否存在
        if(!mediaPlayer.isPlaying()){
            int totalDuration = mediaPlayer.getDuration();
            if (totalDuration != -1) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                if (currentPosition > 0 && currentPosition < totalDuration) {
                    mediaPlayer.start();
                    playButton.setText(PAUSE);
                } else {
                    playNewAudio(cursor);//initialized and prepared and start
                }
            }
        }else{
            playPause();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }
}
