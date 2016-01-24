package com.pengpeng.elifeapplication.newactivities;

import android.app.Activity;
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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.pengpeng.elifeapplication.R;
import com.pengpeng.elifeapplication.utils.Tools;
import com.pengpeng.elifemodel.Audio;

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
    private Audio audio;
    private int part = 0;
    private NetworkImageView mNetworkImageView;
    private String imageUrl;
    private onNotifyListener listener;
    private static final String flag = "NetworkPlayerFragment";
    private RelativeLayout mRelativeLayout;
//    thread.start();

    public interface onNotifyListener {
        void onNotify(String flag);
    }

    @Override
    public void onAttach(Activity activity) {
        listener = (onNotifyListener) activity;
        super.onAttach(activity);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if (audio != null) {
                    switch (msg.what) {
                        case 0:
                            updateProgress();
                            break;
//                        case 1:
//                            updateProgress(0, audio.getAudioPartEndTime().get(0));
//                            break;
//                        case 2:
//                            updateProgress(audio.getAudioPartEndTime().get(0), audio.getAudioPartEndTime().get(1));
//                            break;
//                        case 3:
//                            updateProgress(audio.getAudioPartEndTime().get(1), audio.getAudioPartEndTime().get(2));
//                            break;
                    }
                }
            }
        };


    }

    private void updateProgress() {
        if (audio != null) {
            if (networkAudioPlayer != null && networkAudioPlayer.isPlaying()) {
                int totalTime = audio.getAudioPartEndTime().get(2);
                int currentTime = networkAudioPlayer.getCurrentPosition();
                int seekBarMax = seekBar.getMax();
                Log.e("Progress: ", totalTime + " " + currentTime + " " + seekBarMax);
//                if (currentTime < endTime || currentTime >= startTime) {
                if (totalTime > 0 && currentTime > 0 && seekBarMax > 0) {
                    Log.i("Progress: ", String.valueOf(seekBar.getProgress()));
                    startTextView.setText(Tools.getTimeText(currentTime));
                    seekBar.setProgress((int) (seekBarMax * (float) currentTime / totalTime));
                }
//                }else
//                 {
//                    networkAudioPlayer.playPause();
//                    seekBar.setProgress((int) (seekBarMax * (float) startTime / audio.getAudioPartEndTime().get(2)));
//                    networkAudioPlayer.seekTo(startTime);
//                    playButton.setBackgroundResource(R.drawable.play);
//                    startTextView.setText(Tools.getTimeText(startTime));
//                }
            }
        }
    }

    public NetworkAudioPlayer getPlayer() {
        return this.networkAudioPlayer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_networkplayer, null);
//            setPlayer(((NetworkAudioPlayerActivity) getActivity()).getNetworkAudioPlayer());
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
        if (networkAudioPlayer != null) {
            networkAudioPlayer.release();
            networkAudioPlayer = null;
        }

//        if (intent != null) {
//            getActivity().stopService(intent);
//        }
        super.onDestroy();
    }

    public void init() {
        playButton = (ImageButton) view.findViewById(R.id.playButton);
        preButton = (ImageButton) view.findViewById(R.id.preButton);
        nextButton = (ImageButton) view.findViewById(R.id.nextButton);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        startTextView = (TextView) view.findViewById(R.id.startTextView);
        endTextView = (TextView) view.findViewById(R.id.endTextView);
        networkAudioPlayer = new NetworkAudioPlayer(getActivity());//如果activityv被finish了，这里面的要用到context的方法就无法继续调用，因此这里要注意！！！为什么无法按back返回上一个activty的原因
        networkAudioPlayer.setSourceUrl(audio.getAudioUrl());
//        mNetworkImageView = (NetworkImageView)view.findViewById(R.id.network_player_imageView);
//        mNetworkImageView.setDefaultImageResId(R.drawable.start);
//        mNetworkImageView.setErrorImageResId(R.drawable.error);
//        mRelativeLayout = (RelativeLayout)view.findViewById(R.id.network_player_layout);
//
//        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
//        ImageLoader mImageLoader = new ImageLoader(mQueue, new BitmapCache());
//        mNetworkImageView.setImageUrl(audio.getAudioImageUrl(), mImageLoader);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            mRelativeLayout.setBackground(mNetworkImageView.getDrawable());
//        }
    }

    public void update(Audio audio) {
        Log.i("b NetworkPlayerFragment", audio.toString());
        this.audio = audio;
        Log.i("a NetworkPlayerFragment", audio.toString());
//
    }

    public void setListener() {
        playButton.setOnClickListener(this);
        preButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
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

    public void receiveNotification() {
        networkAudioPlayer.playPause();
        playButton.setBackgroundResource(R.drawable.play);
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
                    listener.onNotify(flag);
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
//        intent = new Intent(getActivity(), MediaPlayerService.class);
//        intent.putExtra("songName", audio.getAudioTitle());
//        getActivity().startService(intent);
    }

//    private void ModeStart(int part) {
//        switch (part) {
//            case 0:
//                networkAudioPlayer.seekTo(0);
//                break;
//            case 1:
//                networkAudioPlayer.seekTo(0);
//                break;
//            case 2:
//                networkAudioPlayer.seekTo(audio.getAudioPartEndTime().get(0));
//                break;
//            case 3:
//                networkAudioPlayer.seekTo(audio.getAudioPartEndTime().get(1));
//                break;
//        }
//
//
//    }

//    public void update(Bundle b) {
//        if (b != null) {
//            this.bundle = b;
//            Toast.makeText(getActivity(), String.valueOf(bundle.getInt("part")), Toast.LENGTH_SHORT).show();
//            part = bundle.getInt("part");
//
//            int start = 0;
//            audio = ((NetworkAudioPlayerActivity) getActivity()).getAudio();
//            int end = audio.getAudioPartEndTime().get(2);
//            switch (part) {
//                case 1:
//                    start = 0;
//                    break;
//                case 2:
//                    start = ((NetworkAudioPlayerActivity) getActivity()).getAudio().getAudioPartEndTime().get(0);
//                    break;
//                case 3:
//                    start = ((NetworkAudioPlayerActivity) getActivity()).getAudio().getAudioPartEndTime().get(1);
//                    break;
//                //(int) (seekBar.getMax() * (float) player.getCurrentPosition() / player.getDuration())
//            }
//            if (networkAudioPlayer != null) {
//
//                if (!networkAudioPlayer.isPlaying()) {//处于不播放状态
//                    if (networkAudioPlayer.isPaused()) {//如果是因为暂停而不播放
//                        networkAudioPlayer.seekTo(start);
//                        seekBar.setProgress((int) (seekBar.getMax() * (float) start / end));
//                        startTextView.setText(Tools.getTimeText(start));
//                        endTextView.setText(Tools.getTimeText(end));
//                    } else {//因为没准备好而不播放
//                        seekBar.setProgress((int) (seekBar.getMax() * (float) start / end));
//                        startTextView.setText(Tools.getTimeText(start));
//                        endTextView.setText(Tools.getTimeText(end));
//                    }
//
//                } else {
//                    networkAudioPlayer.playPause();
//                    networkAudioPlayer.seekTo(start);
//                    seekBar.setProgress((int) (seekBar.getMax() * (float) start / end));
//                    startTextView.setText(Tools.getTimeText(start));
//                    endTextView.setText(Tools.getTimeText(end));
//                    playButton.setBackgroundResource(R.drawable.play);
//
//                }
//            }
//
//
//        }
//    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        playButton.setBackgroundResource(R.drawable.play);
        networkAudioPlayer.play();
        playButton.setBackgroundResource(R.drawable.pause);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        audio = ((NetworkAudioPlayerActivity) getActivity()).getAudio();
//        ModeStart(part);
        endTextView.setText(Tools.getTimeText(audio.getAudioPartEndTime().get(2)));
        networkAudioPlayer.start();
        networkAudioPlayer.setPaused(false);


//        networkAudioPlayer.setPrepared(true);
    }

    private class ProgressBarListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                networkAudioPlayer.seekTo((int) (audio.getAudioPartEndTime().get(2) * (float) seekBar.getProgress() / seekBar.getMax()));
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
