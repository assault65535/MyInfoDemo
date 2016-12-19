package com.TomyYan.TheFirstRankProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by dell on 2016/10/31.
 */
public class BuildCircle {
    public Activity a;
    private String user;
    private String circleAndIntroduce;
    private String option;
    public SetListView listView;
    private static final int SHOW_RESULT = 0;
    private static final String SERVER_ADDRESS = "http://172.22.30.48:8080/firstProject_war_exploded/server-plus-demo";
    private static final String TAG = "GetMyCircleName.java";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESULT:
                    //System.out.println(msg.obj.toString());
                    if(msg.obj.toString().equals("1")){
                        Toast.makeText(a, "创建成功", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent();
                        intent.putExtra("user",user);
                        intent.setClass(a,my_circle.class);
                        a.startActivity(intent);
                        a.finish();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    BuildCircle(){
    }
    public void sendBuildInfo(String user,String circleAndIntroduce,String option){
        this.option=option;
        this.user=user;
        this.circleAndIntroduce=circleAndIntroduce;
        listView=new SetListView();
        sendRequestToServer();
    }
    private void sendRequestToServer() {
        circleBuildSend.postHttpRequest(SERVER_ADDRESS, user, circleAndIntroduce,option, new HttpCallbackListener() {
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
