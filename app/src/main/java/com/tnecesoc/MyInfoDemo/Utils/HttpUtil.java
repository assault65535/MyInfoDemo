package com.tnecesoc.MyInfoDemo.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tnecesoc.MyInfoDemo.Entity.Container;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public class HttpUtil {

    public interface HttpResponseListener {

        void onSuccess(String responseContent);

        void onError(Exception e);
    }

    public interface HttpErrorListener {

        void onError(Exception e);

    }

    public static Bitmap downloadPicture(final String url) {

        try {
            BufferedInputStream inputStream = new BufferedInputStream(new URL(url).openStream());
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Deprecated
    public static void uploadPicture(final String url, final Map<String, String> extraHeaders, final File picture, final HttpResponseListener listener) {

        try {

            URL server = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) server.openConnection();

            final String BOUNDRY = "-----------------------------" + System.currentTimeMillis();
            final String CRLF = "\r\n";

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(4000);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDRY);
            connection.setChunkedStreamingMode(0);

            customizeHeaders(connection, extraHeaders);

//            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());

            out.writeBytes("--" + BOUNDRY + CRLF);
            out.writeBytes("Content-Disposition: form-data; filename=\"" + picture.getName() + "\"" + CRLF);
            out.writeBytes(CRLF);

            FileInputStream fStream = new FileInputStream(picture);

            byte[] buffer = new byte[Math.min(fStream.available(), 1024 * 1024)];

            while (fStream.read(buffer) != -1) {
                out.write(buffer, 0, buffer.length);
            }

            out.writeBytes(CRLF);
            out.writeBytes("--" + BOUNDRY + "--" + CRLF);

            fStream.close();
            out.flush();
            out.close();

            connection.connect();

            String response = receiveFromConnection(connection);

            listener.onSuccess(response);

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            listener.onError(e);
        }
    }

    public static<T> T sendPostRequestForResult(final TypeToken<T> tClass, String url, Map<String, String> headers,  Map<String, String> parameters, final HttpErrorListener listener) {
        final Container<T> container = new Container<>();

        try {

            URL server = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) server.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(2000);
            customizeHeaders(connection, headers);
            customizeParameters(connection, parameters);
            connection.connect();

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());

            // TODO 好气啊
            //noinspection unchecked
            container.setValue((T) new Gson().fromJson(reader, tClass.getType()));

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onError(e);
            }
        }

        return container.getValue();
    }

    public static void sendPostRequest(String url, Map<String, String> parameters, HttpResponseListener listener) {
        sendPostRequestWithExtraHeader(url, null, parameters, listener);
    }

    public static void sendPostRequestWithExtraHeader(String url, Map<String, String> headers, Map<String, String> parameters, HttpResponseListener listener) {
        try {

            URL server = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) server.openConnection();


            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(2000);

            customizeHeaders(connection, headers);
            customizeParameters(connection, parameters);


            connection.connect();

            String response = receiveFromConnection(connection);

            listener.onSuccess(response);

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            listener.onError(e);
        }
    }


    private static void customizeHeaders(HttpURLConnection connection, Map<String, String> headers) {
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    private static void customizeParameters(HttpURLConnection connection, Map<String, String> parameters) throws IOException {
        if (parameters != null) {
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
        }
    }

    private static String receiveFromConnection(HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return fromInputStream(connection.getInputStream());
        } else {
            throw new IOException("Connection failed with code " + connection.getResponseCode());
        }
    }


    private static String fromInputStream(InputStream inputStream) throws IOException {

        if (inputStream == null) {
            return null;
        }

        // utf-8 normally.
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));

        String line, ans = "";
        while ((line = reader.readLine()) != null) {
            ans += line;
        }

        return ans;
    }

}
