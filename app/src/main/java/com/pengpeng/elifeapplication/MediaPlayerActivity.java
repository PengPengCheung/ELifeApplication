package com.pengpeng.elifeapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;


public class MediaPlayerActivity extends ActionBarActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {
    private static final String TAG = "MediaPlayer";
    private AudioPlayer audioPlayer = new AudioPlayer(MediaPlayerActivity.this);
    private Cursor cursor;
    private Button playButton;
    private Button preButton;
    private Button nextButton;
    private Button loopButton;
    private SeekBar seekBar;
    private static final String PLAY = "Play";
    private static final String PAUSE = "Pause";
    private Intent intent;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (audioPlayer != null && audioPlayer.isPrepared()) {
                        int totalTime = audioPlayer.getDuration();
                        int currentTime = audioPlayer.getCurrentPosition();
                        int seekBarMax = seekBar.getMax();
                        Log.e("Progress: ", totalTime + " " + currentTime + " " + seekBarMax);
                        if (totalTime > 0 && currentTime > 0 && seekBarMax > 0) {
                            Log.i("Progress: ", String.valueOf(seekBar.getProgress()));
                            seekBar.setProgress((int) (seekBarMax * (float) currentTime / totalTime));
                        }
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mediaplayer_activity);
        initView();
        cursor = getCursor();
        setListener();
        UpdateSeekBarThread thread = new UpdateSeekBarThread();
        thread.start();

    }

    private Cursor getCursor() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Log.i(TAG + " EXT_CON_URI", uri.toString());
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
        audioPlayer.getMediaPlayer().setOnCompletionListener(this);
        seekBar.setOnSeekBarChangeListener(new ProgressBarListener());
    }

    private void initView() {
        playButton = (Button) findViewById(R.id.play_btn);
        nextButton = (Button) findViewById(R.id.next_btn);
        preButton = (Button) findViewById(R.id.previous_btn);
        loopButton = (Button) findViewById(R.id.loop_btn);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
    }

    @Override
    protected void onDestroy() {
        audioPlayer.release();
        Log.i(TAG + " onStop", audioPlayer.toString());
        audioPlayer = null;
        cursor.close();
        stopService(intent);
        super.onDestroy();
    }

    public class UpdateSeekBarThread extends Thread {
        @Override
        public void run() {
            while (audioPlayer != null) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_btn:
                if (PLAY.equals(playButton.getText())) {
                    audioPlayer.play(cursor);
                    playButton.setText(PAUSE);
                } else if (PAUSE.equals(playButton.getText())) {
                    audioPlayer.playPause();
                    playButton.setText(PLAY);
                }
                break;
            case R.id.next_btn:
                audioPlayer.playNext(cursor);
                if (PLAY.equals(playButton.getText())) {
                    playButton.setText(PAUSE);
                }
                break;
            case R.id.previous_btn:
                audioPlayer.playPrevious(cursor);
                if (PLAY.equals(playButton.getText())) {
                    playButton.setText(PAUSE);
                }
                break;
            case R.id.loop_btn:
                audioPlayer.playLoop();
                break;
        }
        intent = new Intent(MediaPlayerActivity.this, MediaPlayerService.class);
        intent.putExtra("songName", audioPlayer.getCurrentAudioTitle(cursor));
        startService(intent);
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        playButton.setText(PLAY);
    }


    private class ProgressBarListener implements SeekBar.OnSeekBarChangeListener {

        /*
        * 这里的progress参数指的是相对于seekBar来说的progress，而不是音频的progress
        * seekBar默认是的最大值是100,如果改成音频的大小，那么这里的progress参数就是音频的progress
        * */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                audioPlayer.seekTo((int) (audioPlayer.getDuration() * (float) progress / seekBar.getMax()));
                seekBar.setProgress(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
