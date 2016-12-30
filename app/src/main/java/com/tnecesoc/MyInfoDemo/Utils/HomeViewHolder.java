package com.tnecesoc.MyInfoDemo.Utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import com.example.nero.zlylproject.GetPostTopics;
import com.example.nero.zlylproject.Posting;

/**
 * Created by Tnecesoc on 2016/12/30.
 */
public class HomeViewHolder {

    private int REQUSET_NEW_POST = 2;


    private String[] topics;
    private String post = null;
    private Intent intent=null;
    Button postingButton = null;
    Button screeningButton = null;


    public HomeViewHolder(Activity activity) {
        onCreate(activity, activity.getWindow().getDecorView());
    }

    public HomeViewHolder(Activity activity, View holder) {
        onCreate(activity, holder);
    }

    public void doNewPost(Activity holder) {
        Intent intent1 = new Intent();
        intent1.setClass(holder, Posting.class);
        holder.startActivityForResult(intent1, REQUSET_NEW_POST);
    }

    public void sync(Activity activity, final View holder) {
        onCreate(activity, holder);
    }

    protected void onCreate(Activity activity, final View holder) {
        getInfoFromLastActivity();
        GetPostTopics getTopic = new GetPostTopics();
        getTopic.sendView(holder);
        getTopic.sendActivity(activity);
        getTopic.getPostTopics(post, "get_post");
    }
    private boolean getInfoFromLastActivity(){
        this.post = "1";
        return true;
    }

}
