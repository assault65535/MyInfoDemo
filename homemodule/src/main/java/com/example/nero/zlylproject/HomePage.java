package com.example.nero.zlylproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by nilu on 2016/11/4.
 */
public class HomePage extends Activity implements View.OnClickListener {

    private String[] topics;
    private String post = null;
    private Intent intent=null;
    Button postingButton = null;
    Button screeningButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        getInfoFromLastActivity();
        GetPostTopics getTopic = new GetPostTopics();
        getTopic.sendActivity(this);
        getTopic.getPostTopics(post, "get_post");


        postingButton = (Button)findViewById(R.id.send);
        postingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Intent intent1 = new Intent();
                intent1.setClass(HomePage.this,Posting.class);
                startActivity(intent1);
                finish();
            }
        });

        screeningButton = (Button)findViewById(R.id.post_search);
        screeningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                Intent intent2 = new Intent();
                intent2.setClass(HomePage.this,Screening.class);
                startActivity(intent2);
                finish();
            }
        });
    }
    private boolean getInfoFromLastActivity(){
        Intent intent=getIntent();
        post=intent.getStringExtra("post");
        return true;
    }

    @Override
    public void onClick(View view) {

    }
}
