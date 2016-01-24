package com.pengpeng.elifeapplication.newactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.pengpeng.elifeapplication.R;
import com.pengpeng.elifeapplication.utils.Tools;
import com.pengpeng.elifecore.CoreAction;

public class StartActivity extends ActionBarActivity {

    private CoreAction action;
    private String uuid;
    private String TAG = "StartActivity";
//    private ImageView loadImage;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);
        initView();


        // 创建并实例化透明度渐变动画，取值范围为0.0 - 1.0，0.0表示完全不显示，1.0表示完全显示
        AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
        // 设置动画持续时间
        animation.setDuration(6000);
        // 设置组件动画
        ll.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub
//                Toast.makeText(StartActivity.this, "动画开始", Toast.LENGTH_SHORT)
//                        .show();

                init();
            }


            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                // TODO Auto-generated method stub
//                Toast.makeText(StartActivity.this, "动画结束", Toast.LENGTH_SHORT)
//                        .show();
                if(Tools.isNetworkAvailable(StartActivity.this)){
                    Intent intent = new Intent(StartActivity.this, AudioListActivity.class);
                    intent.putExtra("userId", uuid);
                    startActivity(intent);
                    StartActivity.this.finish();
                }
            }
        });
    }

    private void init() {
            uuid = Tools.getUUID(StartActivity.this);
            Log.i(TAG, uuid);
    }

    private void initView() {
//        loadImage=(ImageView)findViewById(R.id.loadimage);
        ll = (LinearLayout)findViewById(R.id.startlayout);
    }
}
