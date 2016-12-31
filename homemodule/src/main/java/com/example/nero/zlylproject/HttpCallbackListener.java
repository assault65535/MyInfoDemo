package com.example.nero.zlylproject;

/**
 * Created by nilu on 2016/11/1.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}