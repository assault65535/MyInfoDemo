package com.example.nero.zlylproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;
import android.os.Message;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


import GlobalModel.Local.SessionHelper;

/**
 * Created by nilu on 2016/11/18.
 */
public class post_talk extends Activity{

        private String community="1";
        private String topic;
        private TextView textView;//帖名TextView
        private String[] massages;//总消息
        private String[] massage;//消息
        private Msg msgs[];
        private String[] talker;//消息发出者
        private String[] temp;//临时存放点
        private String sendMassage;
        private ListView msgListView;
        private EditText inputText;
        private Button send;
        private MsgAdapter adapter;
        private String sendOption;
        private SessionHelper sessionHelper;
        ImageButton backButton = null;

        private List<Msg> msgList = new ArrayList<Msg>();

        private static final int SHOW_RESULT = 0;
        private static final String SERVER_ADDRESS = "http://172.22.147.221:8080/server-plus-demo";
        private static final String TAG = "post_talk.java";
        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SHOW_RESULT:
                        System.out.println("cheshi"+msg.obj.toString());
                        if("send_talk_massage".equals(sendOption)){
                            if("1".equals(msg.obj.toString())){
                                //提示发送成功
                                break;
                            }
                        }
                        else if("get_talk_massage".equals(sendOption)){
                            if(" ".equals(msg.obj.toString())){
                                break;
                            }
                            else{
                                massages=msg.obj.toString().split(" ");
                                massage=new String[massages.length];
                                talker=new String[massages.length];
                                for(int i=0;i<massages.length;i++){
                                    temp=massages[i].split("!!!");
                                    massage[i]=temp[0];
                                    talker[i]=temp[1];
                                }
                                initMsgs();
                            }

                        }
                        break;
                    default:
                        break;
                }
            }
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.post_talk);
            Intent intent=getIntent();
          // community=intent.getStringExtra("community");



            topic=intent.getStringExtra("post");
            System.out.println("test"+community+"!!"+topic);
            textView=(TextView)findViewById(R.id.post_talk_top);
            textView.setText(topic);

          //  initMsgs();
            adapter = new MsgAdapter(post_talk.this, R.layout.msg_item, msgList);
            inputText = (EditText)findViewById(R.id.input_text);
            send = (Button)findViewById(R.id.send);
            msgListView = (ListView)findViewById(R.id.content);
            msgListView.setAdapter(adapter);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String content = inputText.getText().toString();
                    if (!"".equals(content)) {
                       // System.out.println("testtest"+content);
                        Msg msg = new Msg(content, Msg.TYPE_SEND);
                        msgList.add(msg);
                        adapter.notifyDataSetChanged();
                        msgListView.setSelection(msgList.size());
                        inputText.setText("");
                        sendMassage = topic + "!!-!!" + content;
                        sendOption = "send_talk_massage";
                        sendRequestToServer(community, sendMassage, "send_talk_massage");
                        System.out.println("hhh"+community+sendMassage);
                    }
                }
            });
            sendOption="get_talk_massage";
            sendRequestToServer(community, topic, "get_talk_massage");
            
            
            backButton = (ImageButton) findViewById(R.id.back);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view1) {
                    finish();
                }
            });
            
        }

    private void sendRequestToServer(String community,String post,String option) {
        postBuildSend.postHttpRequest(SERVER_ADDRESS,community,post,option, new HttpCallbackListener() {
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
    
        private void initMsgs() {
            msgs=new Msg[massage.length];
            for(int i=0;i<massage.length;i++){
                if(community.equals(talker[i])){
                    msgs[i]=new Msg(massage[i],Msg.TYPE_SEND);
                }
                else{
                    msgs[i]=new Msg(massage[i],Msg.TYPE_RECEIVED);
                }
                msgList.add(msgs[i]);
            }
        }

}
