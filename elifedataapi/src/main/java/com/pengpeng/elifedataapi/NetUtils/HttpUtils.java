package com.pengpeng.elifedataapi.NetUtils;


import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by pengpeng on 16-1-13.
 */
public class HttpUtils {

    private final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

    private String responseStr;

    private static HttpUtils instance = null;

    private HttpUtils(){}

    public static HttpUtils getInstance(){
        if(instance == null){
            synchronized (HttpUtils.class){
                if(instance == null){
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    public <T> T handlePost(String url, Object object, Type typeOfT) throws JsonParseException {
        if(object != null && url != null && typeOfT != null){
            String json = JsonUtils.toJson(object);
            String response = post(url, json);//如果返回的字符串为空的时候，response会被赋值为出错信息，此时json解析会出错
            return JsonUtils.fromJson(response, typeOfT);
        }else{
            return null;
        }
    }

    /*okhttp post*/
    private String post(String url, String json) {

        OkHttpClient client = new OkHttpClient();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

//        responseContent = client.newCall(request).execute();同步post

        //异步post
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
                responseStr = TIME_OUT_EVENT_MSG;
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response != null){
                    responseStr = response.body().string();
                }
            }
        });

        //如果返回字符串为空,调用onFailure方法，字符串变成TIME_OUT_EVENT_MSG的值，仍然可以跳出循环
        while(responseStr == null){

        }
        return responseStr;
    }

}
