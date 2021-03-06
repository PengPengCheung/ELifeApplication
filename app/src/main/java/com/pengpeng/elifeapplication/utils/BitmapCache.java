package com.pengpeng.elifeapplication.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by pengpeng on 16-1-22.
 */
public class BitmapCache implements ImageLoader.ImageCache{

    private LruCache<String, Bitmap> mCache;

    public BitmapCache(){
        int MaxSize = 10*1024*1024;
        mCache = new LruCache<String, Bitmap>(MaxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String s) {
        return mCache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        mCache.put(s,bitmap);
    }
}
