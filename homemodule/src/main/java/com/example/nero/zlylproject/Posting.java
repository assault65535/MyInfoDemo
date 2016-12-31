package com.example.nero.zlylproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * Created by nilu on 2016/9/8.
 */
public class Posting extends Activity {
    private ImageButton buildBack=null;
    private Button buildSure=null;
    private EditText buildName=null;
    private EditText buildContent=null;
    private String community=null;
    private String buildPostName=null;
    private String buildPostContent=null;
    private String NameAndContent=null;
    private Intent intent=null;
    private ButtonListener buttonListener=null;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posting);
        intent=getIntent();
        community=intent.getStringExtra("community");
        buttonListener=new ButtonListener();

        buildBack=(ImageButton)findViewById(R.id.back);
        buildSure=(Button)findViewById(R.id.send);
        buildName=(EditText)findViewById(R.id.et1);
        buildContent=(EditText)findViewById(R.id.et2);
        buildBack.setOnClickListener(buttonListener);
        buildSure.setOnClickListener(buttonListener);
    }
    private class ButtonListener implements View.OnClickListener{
        public void onClick(View v){
            int i = v.getId();
            if (i == R.id.back) {

                finish();

            } else if (i == R.id.send) {
                buildPostName = buildName.getText().toString();
                buildPostContent = buildContent.getText().toString();
                if (buildPostName.equals("") && buildPostContent.equals("")) {
                    return;
                } else {
                    NameAndContent = buildPostName + "  !-!  " + buildPostContent;
                    //System.out.println(NameAndIntroduce);
                    NewPost newPost = new NewPost();
                    newPost.sendActivity(Posting.this);
                    newPost.sendBuildInfo(community, NameAndContent, "send_post");
                    //System.out.println(NameAndIntroduce);
                }

            }
        }
    }
}
