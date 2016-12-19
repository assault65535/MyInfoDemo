package com.TomyYan.TheFirstRankProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by dell on 2016/8/2.
 */
public class register extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button buttonBackLogin=(Button)findViewById(R.id.registerBack);
        buttonBackLogin.setOnClickListener(new buttonSubmitListener());
    }
    class buttonSubmitListener implements View.OnClickListener{
        public void onClick(View v){
            if(v.getId()==R.id.registerBack){
                Intent intent=new Intent();
                intent.setClass(register.this,LoginIn.class);
                register.this.startActivity(intent);
                finish();
            }
        }
    }
}
