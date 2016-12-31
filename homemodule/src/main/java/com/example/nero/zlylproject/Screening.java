package com.example.nero.zlylproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


/**
 * Created by nilu on 2016/9/8.
 */
public class Screening extends Activity {

    private String topic;

    ImageButton backButton = null;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.post_search);
        Intent intent = getIntent();
        topic=intent.getStringExtra("topic");
        GetPostTopics getPostTopics = new GetPostTopics();
        getPostTopics.sendActivity(this);
        getPostTopics.getPostTopics(topic,"get-other-post");


        backButton = (ImageButton)findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Intent intent = new Intent();
                intent.setClass(Screening.this,HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
