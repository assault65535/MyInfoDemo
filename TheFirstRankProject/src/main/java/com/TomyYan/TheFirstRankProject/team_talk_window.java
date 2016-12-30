package com.TomyYan.TheFirstRankProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.TomyYan.GlobalModel.Local.SessionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/10/2.
 */
public class team_talk_window extends Activity {

    private String user;//使用者
    private String team;//对话圈
    private TextView textView;//圈名TextView
    private Msg msgs[];
    private String[] massages;//总消息
    private String[] massage;//消息
    private String[] talker;//消息发出者
    private String[] temp;//临时存放点
    private String sendMassage;
    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;
    private String sendOption;
    private SessionHelper sessionHelper;
    private ImageButton refresh;

    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;


    private List<Msg> msgList = new ArrayList<Msg>();

    private static final int SHOW_RESULT = 0;
    private static final String SERVER_ADDRESS = "http://172.22.30.48:8080/firstProject_war_exploded/server-plus-demo";
    private static final String TAG = "CircleEnjoy.java";
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
        setContentView(R.layout.activity_talk);

        //启动时不弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //获取对话圈的拥有者及圈名
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        //
        sessionHelper = new SessionHelper(team_talk_window.this);
        user = sessionHelper.getSessionAttribute(SessionHelper.KEY_PHONE);
        //
        team = intent.getStringExtra("team");
        textView = (TextView) findViewById(R.id.team_talk_top);
        textView.setText(team);
        refresh = (ImageButton) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOption = "get_talk_massage";
                sendRequestToServer(user, team, "get_talk_massage");
                Toast.makeText(team_talk_window.this, "刷新成功", Toast.LENGTH_SHORT).show();
            }
        });

        //initMsgs();//初始化消息数据
        sendOption = "get_talk_massage";
        sendRequestToServer(user, team, "get_talk_massage");

        adapter = new MsgAdapter(team_talk_window.this, R.layout.msg_item, msgList);
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgListView = (ListView) findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(msgList.size());
                    inputText.setText("");
                    sendMassage = team + "!!-!!" + content;
                    sendOption = "send_talk_massage";
                    sendRequestToServer(user, sendMassage, "send_talk_massage");
                }
            }
        });
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //继承了Activity的onTouchEvent方法，直接监听点击事件
//        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//            //当手指按下的时候
//            x1 = event.getX();
//            y1 = event.getY();
//        }
//        if(event.getAction() == MotionEvent.ACTION_UP) {
//            //当手指离开的时候
//            x2 = event.getX();
//            y2 = event.getY();
//            if(y1 - y2 > 50) {
//                sendOption="get_talk_massage";
//                sendRequestToServer(user, team, "get_talk_massage");
//                Toast.makeText(team_talk_window.this, "向上滑", Toast.LENGTH_SHORT).show();
//            } else if(y2 - y1 > 50) {
//                Toast.makeText(team_talk_window.this, "向下滑", Toast.LENGTH_SHORT).show();
//            } else if(x1 - x2 > 50) {
//                Toast.makeText(team_talk_window.this, "向左滑", Toast.LENGTH_SHORT).show();
//            } else if(x2 - x1 > 50) {
//                Toast.makeText(team_talk_window.this, "向右滑", Toast.LENGTH_SHORT).show();
//            }
//        }
//        return super.onTouchEvent(event);
//    }

    private void sendRequestToServer(String user,String team,String option) {
        circleBuildSend.postHttpRequest(SERVER_ADDRESS, user, team,option, new HttpCallbackListener() {
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
//        Msg msg1 = new Msg("Hello, how are you?", Msg.TYPE_RECEIVED);
//        msgList.add(msg1);
//        Msg msg2 = new Msg("Fine, thank you, and you?", Msg.TYPE_SEND);
//        msgList.add(msg2);
//        Msg msg3 = new Msg("I am fine, too!", Msg.TYPE_RECEIVED);
//        msgList.add(msg3);
        msgs=new Msg[massage.length];
        adapter.clear();
        for(int i=0;i<massage.length;i++){
            if(user.equals(talker[i])){
                msgs[i]=new Msg(massage[i],Msg.TYPE_SEND);
            }
            else{
                msgs[i]=new Msg(massage[i],Msg.TYPE_RECEIVED);
            }
            msgList.add(msgs[i]);
        }
    }
}
