package com.tnecesoc.MyInfoDemo.Utils;

import java.io.*;
import java.net.ContentHandler;
import java.net.ContentHandlerFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public class HttpUtil {

    public interface HttpResponseListener {

        void onSuccess(String responseContent);

        void onError(Exception e);
    }

    public static void sendPostRequest(String url, Map<String, String> parameters, HttpResponseListener listener) {

        try {

            URL server = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) server.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(4000);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));

            boolean isNotFirst = false;
            for (Map.Entry entry : parameters.entrySet()) {
                if (isNotFirst) {
                    out.write("&" + entry.getKey() + "=" + entry.getValue());
                } else {
                    out.write(entry.getKey() + "=" + entry.getValue());
                }
                isNotFirst = true;
            }
            out.flush();
            out.close();
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader in = new InputStreamReader(connection.getInputStream(), "UTF-8");

                BufferedReader reader = new BufferedReader(in);

                String line, responseContent = "";
                while ((line = reader.readLine()) != null) {
                    responseContent += line;
                }

                listener.onSuccess(responseContent);
            } else {
                throw new IOException("Connection failed");
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            listener.onError(e);
        }
    }

}
