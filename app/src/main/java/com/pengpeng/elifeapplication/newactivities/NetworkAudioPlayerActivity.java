package com.pengpeng.elifeapplication.newactivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pengpeng.elifeapplication.R;
import com.pengpeng.elifeapplication.oldactivities.NetworkAudioPlayer;
import com.pengpeng.elifecore.CoreAction;
import com.pengpeng.elifecore.CoreActionImpl;
import com.pengpeng.elifecore.ResponseCallbackListener;
import com.pengpeng.elifemodel.Audio;
import com.pengpeng.elifemodel.UserAudioBehavior;

import java.util.ArrayList;
import java.util.List;

//用户点进这个activity就知道哪段音频了而不是点击播放才知道
public class NetworkAudioPlayerActivity extends ActionBarActivity implements View.OnClickListener, ClassificationFragment.onPartDividedListener{

    private String TAG = "MainActivity";
    private ViewPager viewPager;
    private NetworkAudioPlayer networkAudioPlayer;
    private CoreAction action;
    private UserAudioBehavior uaUserAudioBehavior;
    private Audio audio;
    private List<Fragment> fragmentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_network_audio_player);
        init();
        uaUserAudioBehavior.setUserUUID("12345678");
        uaUserAudioBehavior.setAudioId("987654321");
        action.getAudio(uaUserAudioBehavior, new ResponseCallbackListener() {
            @Override
            public void onSuccess(Object data) {
                Log.i(TAG, "--onSuccess");
                audio = (Audio)data;
                networkAudioPlayer.setSourceUrl(((Audio)data).getAudioUrl());
                Toast.makeText(NetworkAudioPlayerActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error_code, String reason) {
//                sourceUrl = urlString2;
                Log.i(TAG, "--onFailure");
                Toast.makeText(NetworkAudioPlayerActivity.this, error_code+" "+reason, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init() {
        // TODO Auto-generated method stub
        ImageButton back = (ImageButton) this.findViewById(R.id.back);
        back.setOnClickListener(this);
        action = new CoreActionImpl();
        uaUserAudioBehavior = new UserAudioBehavior();

        viewPager = (ViewPager) this.findViewById(R.id.viewPager);


        Fragment classificationFragment = new ClassificationFragment();
        Fragment exerciseFragment = new ExerciseFragment();
        Fragment networkPlayerFragment = new NetworkPlayerFragment();

        networkAudioPlayer = new NetworkAudioPlayer(this);
//        ((NetworkPlayerFragment)networkPlayerFragment).setPlayer(networkAudioPlayer);

        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(classificationFragment);
        fragmentList.add(networkPlayerFragment);
        fragmentList.add(exerciseFragment);

        FragmentPagerAdapter adapter = new ListFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
//        viewPager.setOnPageChangeListener(new onFragmentPageChangedListener(fragmentList, viewPager.getCurrentItem()));
    }


    public ViewPager getViewPager(){
        return this.viewPager;
    }

    public NetworkAudioPlayer getNetworkAudioPlayer(){
        return networkAudioPlayer;
    }

    public Audio getAudio(){
        return this.audio;
    }




    @Override
    public void onClick(View view) {
        Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPartDivided(Bundle bundle) {
        ((NetworkPlayerFragment)fragmentList.get(1)).update(bundle);
    }
}
