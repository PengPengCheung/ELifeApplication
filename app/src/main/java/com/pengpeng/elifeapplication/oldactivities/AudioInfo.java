package com.pengpeng.elifeapplication.oldactivities;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by pengpeng on 15-12-8.
 */
public class AudioInfo {
    private static final String TAG = "AudioInfo";

    public long getCurrentAudioId(Cursor cursor) {
        int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        long thisId = cursor.getLong(idColumn);
        Log.i(TAG + " thisId", String.valueOf(thisId));
        return thisId;
    }

    public String getCurrentAudioTitle(Cursor cursor) {
        int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        String thisTitle = cursor.getString(titleColumn);
        Log.i(TAG + " thisTitle", thisTitle);
        return thisTitle;
    }

    public Uri getCurrentAudioUri(Cursor cursor){
        long thisId = getCurrentAudioId(cursor);
        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, thisId);
        Log.i(TAG + " contentUri", contentUri.toString());
        return contentUri;
    }
}
