package com.TomyYan.TheFirstRankProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.TomyYan.GlobalModel.Local.SessionHelper;

/**
 * Created by dell on 2016/12/9.
 */
public class CircleEnjoy extends Activity {

    private String user;
    private String team;
    private TextView TopText;
    private ImageButton back;
    private TextView introduce;
    private Button enjoy;
    private ButtonListener buttonListener;
    private SessionHelper sessionHelper;
    private static final int SHOW_RESULT = 0;
    private static final String SERVER_ADDRESS = "http://172.22.30.48:8080/firstProject_war_exploded/server-plus-demo";
    private static final String TAG = "CircleEnjoy.java";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESULT:
                    String show=msg.obj.toString();
                    System.out.println(show+"test");
                    introduce.setText(show);
                    break;
                default:
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_enjoy);
        Intent intent=getIntent();
        user=intent.getStringExtra("user");
        //
        sessionHelper = new SessionHelper(CircleEnjoy.this);
        user=sessionHelper.getSessionAttribute(SessionHelper.KEY_PHONE);
        //
        team=intent.getStringExtra("team");
        TopText=(TextView)findViewById(R.id.circle_enjoy_top);
        TopText.setText(team);
        buttonListener=new ButtonListener();
        back=(ImageButton)findViewById(R.id.circle_enjoy_back);
        introduce=(TextView)findViewById(R.id.circle_enjoy_introduce);
        enjoy=(Button)findViewById(R.id.circle_enjoy_enjoy);
        back.setOnClickListener(buttonListener);
        enjoy.setOnClickListener(buttonListener);
        sendRequestToServer("",team, "circle_enjoy_introduce");
    }
    private void sendRequestToServer(String user,String team,String option) {
        circleBuildSend.postHttpRequest(SERVER_ADDRESS, user, team,option, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message msg = new Message();
                msg.what = SHOW_RESULT;
                msg.obj = response + "";
                handler.sendMessage(msg);
            }
            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Connection Error");
            }
        });
    }
    class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            int i = v.getId();
            if (i == R.id.circle_enjoy_back) {
                finish();

            } else if (i == R.id.circle_enjoy_enjoy) {
                sendRequestToServer(user, team, "circle_enjoy_enjoy");

            }
        }
    }
}
