package com.pengpeng.elifeapplication;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MediaPlayerActivity extends ActionBarActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private static final String TAG = "MediaPlayer";
    private AudioPlayer audioPlayer = new AudioPlayer(MediaPlayerActivity.this);
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
        audioPlayer.release();
        Log.i(TAG + " onStop", audioPlayer.toString());
        cursor.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_btn:
                audioPlayer.play(cursor);
                if(PLAY.equals(playButton.getText())){
                    playButton.setText(PAUSE);
                }else{
                    playButton.setText(PLAY);
                }
                break;
            case R.id.next_btn:
                audioPlayer.playNext(cursor);
                break;
            case R.id.previous_btn:
                audioPlayer.playPrevious(cursor);
                break;
            case R.id.loop_btn:
                audioPlayer.playLoop();
                break;
        }
    }



    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }
}
