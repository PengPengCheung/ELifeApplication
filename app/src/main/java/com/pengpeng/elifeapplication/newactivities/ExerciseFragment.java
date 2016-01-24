package com.pengpeng.elifeapplication.newactivities;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.pengpeng.elifeapplication.R;
import com.pengpeng.elifemodel.Audio;

/**
 * Created by pengpeng on 16-1-15.
 */
public class ExerciseFragment extends Fragment implements View.OnClickListener, MediaPlayer.OnPreparedListener {

    private String TAG = "ExerciseFragment";
    private View view;
    private ImageButton hornImgButton;
    private ImageView partImgView;
    private NetworkAudioPlayer player;
    private Audio audio;
    private TimePart mTimePart;
    private Handler handler;
    private ImageButton lastPartButton;
    private ImageButton nextPartButton;
    private static final String flag = "ExerciseFragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (player != null && player.isPlaying()) {
                    TimePart tp = (TimePart) msg.obj;
                    int currentTime = player.getCurrentPosition();
                    if (currentTime >= tp.end || currentTime < tp.start) {
                        player.playPause();
                        player.seekTo(tp.start);
                    }
                }

            }
        };
        player = ((NetworkAudioPlayerActivity) getActivity()).getNetworkAudioPlayer();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_exercise, null);
            init();
            setListener();
            Log.i(TAG, "view not null");
            ExerciseThread thread = new ExerciseThread();
            thread.start();
        }
        Log.i(TAG, player.toString());
        return view;
    }

    @Override
    public void onDestroy() {
        player.release();
        player = null;
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    private void setListener() {
        hornImgButton.setOnClickListener(this);
        lastPartButton.setOnClickListener(this);
        nextPartButton.setOnClickListener(this);
        player.setOnPreparedListener(this);
    }

    private void init() {
        hornImgButton = (ImageButton) view.findViewById(R.id.fragment_exercise_horn);
        partImgView = (ImageView) view.findViewById(R.id.fragment_exercise_part);
        lastPartButton = (ImageButton) view.findViewById(R.id.fragment_exercise_last);
        nextPartButton = (ImageButton) view.findViewById(R.id.fragment_exercise_next);

        mTimePart = new TimePart();
        mTimePart.start = 0;
        mTimePart.part = 1;

    }

    @Override
    public void onClick(View view) {
        Bundle b = new Bundle();
        switch (view.getId()) {
            case R.id.fragment_exercise_horn:
                if (player.isPlaying()) {
                    player.playPause();
                } else {
                    player.play();
                    ((NetworkAudioPlayerActivity)getActivity()).onNotify(flag);
                    Log.i(TAG, "thread");
                }
                break;
            case R.id.fragment_exercise_last:
                b.putInt("part", mTimePart.part - 1);
                updateAudio(b);
                break;
            case R.id.fragment_exercise_next:
                b.putInt("part", mTimePart.part + 1);
                updateAudio(b);
                break;
        }
    }

    public void updateAudio(Bundle bundle) {
        if (bundle != null) {
            Toast.makeText(getActivity(), String.valueOf(bundle.getInt("part")), Toast.LENGTH_SHORT).show();
            int part = bundle.getInt("part");

            audio = ((NetworkAudioPlayerActivity) getActivity()).getAudio();
            switch (part) {
                case 1:
                    mTimePart.start = 0;
                    mTimePart.end = audio.getAudioPartEndTime().get(0);
                    partImgView.setImageResource(R.drawable.part1imageview);
                    break;
                case 2:
                    mTimePart.start = audio.getAudioPartEndTime().get(0);
                    mTimePart.end = audio.getAudioPartEndTime().get(1);
                    partImgView.setImageResource(R.drawable.part2imageview);
                    break;
                case 3:
                    mTimePart.start = audio.getAudioPartEndTime().get(1);
                    mTimePart.end = audio.getAudioPartEndTime().get(2);
                    partImgView.setImageResource(R.drawable.part3imageview);
                    break;
                default:
                    Toast.makeText(getActivity(), "No More Parts!", Toast.LENGTH_SHORT).show();
                    //(int) (seekBar.getMax() * (float) player.getCurrentPosition() / player.getDuration())
            }
            if (part > 0 && part < 4) {
                mTimePart.part = part;
                if (player != null) {

                    if (!player.isPlaying()) {
                        if (player.isPaused()) {
                            player.seekTo(mTimePart.start);
                        } else {

                        }
                    } else {
                        player.playPause();
                        player.seekTo(mTimePart.start);

                    }
                }
            }
        }
    }

    //
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        audio = ((NetworkAudioPlayerActivity) getActivity()).getAudio();
        player.seekTo(mTimePart.start);
        if (mTimePart.part == 1) {
            mTimePart.end = audio.getAudioPartEndTime().get(0);
        }
        player.start();
        player.setPaused(false);
    }

    class TimePart {
        int start;
        int end;
        int part;
    }

    public class ExerciseThread extends Thread {
        @Override
        public void run() {
            while (player != null) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = handler.obtainMessage(0, mTimePart);
                handler.sendMessage(message);
            }

        }
    }
}
