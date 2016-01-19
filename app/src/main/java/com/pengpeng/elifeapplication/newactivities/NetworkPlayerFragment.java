package com.pengpeng.elifeapplication.newactivities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pengpeng.elifeapplication.R;
import com.pengpeng.elifeapplication.oldactivities.MediaPlayerService;
import com.pengpeng.elifeapplication.oldactivities.NetworkAudioPlayer;
import com.pengpeng.elifeapplication.utils.Tools;

/**
 * Created by pengpeng on 16-1-15.
 */
public class NetworkPlayerFragment extends Fragment implements View.OnClickListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private View view;
    //    private TextView songWord;
    private ImageButton playButton;
    private ImageButton preButton;
    private ImageButton nextButton;
    private ImageButton loopButton;
    private SeekBar seekBar;
    private TextView startTextView;
    private TextView endTextView;
    private NetworkAudioPlayer networkAudioPlayer;
    private Handler handler;
    private Intent intent;
    private Bundle bundle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        if (networkAudioPlayer != null && networkAudioPlayer.isPlaying()) {
                            endTextView.setText(Tools.getTimeText(networkAudioPlayer.getDuration()));
                            int totalTime = networkAudioPlayer.getDuration();
                            int currentTime = networkAudioPlayer.getCurrentPosition();
                            int seekBarMax = seekBar.getMax();
                            Log.e("Progress: ", totalTime + " " + currentTime + " " + seekBarMax);
                            if (totalTime > 0 && currentTime > 0 && seekBarMax > 0) {
                                Log.i("Progress: ", String.valueOf(seekBar.getProgress()));
                                startTextView.setText(Tools.getTimeText(currentTime));
                                seekBar.setProgress((int) (seekBarMax * (float) currentTime / totalTime));
                            }
                        }
                        break;
                }
            }
        };
        setPlayer(((NetworkAudioPlayerActivity) getActivity()).getNetworkAudioPlayer());

    }

    public void setPlayer(NetworkAudioPlayer player) {
        this.networkAudioPlayer = player;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_networkplayer, null);
            init();
            setListener();
            UpdateSeekBarThread thread = new UpdateSeekBarThread();
            thread.start();
        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onDestroy() {
        networkAudioPlayer.release();
        networkAudioPlayer = null;
        if (intent != null) {
            getActivity().stopService(intent);
        }
        super.onDestroy();
    }

    public void init() {
        playButton = (ImageButton) view.findViewById(R.id.playButton);
        preButton = (ImageButton) view.findViewById(R.id.preButton);
        nextButton = (ImageButton) view.findViewById(R.id.nextButton);
        // back = (ImageButton) view.findViewById(R.id.back);
//        txt = (ImageButton) view.findViewById(R.id.txt_change);
//        songWord = (TextView) view.findViewById(R.id.songWord);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        startTextView = (TextView) view.findViewById(R.id.startTextView);
        endTextView = (TextView) view.findViewById(R.id.endTextView);
    }

    public void setListener() {
        playButton.setOnClickListener(this);
        preButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
//        txt.setOnClickListener(this);
        networkAudioPlayer.setOnPreparedListener(this);
        networkAudioPlayer.setOnCompletionListener(this);
        seekBar.setOnSeekBarChangeListener(new ProgressBarListener());

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playButton:
                if (networkAudioPlayer.isPlaying()) {
                    networkAudioPlayer.playPause();
                    playButton.setBackgroundResource(R.drawable.play);

                } else {
                    networkAudioPlayer.play();
                    playButton.setBackgroundResource(R.drawable.pause);
                }
                break;
            case R.id.preButton:
                networkAudioPlayer.playPrevious();
                playButton.setBackgroundResource(R.drawable.pause);
                break;
            case R.id.nextButton:
                networkAudioPlayer.playNext();
                playButton.setBackgroundResource(R.drawable.pause);
                break;
        }
        intent = new Intent(getActivity(), MediaPlayerService.class);
        intent.putExtra("songName", "This is networkPlayer's song.");
        getActivity().startService(intent);
    }

    public void update(Bundle b) {
        if (b != null) {
            this.bundle = b;
            Toast.makeText(getActivity(), String.valueOf(bundle.getInt("part")), Toast.LENGTH_SHORT).show();

            if (bundle.getInt("part") == 2) {
                int pOneEnd = ((NetworkAudioPlayerActivity) getActivity()).getAudio().getAudioPartEndTime().get(0);
                int pThirdEnd = ((NetworkAudioPlayerActivity) getActivity()).getAudio().getAudioPartEndTime().get(2);
                //(int) (seekBar.getMax() * (float) player.getCurrentPosition() / player.getDuration())
                seekBar.setProgress((int) (seekBar.getMax() * (float) pOneEnd / pThirdEnd));
                endTextView.setText(Tools.getTimeText(pThirdEnd));
            }

        }

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        playButton.setBackgroundResource(R.drawable.play);
        networkAudioPlayer.play();
        playButton.setBackgroundResource(R.drawable.pause);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        networkAudioPlayer.seekTo(networkAudioPlayer.getDuration()/3);
        networkAudioPlayer.start();
        networkAudioPlayer.setPaused(false);
//        networkAudioPlayer.setPrepared(true);
    }

    private class ProgressBarListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                networkAudioPlayer.seekTo(Tools.progressToPosition(seekBar, networkAudioPlayer));
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
