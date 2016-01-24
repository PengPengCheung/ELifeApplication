package com.pengpeng.elifeapplication.newactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pengpeng.elifeapplication.R;
import com.pengpeng.elifecore.CoreAction;
import com.pengpeng.elifecore.CoreActionImpl;
import com.pengpeng.elifecore.ResponseCallbackListener;
import com.pengpeng.elifemodel.Audio;

import java.util.LinkedList;
import java.util.List;

public class AudioListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener<ListView> {

    private PullToRefreshListView audioListView;
    private CoreAction action;
    private AudioListViewAdapter adapter;
    private String TAG = "AudioListActivity";
    private LinkedList<Audio> audioList;
    private String uuid;
    private Boolean isRefreshing = false;
//    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);
        init();
        setListener();
//        setListener();
//        progressBar.

        if (uuid != null) {
            action.getAudioList(uuid, new ResponseCallbackListener<List<Audio>> () {
                @Override
                public void onSuccess(List<Audio> data) {
                    audioList.addAll(data);
                    adapter.setData(audioList);
                    adapter.notifyDataSetChanged();

                    audioListView.setAdapter(adapter);
//                    audioListView.onRefreshComplete();
                }

                @Override
                public void onFailure(String error_code, String reason) {
                    Toast.makeText(AudioListActivity.this, error_code + " " + reason, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void setListener() {
        audioListView.setOnItemClickListener(this);
        audioListView.setOnRefreshListener(this);

    }

    private void init() {
        audioListView = (PullToRefreshListView) findViewById(R.id.audio_list);
        audioListView.setPullToRefreshEnabled(true);
        audioListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        audioListView.getLoadingLayoutProxy().setPullLabel("Refreshing");
        adapter = new AudioListViewAdapter(AudioListActivity.this);
        action = new CoreActionImpl();
        audioList = new LinkedList<Audio>();
        Intent intent = getIntent();
        uuid = intent.getStringExtra("userId");

        Log.i(TAG, uuid);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(AudioListActivity.this, NetworkAudioPlayerActivity.class);
        Log.i(TAG, String.valueOf(i));
        Log.i(TAG, String.valueOf(audioList.size()));
        intent.putExtra("audioId", audioList.get(i - 1).getAudioId());
        intent.putExtra("userId", uuid);
        intent.putExtra("audioTitle", audioList.get(i-1).getAudioTitle());
        startActivity(intent);
//        AudioListActivity.this.finish();

    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        action.getAudioList(uuid, new ResponseCallbackListener<List<Audio>>() {
            @Override
            public void onSuccess(List<Audio> data) {
                isRefreshing = true;
                audioList.addLast(data.get(0));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error_code, String reason) {
                isRefreshing = false;
                Toast.makeText(AudioListActivity.this, error_code + " " + reason, Toast.LENGTH_SHORT).show();
            }
        });
        while(!isRefreshing){}

        audioListView.onRefreshComplete();

    }
}
