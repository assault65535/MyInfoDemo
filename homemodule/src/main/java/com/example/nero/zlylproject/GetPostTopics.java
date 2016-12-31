package com.example.nero.zlylproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by nilu on 2016/11/11.
 */
public class GetPostTopics {
    public Activity a;
    public View view;

    public String[] topic = null;
    private String community="1";
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
                    topic = new String[65];
                    if (msg.obj.toString() != " ") {
                        topic = msg.obj.toString().split(" ");
                    }
//                SetListView listView=new SetListView();
                    if ("get_post".equals(option) && topic != null) {
                        if (view == null) {
                            listView.setListView(topic, a, R.id.posts);
                        } else {
                            listView.setListView(topic, view, R.id.posts);
                        }
                        //设置监听器
                        listView.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("community", community);
                                intent.putExtra("post", topic[position]);
                                // System.out.println("test"+topic[position]);
                                intent.setClass(a, post_talk.class);
                                a.startActivity(intent);
                            }
                        });
                    } else if ("get-other-post".equals(option) && topic != null) {
                        if (view == null) {
                            listView.setListView(topic, a, R.id.searchPostName);
                        } else {
                            listView.setListView(topic, view, R.id.posts);
                        }
                        //设置监听器
                        listView.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //跳转该帖子页面
                            }
                        });
                    }


                    break;
                default:
                    break;
            }
        }
    };

    public GetPostTopics() {
    }

    public void getPostTopics(String community, String option) {
        this.option = option;
        this.community = community;
        listView = new SetListView();
        sendRequestToServer();

    }

    private void sendRequestToServer() {
        HttpUtil.postHttpRequest(SERVER_ADDRESS,community, "", option, new HttpCallbackListener() {
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

    public void sendActivity(Activity a) {
        this.a = a;
    }

    public void sendView(View view) {
        this.view = view;
    }

}
