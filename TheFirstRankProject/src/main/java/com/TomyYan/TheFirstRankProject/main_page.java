package com.TomyYan.TheFirstRankProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by dell on 2016/9/3.
 */
public class main_page extends Activity{
    private String user=null;
    private ImageButton myCircleButton=null;
    private ImageButton personalButton=null;
    private ButtonListener buttonListener=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        Intent intent=getIntent();
        user=intent.getStringExtra("user");
        buttonListener=new ButtonListener();
        myCircleButton=(ImageButton)findViewById(R.id.myCircle);
        personalButton=(ImageButton)findViewById(R.id.personal);
        myCircleButton.setOnClickListener(buttonListener);
        personalButton.setOnClickListener(buttonListener);
    }
    class ButtonListener implements View.OnClickListener{
        public void onClick(View v){
            if(v.getId()==R.id.myCircle){
                PageChange pageChange=new PageChange();
                pageChange.pageChange(user,main_page.this,my_circle.class);
                finish();
//                Intent intent=new Intent();
//                intent.putExtra("user",user);
//                intent.setClass(main_page.this,my_circle.class);
//                main_page.this.startActivity(intent);
//                finish();
            }
            else if(v.getId()==R.id.personal){
                PageChange pageChange=new PageChange();
                pageChange.pageChange(user,main_page.this,personal.class);
                finish();
//                Intent intent=new Intent();
//                intent.putExtra("user",user);
//                intent.setClass(main_page.this,personal.class);
//                main_page.this.startActivity(intent);
//                finish();
            }
        }
    }
}
