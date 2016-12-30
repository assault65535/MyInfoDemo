package com.TomyYan.TheFirstRankProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.TomyYan.TheFirstRankProject.R;

public class LoginIn extends Activity implements View.OnClickListener {
    private EditText number;
    private EditText key;
    private Button sureButton;
    private String a;
    private String b;
    private static final int SHOW_RESULT = 0;
    private static final String TAG = "MainActivity";
    private static final String SERVER_ADDRESS = "http://172.22.30.48:8080/firstProject_war_exploded/server-plus-demo";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESULT:
                    //res.setText(msg.obj.toString());
                    if(msg.obj.toString().equals("1")){
                        Intent intent=new Intent();
                        intent.putExtra("user",a);
                        intent.setClass(LoginIn.this,main_page.class);
                        LoginIn.this.startActivity(intent);
                        finish();
                    }
                    else if(msg.obj.toString().equals("0")){
                        //Toast.makeText(msg.getContext(),"输入的号码或密码有误，请重新输入", Toast.LENGTH_SHORT).show();
                        number.setText("密码错误");
                        key.setText("");
                    }
                    else if(msg.obj.toString().equals("2")){
                        //Toast.makeText(msg.getContext(),"输入的号码或密码有误，请重新输入", Toast.LENGTH_SHORT).show();
                        number.setText("驱动错误");
                        key.setText("");
                    }
                    else if(msg.obj.toString().equals("3")){
                        //Toast.makeText(msg.getContext(),"输入的号码或密码有误，请重新输入", Toast.LENGTH_SHORT).show();
                        number.setText("mysql错误");
                        key.setText("");
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
        setContentView(R.layout.activity_login_in);
        Button buttonBuild=(Button)findViewById(R.id.buildButton);
        buttonBuild.setOnClickListener(new buttonListener());
         Button mainInterfaceBack=(Button)findViewById(R.id.interfaceMainBack);
         mainInterfaceBack.setOnClickListener(new buttonListener());
         Button forgetKeyButton=(Button)findViewById(R.id.forgetKey);
         forgetKeyButton.setOnClickListener(new buttonListener());
         sureButton=(Button)findViewById(R.id.sureButton);
         sureButton.setOnClickListener(this);
         number=(EditText) findViewById(R.id.numberEdit);
         key=(EditText)findViewById(R.id.keyEdit);
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sureButton) {
            sendRequestToServer();
            //测试代码
//                Intent intent=new Intent();
//                intent.putExtra("user",1);
//                intent.setClass(LoginIn.this,main_page.class);
//                LoginIn.this.startActivity(intent);
//                finish();
            //结束

        } else {
        }
    }
    private void sendRequestToServer() {
        a = number.getText().toString();
        b = key.getText().toString();
        if (a.equals("") || b.equals("")) {
            return;
        }
        HttpUtil.postHttpRequest(SERVER_ADDRESS, a, b,"login", new HttpCallbackListener() {
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
    class buttonListener implements View.OnClickListener {
        public void onClick(View v){
            if(v.getId()==R.id.buildButton){
                Intent intent=new Intent();
                intent.setClass(LoginIn.this,register.class);
                LoginIn.this.startActivity(intent);
            }
            else if(v.getId()==R.id.interfaceMainBack){
                finish();
            }
            else if(v.getId()==R.id.forgetKey){
                Intent intent=new Intent();
                intent.setClass(LoginIn.this,FindKey.class);
                LoginIn.this.startActivity(intent);
            }
//            else if(v.getId()==R.id.sureButton){
//                number=(EditText) findViewById(R.id.numberEdit);
//                String phoneNumber=number.getText().toString();
//                key=(EditText)findViewById(R.id.keyEdit);
//                String keyNumber=key.getText().toString();

//                if(phoneNumber.equals("")&&keyNumber.equals("")){
//                    Intent intent=new Intent();
//                    intent.putExtra("user",phoneNumber);
//                    intent.setClass(LoginIn.this,main_page.class);
//                    LoginIn.this.startActivity(intent);
//                    finish();
//                }
//                else{
//                    Toast.makeText(v.getContext(),"输入的号码或密码有误，请重新输入", Toast.LENGTH_SHORT).show();
//                    number.setText("");
//                    key.setText("");
//                }
//           }
        }
    }
}
