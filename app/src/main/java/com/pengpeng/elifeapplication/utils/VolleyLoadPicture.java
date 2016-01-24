package com.pengpeng.elifeapplication.utils;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by pengpeng on 16-1-24.
 */
public class VolleyLoadPicture {
    private ImageLoader mImageLoader = null;
    private BitmapCache mBitmapCache;

    private ImageLoader.ImageListener one_listener;

    public VolleyLoadPicture(Context context,ImageView imageView){
        one_listener = ImageLoader.getImageListener(imageView, 0, 0);

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mBitmapCache = new BitmapCache();
        mImageLoader = new ImageLoader(mRequestQueue, mBitmapCache);
    }

    public ImageLoader getmImageLoader() {
        return mImageLoader;
    }

    public void setmImageLoader(ImageLoader mImageLoader) {
        this.mImageLoader = mImageLoader;
    }

    public ImageLoader.ImageListener getOne_listener() {
        return one_listener;
    }

    public void setOne_listener(ImageLoader.ImageListener one_listener) {
        this.one_listener = one_listener;
    }
}
