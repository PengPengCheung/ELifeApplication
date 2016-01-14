package com.pengpeng.elifeapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by pengpeng on 15-12-8.
 */
public class Tools {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            /*没有网络*/
            return false;
        } else {
            /*有网络*/
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo network : info) {
                    if (network.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 传入的time参数单位为milliseconds，即毫秒.这个方法可以将毫秒单位的时间转换为0：00形式的时间
     *
     * @param time 传入的时间长度，单位为milliseconds，即毫秒
     * @return 返回00：00形式的时间字符串
     */
    public static String getTimeText(int time) {
        if (time < 0) {
            return "time wrong";
        }
        int totalSeconds = time / 1000;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        String showTime;
        if (seconds > 9 && seconds < 60) {
            showTime = minutes + ":" + seconds;
        } else {
            showTime = minutes + ":0" + seconds;
        }
        return showTime;
    }



    public static <T> T untilFinished(T var){
        while(var == null){

        }
        return var;
    }
//    public static void main(String[] args){
//        System.out.println("hello I am .");
//
//        new JSONObject(new HashMap());
//    }
}
