package com.pengpeng.elifeapplication.newactivities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pengpeng.elifeapplication.R;

public class AudioListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener<ListView>{

    private PullToRefreshListView audioListView;
//    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);
        initView();
        setListener();

    }

    private void setListener() {
        audioListView.setOnItemClickListener(this);
        audioListView.setOnRefreshListener(this);
    }

    private void initView() {
        audioListView = (PullToRefreshListView)findViewById(R.id.audio_list);
//        audioListView.setAdapter();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {

    }
}
