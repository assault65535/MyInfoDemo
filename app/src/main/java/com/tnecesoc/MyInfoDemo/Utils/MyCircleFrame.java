package com.tnecesoc.MyInfoDemo.Utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.TomyYan.TheFirstRankProject.*;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;

import java.util.List;

/**
 * Created by Tnecesoc on 2016/12/17.
 */
public class MyCircleFrame {

    private String user = null;
    private Button build;

    public MyCircleFrame(Activity holder, View viewHolder, View.OnClickListener listener) {
        onCreate(holder, viewHolder, listener);
    }

    public void onCreate(Activity holder, View viewHolder, View.OnClickListener listener) {
        //从上一个Activity中获取信息
        getInfoFromLastActivity(holder);
        //获取我的圈信息
        GetMyCircleNames getName = new GetMyCircleNames();
        getName.sendActivity(holder);
        getName.getMyCircleNames(user,"get_circle");//user,option:获取my_circle

        setListener(viewHolder, listener);
    }


    private void getInfoFromLastActivity(Activity holder){
        user = new SessionHelper(holder).getSessionAttribute(SessionHelper.KEY_USERNAME);
    }

    private void setListener(View holder, View.OnClickListener buttonListener) {
        build = (Button) holder.findViewById(R.id.build);
        build.setOnClickListener(buttonListener);
    }

    public void doCircleSearch(Activity holder) {
        PageChange pageChange=new PageChange();
        pageChange.pageChange(user, holder, circle_search.class);
    }


    public void onClick(Activity holder, View v){
        if(v.getId()==R.id.build){
            PageChange pageChange=new PageChange();
            pageChange.pageChange(user,holder,circle_build.class);
        }
    }

}
