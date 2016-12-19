package com.tnecesoc.MyInfoDemo.Utils;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Tnecesoc on 2016/12/6.
 */
public class OkHttpUtil {

    private static final OkHttpClient client = new OkHttpClient();

    private static final MediaType TYPE_PNG = MediaType.parse("image/png");

    public static void uploadPicture(final String url, final Map<String, String> extraHeaders, final File picture, final HttpUtil.HttpResponseListener listener) {

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", picture.getName(),
                        RequestBody.create(TYPE_PNG, picture))
                .build();

        Request.Builder preRequest = new Request.Builder();

        boolean isFirstHeader = true;

        for (Map.Entry<String, String> entry : extraHeaders.entrySet()) {
            if (isFirstHeader) {
                preRequest.header(entry.getKey(), entry.getValue());
                isFirstHeader = false;
            } else {
                preRequest.addHeader(entry.getKey(), entry.getValue());
            }
        }

        preRequest
                .url(url)
                .post(requestBody);

        try {
            Response response = client.newCall(preRequest.build()).execute();

            if (response.isSuccessful()) {

                if (response.body().string().isEmpty()) {
                    listener.onSuccess("true");
                } else {
                    listener.onSuccess(response.body().string());
                }

            } else {
                System.err.println(response.body().string());
                throw new IOException("Upload failed with code " + response.code());
            }

        } catch (IOException e) {
            e.printStackTrace();
            listener.onError(e);
        }


    }

}