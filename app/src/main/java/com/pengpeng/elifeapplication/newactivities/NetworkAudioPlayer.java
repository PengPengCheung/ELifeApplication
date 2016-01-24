package com.pengpeng.elifeapplication.newactivities;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.pengpeng.elifeapplication.oldactivities.IAudioPlayer;
import com.pengpeng.elifeapplication.utils.Tools;

import java.io.IOException;

/**
 * Created by pengpeng on 15-12-8.
 */
public class NetworkAudioPlayer extends MediaPlayer implements IAudioPlayer {

    private Context context;
    private boolean paused = false;

    public boolean isPrepared() {
        return prepared;
    }

    public void setPrepared(boolean prepared) {
        this.prepared = prepared;
    }

    private boolean prepared = false;
    String urlString1= "http://sc1.111ttt.com/2015/1/12/08/105081507558.mp3";
    String urlString2 = "http://sc1.111ttt.com/2015/1/12/08/105082233058.mp3";
//    String urlString3 = "http://music.baidutt.com/up/kwcackwp/kuuwa.mp3";
    String urlString3 = "http://sc1.111ttt.com/2015/1/12/09/105090933364.mp3";
    String url = "http://192.168.235.8:8000/test";

    private String sourceUrl;

    public NetworkAudioPlayer(Context context){
        this.context = context;
//        action = new CoreActionImpl();
//        uaUserAudioBehavior = new UserAudioBehavior();
    }

    public void setSourceUrl(String url){
        this.sourceUrl = url;
    }



    private String getSourceUrl() throws IOException {
        return this.sourceUrl;
    }
        //192.168.235.8
        //10.0.2.2:8000
//        String url = "http://192.168.235.8:8000/test";
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("hello", "test");
//        JSONObject jsonObject = new JSONObject(map);
//        mDataApi.getAudioById("12345678", "987654321");


//        uaUserAudioBehavior.setUserUUID("12345678");
//        uaUserAudioBehavior.setAudioId("987654321");
//        action.getAudio(uaUserAudioBehavior, new ResponseCallbackListener() {
//            @Override
//            public void onSuccess(Object data) {
//                sourceUrl = ((Audio)data).getAudioUrl();
//                Toast.makeText(context, "onSuccess", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(String error_code, String reason) {
//                sourceUrl = urlString2;
//                Toast.makeText(context, error_code+" "+reason, Toast.LENGTH_SHORT).show();
//            }
//        });
//        while(sourceUrl == null){
//
//        }
//        return sourceUrl;



//        return Tools.untilFinished(sourceUrl);

//        while(responseStr == null){
//
//        }
//        Log.i("responseStr", responseStr);
//
//        String[] strs = new String[3];
//        strs[0] = urlString1;
//        strs[1] = urlString2;
//        strs[2] = urlString3;
//        Log.i("urlString", urlString1+" no?");
//        //System.out.println(urlString1);
//        Random random = new Random();
////        return strs[random.nextInt(3)];
//        return strs[0];







//        Log.i("requestString", requestQueue.toString());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                Log.i("responseString", s);
//                responseStr = s;
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                volleyError.printStackTrace();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("hello", "test");
//                return map;
//            }
//        };

/*AsyncHttpClient post
* */
        //要等到返回数据，调用下面的方法才能继续往下执行，否则继续运行请求往下的方法会报错，因此要在接口方法内写与请求数据有关的操作
        //
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.put("hello", "test");
//        client.post(url, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                String response = new String(bytes);
//                responseStr = response;
//                Log.i("responseStr", responseStr);
//                JSONObject json = null;
//                try {
//                    if(responseStr!=null){
//                        Log.i("requestString4", responseStr);
//                        json = new JSONObject(responseStr);
//                        urlString1 = json.getString("testResult");
//                        Log.i("requestString", urlString1);
//                    }else{
//                        Log.i("requestString5", "responseStr is null");
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                throwable.printStackTrace();
//            }
//        });


//


/*Volley JsonObject*/
//        JsonRequest<JSONObject> stringRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                Log.i("response", jsonObject.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Accept", "application/json");
//                headers.put("Content-Type", "application/json; charset=UTF-8");
//
//                return headers;
//            }
//        };
//        try {
//            Log.i("requestString2", stringRequest.getBody().toString());
//            MyApplication.getRequestQueue().start();
//            MyApplication.getRequestQueue().add(stringRequest);
//
//            Log.i("requestString3", stringRequest.getBody().toString());
//        } catch (Exception authFailureError) {
//            authFailureError.printStackTrace();
//        }





//        Request<JSONObject> jsonObjectRequest = new NormalPostRequest(url, map, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                Log.i("jsonObject", jsonObject.toString());
//                try {
//                    urlString1 = jsonObject.getString("testResult");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.i("volleyError", volleyError.toString());
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
//        Log.i("jsonObjectRequest", jsonObjectRequest.toString());
//        final String jsonStr = jsonObject.toString();
//        Log.i("requestString", jsonStr);
//        String responseStr = post(url, jsonStr);
//        Log.i("responseString", responseStr);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    String responseStr = post(url, jsonStr);
//                    Log.i("responseString", responseStr);
//                    JSONObject responseJson = new JSONObject(responseStr);
//                    Log.i("responseResult", responseJson.get("testResult").toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();








    public void setPaused(boolean paused) {
        this.paused = paused;
    }


    @Override
    public boolean isPaused() {
        return this.paused;
    }

    @Override
    public void playPause() {
        if(isPlaying()){
            pause();
            paused = true;
        }
    }

    @Override
    public void playPrevious() {
        if(Tools.isNetworkAvailable(context)){
            playPrepared();
        }
    }

    @Override
    public void playNext() {
        if(Tools.isNetworkAvailable(context)){
            playPrepared();
        }
    }

    @Override
    public void playPrepared() {
        if(Tools.isNetworkAvailable(context)){
            try {
                reset();
                setDataSource(getSourceUrl());
                setAudioStreamType(AudioManager.STREAM_MUSIC);
                prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void play() {
        if (isPaused()) {
            start();
            paused = false;
        } else {
            playPrepared();
        }
    }
}
