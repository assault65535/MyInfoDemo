package com.TomyYan.TheFirstRankProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.TomyYan.GlobalModel.Local.SessionHelper;
import com.TomyYan.TheFirstRankProject.R;

/**
 * Created by dell on 2016/9/10.
 */
public class circle_build extends Activity{
    private ImageButton haveBuild=null;
    private ImageButton buildBack=null;
    private Button buildSure=null;
    private EditText buildName=null;
    private EditText buildIntroduce=null;
    private String user=null;
    private String buildCircleName=null;
    private String buildCircleIntroduce=null;
    private String NameAndIntroduce=null;
    private Intent intent=null;
    private Intent intentP=null;
    private ButtonListener buttonListener=null;
    private SessionHelper sessionHelper;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_build);
//        intent=getIntent();
//        user=intent.getStringExtra("user");
        sessionHelper = new SessionHelper( circle_build.this);
        user=sessionHelper.getSessionAttribute(SessionHelper.KEY_PHONE);

        intentP=new Intent();
        buttonListener=new ButtonListener();
        haveBuild=(ImageButton)findViewById(R.id.haveBuild);
        buildBack=(ImageButton)findViewById(R.id.build_back);
        buildSure=(Button)findViewById(R.id.build_sure);
        buildName=(EditText)findViewById(R.id.build_name);
        buildIntroduce=(EditText)findViewById(R.id.build_introduce);
        buildBack.setOnClickListener(buttonListener);
        haveBuild.setOnClickListener(buttonListener);
        buildSure.setOnClickListener(buttonListener);
    }
    private class ButtonListener implements View.OnClickListener{
        public void onClick(View v){
            int i = v.getId();
            if (i == R.id.haveBuild) {
                intentP.putExtra("user", user);
                intentP.setClass(circle_build.this, have_build.class);
                circle_build.this.startActivity(intentP);

            } else if (i == R.id.build_back) {
                Intent intent = new Intent();
                intent.putExtra("user", user);
//                intent.setClass(circle_build.this, my_circle.class);
//                circle_build.this.startActivity(intent);
                finish();

            } else if (i == R.id.build_sure) {
                buildCircleName = buildName.getText().toString();
                buildCircleIntroduce = buildIntroduce.getText().toString();
                if (buildCircleName.equals("") && buildCircleIntroduce.equals("")) {
                    return;
                } else {
                    NameAndIntroduce = buildCircleName + "  !-!  " + buildCircleIntroduce;
                    //System.out.println(NameAndIntroduce);
                    BuildCircle buildCircle = new BuildCircle();
                    buildCircle.sendActivity(circle_build.this);
                    buildCircle.sendBuildInfo(user, NameAndIntroduce, "build_circle");
                    //System.out.println(NameAndIntroduce);
                }

            }
        }
    }
}
