package com.pengpeng.elifeapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pengpeng.elifeapplication.utils.Tools;


public class NetworkPlayerActivity extends ActionBarActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private static final String TAG = "NetworkPlayer";
    private NetworkAudioPlayer networkAudioPlayer = new NetworkAudioPlayer(NetworkPlayerActivity.this);
    private Button playButton;
    private Button preButton;
    private Button nextButton;
    private Button loopButton;
    private SeekBar seekBar;
    private TextView startTimeText;
    private TextView endTimeText;
    private static final String initString = "0:00";
    private static final String PLAY = "Play";
    private static final String PAUSE = "Pause";
    private Intent intent;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (networkAudioPlayer != null && networkAudioPlayer.isPlaying()) {
                        endTimeText.setText(Tools.getTimeText(networkAudioPlayer.getDuration()));
                        int totalTime = networkAudioPlayer.getDuration();
                        int currentTime = networkAudioPlayer.getCurrentPosition();
                        int seekBarMax = seekBar.getMax();
                        Log.e("Progress: ", totalTime + " " + currentTime + " " + seekBarMax);
                        if (totalTime > 0 && currentTime > 0 && seekBarMax > 0) {
                            Log.i("Progress: ", String.valueOf(seekBar.getProgress()));
                            startTimeText.setText(Tools.getTimeText(currentTime));
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
        setContentView(R.layout.network_audio_layout);
        initView();
        setListener();

        UpdateSeekBarThread thread = new UpdateSeekBarThread();
        thread.start();
    }

    @Override
    protected void onDestroy() {
        networkAudioPlayer.release();
        Log.i(TAG + " onStop", networkAudioPlayer.toString());
        networkAudioPlayer = null;
        if (intent != null) {
            stopService(intent);
        }
        super.onDestroy();
    }

    private void initView() {
        playButton = (Button) findViewById(R.id.play_btn);
        nextButton = (Button) findViewById(R.id.next_btn);
        preButton = (Button) findViewById(R.id.previous_btn);
        loopButton = (Button) findViewById(R.id.loop_btn);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        startTimeText = (TextView) findViewById(R.id.startTime);
        endTimeText = (TextView) findViewById(R.id.endTime);
    }

    private void setListener() {
        playButton.setOnClickListener(this);
        networkAudioPlayer.setOnPreparedListener(this);
        nextButton.setOnClickListener(this);
        preButton.setOnClickListener(this);
        loopButton.setOnClickListener(this);
        networkAudioPlayer.setOnCompletionListener(this);
        seekBar.setOnSeekBarChangeListener(new ProgressBarListener());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_btn:
                if (PLAY.equals(playButton.getText())) {
                    networkAudioPlayer.play();
                    playButton.setText(PAUSE);
                } else {
                    networkAudioPlayer.playPause();
                    playButton.setText(PLAY);
                }
                break;
            case R.id.previous_btn:
                networkAudioPlayer.playPrevious();
                playButton.setText(PAUSE);
                break;
            case R.id.next_btn:
                networkAudioPlayer.playNext();
                playButton.setText(PAUSE);
                break;
            case R.id.loop_btn:
                networkAudioPlayer.setLooping(true);
                break;
        }
        intent = new Intent(NetworkPlayerActivity.this, MediaPlayerService.class);
        intent.putExtra("songName", "This is networkPlayer's song.");
        startService(intent);
    }

    public class UpdateSeekBarThread extends Thread {
        @Override
        public void run() {
            while (networkAudioPlayer != null) {
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
    public void onPrepared(MediaPlayer mediaPlayer) {
        networkAudioPlayer.start();
        networkAudioPlayer.setPaused(false);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        playButton.setText(PLAY);
        networkAudioPlayer.play();
        playButton.setText(PAUSE);
        Log.i("Completion", networkAudioPlayer.isPaused() + " " + networkAudioPlayer.isPlaying());
    }

    private class ProgressBarListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                networkAudioPlayer.seekTo((int) (networkAudioPlayer.getDuration() * (float) progress / seekBar.getMax()));
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
