package com.TomyYan.TheFirstRankProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;

/**
 * Created by dell on 2016/9/11.
 */
public class have_build extends Activity{
    private String user=null;
    private ImageButton back=null;
    private ButtonListener buttonListener=null;
    private Intent intent=null;
    private Intent intentP=null;
    private String[] names;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.have_build);
        intent=getIntent();
        intentP=new Intent();
        user=intent.getStringExtra("user");
        buttonListener=new ButtonListener();
        back=(ImageButton)findViewById(R.id.haveBuildBack);
        back.setOnClickListener(buttonListener);
        GetMyCircleNames getOtherName=new GetMyCircleNames();
        getOtherName.sendActivity(this);
        getOtherName.getMyCircleNames(user,"get_circle_have_build");

//        //列出所加入的邻里圈
//        GetMyCircleNames getName=new GetMyCircleNames();
//        //names=getName.getMyCircleNames(user,"have_build");//3:option:获取have_build信息
//        SetListView listView=new SetListView();
//        listView.setListView(names,this,R.id.have_build_names);
//        listView.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent=new Intent();
//                intent.putExtra("user",user);
//                intent.putExtra("team",names[position]);
//                intent.setClass(have_build.this,team_talk_window.class);
//                have_build.this.startActivity(intent);
//            }
//        });


    }
    private class ButtonListener implements View.OnClickListener{
        public void onClick(View v){
            int i = v.getId();
            if (i == R.id.haveBuildBack) {//                    intentP.putExtra("user",user);
//                    intentP.setClass(have_build.this,circle_build.class);
//                    have_build.this.startActivity(intentP);
                finish();

            } else {
            }
        }
    }
}
