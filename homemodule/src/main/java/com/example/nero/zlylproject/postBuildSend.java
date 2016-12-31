package com.example.nero.zlylproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by nilu on 2016/12/9.
 */
public class postBuildSend {
    public static void postHttpRequest(final String address, final String a, final String b, final String c,final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {

                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    // 设置编码格式
                    connection.setRequestProperty("charset","utf8");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestProperty("para1", a);
                    connection.setRequestProperty("para2", URLEncoder.encode(b, "utf-8"));
                    connection.setRequestProperty("para3", c);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String response;
                    response = reader.readLine();
                    if (listener != null) {
                        listener.onFinish(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
