package com.pengpeng.elifedataapi.NetUtils;

/**
 * Created by pengpeng on 16-1-13.
 */
public final class CommonResource {
    public static final class Path{
        public static final String serverPath = "http://192.168.235.33:8000/";
//        public static final String testPath = "http://192.168.235.33:8000/getAudio";//丽璇的data的数据类型是list而不是对象！！！！
        public static final String testPath = serverPath+"getAudio";
        public static final String audioListPath = serverPath+"showLists/";
    }

    public static final class Event{
        public final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
        public final static String TIME_OUT_EVENT_MSG = "连接服务器失败";
        public final static String DATA_WRONG_EVENT = "FETCHED_DATA_WRONG";
        public final static String DATA_WRONG_EVENT_MSG = "数据获取错误";
    }

}
