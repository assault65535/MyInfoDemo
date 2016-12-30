package com.TomyYan.TheFirstRankProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by dell on 2016/8/3.
 */
public class FindKey extends Activity {
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findkey);
        Button findKeyBackButton=(Button)findViewById(R.id.findKeyBack);
        findKeyBackButton.setOnClickListener(new findKeyInterfaceListener());
    }
    class findKeyInterfaceListener implements View.OnClickListener{
        public void onClick(View v){
            if(v.getId()==R.id.findKeyBack){
                Intent intent=new Intent();
                intent.setClass(FindKey.this, LoginIn.class);
                FindKey.this.startActivity(intent);
                finish();
            }
        }
    }
}
