package com.pengpeng.elifeapplication.newactivities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.pengpeng.elifeapplication.R;
import com.pengpeng.elifeapplication.utils.BitmapCache;
import com.pengpeng.elifemodel.Audio;

import java.util.List;

/**
 * Created by pengpeng on 16-1-19.
 */
public class AudioListViewAdapter extends BaseAdapter {

    private List<Audio> audioList;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    private BitmapCache mBitmapCache;

    public AudioListViewAdapter(Context context) {

        mInflater = LayoutInflater.from(context);
        RequestQueue mQueue = Volley.newRequestQueue(context);
        mBitmapCache = new BitmapCache();
//        mBitmapCache.
        mImageLoader = new ImageLoader(mQueue, mBitmapCache);

    }

    public void setData(List<Audio> list) {
        this.audioList = list;
    }

    @Override
    public int getCount() {

        return audioList.size();
    }

    @Override
    public Object getItem(int i) {

        return audioList.get(i);
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.list_item, null);
            viewHolder.title = (TextView) view.findViewById(R.id.titleTextView);
            viewHolder.date = (TextView) view.findViewById(R.id.dateTextView);
            viewHolder.img = (NetworkImageView) view.findViewById(R.id.imageView);
            viewHolder.img.setErrorImageResId(R.drawable.error);
            viewHolder.img.setDefaultImageResId(R.drawable.start);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(audioList.get(i).getAudioTitle());
        viewHolder.date.setText(audioList.get(i).getAudioDate());
//        Bitmap bitmap = BitmapFactory.decodeByteArray();
        Log.i("audioImageUrl", audioList.get(i).getAudioImageUrl());
        viewHolder.img.setImageUrl(audioList.get(i).getAudioImageUrl(), mImageLoader);
//        viewHolder.img.setImageResource(R.drawable.start);
        return view;
    }

    static class ViewHolder {
        public NetworkImageView img;
        public TextView title;
        public TextView date;
    }
}
