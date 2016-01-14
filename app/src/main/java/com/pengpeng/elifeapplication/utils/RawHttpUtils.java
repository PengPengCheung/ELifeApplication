package com.pengpeng.elifeapplication.utils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pengpeng on 16-1-11.
 */
public class RawHttpUtils {
    public RawHttpUtils() {
        // TODO Auto-generated constructor stub
    }
    public static String sendPostMessage(JSONObject json,
                                         String encode, String path) {
        URL url=null;
        System.out.println("bufferToString " + json.toString());
        try {
            url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            System.out.println(1);
            urlConnection.setConnectTimeout(8000);

            urlConnection.setDoInput(true);// 从服务器获取数据
            urlConnection.setDoOutput(true);// 向服务器写数据
            // 获得上传信息的字节大小和长度
            byte[] mydata = json.toString().getBytes();
            urlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Length",
                    String.valueOf(mydata.length));
            System.out.println(2);

            // 获得输出流，向服务器输出数据
            OutputStream outputStream;
            outputStream = urlConnection.getOutputStream();
            System.out.println(3);
            outputStream.write(mydata, 0, mydata.length);

            outputStream.close();
            System.out.println(4);

            // 获得服务器响应结果的状态码
            int responseCode = urlConnection.getResponseCode();
            System.out.println(responseCode);
            if (responseCode == 200) {
                return changeInputStream(urlConnection.getInputStream(), encode);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "hello";
    }

    private static String changeInputStream(InputStream inputStream,
                                            String encode) {
        // TODO Auto-generated method stub
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, len);
                }
                result = new String(outputStream.toByteArray(), encode);
                System.out.println(result);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }
}
