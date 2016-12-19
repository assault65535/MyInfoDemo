package com.TomyYan.TheFirstRankProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/9/3.
 */
public class my_circle extends Activity {
    private String user=null;
    private ImageButton personal=null;
    private ImageButton mainPage=null;
    private ImageButton circleSearch=null;
    private ButtonListener buttonListener=null;
    private Button build;
    private String[] names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_circle);
        //从上一个Activity中获取信息
        getInfoFromLastActivity();
        //获取我的圈信息
        GetMyCircleNames getName=new GetMyCircleNames();
        getName.sendActivity(this);
        getName.getMyCircleNames(user,"get_circle");//user,option:获取my_circle

//        names=new String[]{"洛天依","初音"};
//        SetListView listView=new SetListView();
//        listView.setListView(names,this,R.id.circleName);


//        listView.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent=new Intent();
//                intent.putExtra("user",user);
//                intent.putExtra("team",names[position]);
//                intent.setClass(my_circle.this,team_talk_window.class);
//                my_circle.this.startActivity(intent);
//            }
//        });


        setListener();
    }
    private boolean getInfoFromLastActivity(){
        Intent intent=getIntent();
        user=intent.getStringExtra("user");
        return true;
    }

    private boolean setListener(){
        buttonListener=new ButtonListener();
        mainPage=(ImageButton)findViewById(R.id.mainPage);
        personal=(ImageButton)findViewById(R.id.personal);
        circleSearch=(ImageButton)findViewById(R.id.circleSearch);
        build=(Button)findViewById(R.id.build);
        build.setOnClickListener(buttonListener);
        mainPage.setOnClickListener(buttonListener);
        personal.setOnClickListener(buttonListener);
        circleSearch.setOnClickListener(buttonListener);
        return true;
    }
    class ButtonListener implements View.OnClickListener{
        public void onClick(View v){
            if(v.getId()==R.id.mainPage){
                PageChange pageChange=new PageChange();
                pageChange.pageChange(user,my_circle.this,main_page.class);
                finish();
//                Intent intent=new Intent();
//                intent.putExtra("user",user);
//                intent.setClass(my_circle.this,main_page.class);
//                my_circle.this.startActivity(intent);
//                finish();
            }
            else if(v.getId()==R.id.personal){
                PageChange pageChange=new PageChange();
                pageChange.pageChange(user,my_circle.this,personal.class);
                finish();
//                Intent intent=new Intent();
//                intent.putExtra("user",user);
//                intent.setClass(my_circle.this,personal.class);
//                my_circle.this.startActivity(intent);
//                finish();
            }
            else if(v.getId()==R.id.circleSearch){
                PageChange pageChange=new PageChange();
                pageChange.pageChange(user,my_circle.this,circle_search.class);
//                Intent intent=new Intent();
//                intent.putExtra("user",user);
//                intent.setClass(my_circle.this,circle_search.class);
//                my_circle.this.startActivity(intent);
            }
            else if(v.getId()==R.id.build){
                PageChange pageChange=new PageChange();
                pageChange.pageChange(user,my_circle.this,circle_build.class);
                finish();
            }
        }
    }
}
