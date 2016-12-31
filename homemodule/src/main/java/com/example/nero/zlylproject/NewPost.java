package com.example.nero.zlylproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by nilu on 2016/12/9.
 */
public class NewPost {
    public Activity a;
    private String community;
    private String postAndContent;
    private String option;
    public SetListView listView;
    private static final int SHOW_RESULT = 0;
    private static final String SERVER_ADDRESS = "http://172.22.147.221:8080/server-plus-demo";
    private static final String TAG = "GetPostTopics.java";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESULT:
                    //System.out.println(msg.obj.toString());
                    if(msg.obj.toString().equals("1")){
                        Toast.makeText(a, "发帖成功", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent();
                        a.setResult(25252);
                        a.finish();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    NewPost(){
    }
    public void sendBuildInfo(String community,String postAndContent,String option){
        this.option=option;
        this.community=community;
        this.postAndContent=postAndContent;
        listView=new SetListView();
        sendRequestToServer();
    }

    private void sendRequestToServer() {
        postBuildSend.postHttpRequest(SERVER_ADDRESS, community, postAndContent,option, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message msg = new Message();
                msg.what = SHOW_RESULT;
                msg.obj = response;
                handler.sendMessage(msg);
            }
            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Connection Error");
            }
        });
    }
    public void sendActivity(Activity a){
        this.a=a;
    }
}
