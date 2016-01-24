package com.pengpeng.elifeapplication.newactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pengpeng.elifeapplication.R;
import com.pengpeng.elifecore.CoreAction;
import com.pengpeng.elifecore.CoreActionImpl;
import com.pengpeng.elifecore.ResponseCallbackListener;
import com.pengpeng.elifemodel.Audio;
import com.pengpeng.elifemodel.UserAudioBehavior;

import java.util.ArrayList;
import java.util.List;

//用户点进这个activity就知道哪段音频了而不是点击播放才知道
public class NetworkAudioPlayerActivity extends ActionBarActivity implements View.OnClickListener, ClassificationFragment.onPartDividedListener, NetworkPlayerFragment.onNotifyListener {

    private String TAG = "MainActivity";
    private ViewPager viewPager;
    private NetworkAudioPlayer networkAudioPlayer;
    private CoreAction action;
    private UserAudioBehavior uaUserAudioBehavior;
    private Audio audio;
    private List<Fragment> fragmentList;
    private TextView title;
    private ImageButton back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_network_audio_player);
        init();
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String audioId = intent.getStringExtra("audioId");
        String audioTitle = intent.getStringExtra("audioTitle");
        if(audioTitle!=null){
            title.setText(audioTitle);
        }
        action = new CoreActionImpl();
        uaUserAudioBehavior = new UserAudioBehavior();
        Log.i(TAG, userId);
        Log.i(TAG, audioId);
        if (userId != null && audioId != null) {
            uaUserAudioBehavior.setUserId(userId);
            uaUserAudioBehavior.setAudioId(audioId);
            action.getAudio(uaUserAudioBehavior, new ResponseCallbackListener<Audio>() {
                @Override
                public void onSuccess(Audio data) {
                    Log.i(TAG, "--onSuccess");
                    audio = data;
                    networkAudioPlayer.setSourceUrl(data.getAudioUrl());
                    ((NetworkPlayerFragment)fragmentList.get(2)).update(audio);
                    Toast.makeText(NetworkAudioPlayerActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String error_code, String reason) {
                    Log.i(TAG, "--onFailure");
                    Toast.makeText(NetworkAudioPlayerActivity.this, error_code + " " + reason, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(NetworkAudioPlayerActivity.this, "userId and audioId are null.", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onPause() {

//        networkAudioPlayer.release();
//        networkAudioPlayer = null;
//        action = null;
//        uaUserAudioBehavior = null;
//        viewPager = null;
////        ((NetworkPlayerFragment)fragmentList.get(2)).getPlayer().release();
//        fragmentList.get(2).onDestroy();
//        fragmentList.get(0).onDestroy();
//        fragmentList.get(1).onDestroy();
//        fragmentList.clear();
//        fragmentList = null;
        super.onPause();
        Log.i(TAG, "onPause");
    }

    private void init() {
        // TODO Auto-generated method stub
        back = (ImageButton) this.findViewById(R.id.back);
        title = (TextView)this.findViewById(R.id.title);

        back.setOnClickListener(this);



        viewPager = (ViewPager) this.findViewById(R.id.viewPager);


        Fragment classificationFragment = new ClassificationFragment();
        Fragment exerciseFragment = new ExerciseFragment();
        Fragment networkPlayerFragment = new NetworkPlayerFragment();

        networkAudioPlayer = new NetworkAudioPlayer(this);

        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(classificationFragment);
//
        fragmentList.add(exerciseFragment);
        fragmentList.add(networkPlayerFragment);

        FragmentPagerAdapter adapter = new ListFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
//        viewPager.setCurrentItem(1);
//        viewPager.setOnPageChangeListener(new onFragmentPageChangedListener(fragmentList, viewPager.getCurrentItem()));
    }


    public ViewPager getViewPager() {
        return this.viewPager;
    }

    public NetworkAudioPlayer getNetworkAudioPlayer() {
        return networkAudioPlayer;
    }

    public Audio getAudio() {
        return this.audio;
    }


    @Override
    public void onClick(View view) {
        Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(NetworkAudioPlayerActivity.this, AudioListActivity.class);
//        Log.i(TAG, "onclick");
//        startActivity(intent);
//        Log.i(TAG, "startActivity");
//        NetworkAudioPlayerActivity.this.finish();
//        Log.i(TAG, "finishActivity");
    }

    @Override
    public void onPartDivided(Bundle bundle) {
        ((ExerciseFragment) fragmentList.get(1)).updateAudio(bundle);
    }

    @Override
    public void onNotify(String flag) {
        if(flag.equals("NetworkPlayerFragment")){
            networkAudioPlayer.playPause();
        }else if(flag.equals("ExerciseFragment")){
            ((NetworkPlayerFragment) fragmentList.get(2)).receiveNotification();
        }
    }
}
