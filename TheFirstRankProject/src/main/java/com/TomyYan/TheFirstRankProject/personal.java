package com.TomyYan.TheFirstRankProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by dell on 2016/9/4.
 */
public class personal extends Activity {
    private String user=null;
    private ImageButton mainPage=null;
    private ImageButton my_circle=null;
    private ImageButton personal=null;
    private ButtonListener buttonListener=null;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal);
        Intent intent=getIntent();
        user=intent.getStringExtra("user");
        buttonListener=new ButtonListener();
        mainPage=(ImageButton)findViewById(R.id.mainPage);
        my_circle=(ImageButton)findViewById(R.id.myCircle);
        personal=(ImageButton)findViewById(R.id.personal);
        mainPage.setOnClickListener(buttonListener);
        my_circle.setOnClickListener(buttonListener);
    }
    class ButtonListener implements View.OnClickListener{
        public void onClick(View v){
            if(v.getId()==R.id.mainPage){
                PageChange pageChange=new PageChange();
                pageChange.pageChange(user,personal.this,main_page.class);
                finish();
//                Intent intent=new Intent();
//                intent.putExtra("user",user);
//                intent.setClass(personal.this,main_page.class);
//                personal.this.startActivity(intent);
//                finish();
            }
            else if(v.getId()==R.id.myCircle){
                PageChange pageChange=new PageChange();
                pageChange.pageChange(user,personal.this,my_circle.class);
                finish();
//                Intent intent=new Intent();
//                intent.putExtra("user",user);
//                intent.setClass(personal.this,my_circle.class);
//                personal.this.startActivity(intent);
//                finish();
            }
        }
    }
}
