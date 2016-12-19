package com.TomyYan.TheFirstRankProject;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/9/28.
 */
public class SetListView {

    protected ListView list;

    protected boolean setListView(String[] names,/*String[] teamID, */Activity activity,int id){
        List<Map<String,Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i=0;i<names.length;i++){
            Map<String,Object> listItem=new HashMap<String,Object>();
            listItem.put("name",names[i]);
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter= new SimpleAdapter(activity,listItems,R.layout.link_people,
                new String[]{"name"},new int[]{R.id.name});
        list=(ListView)activity.findViewById(id);
        list.setAdapter(simpleAdapter);
        return true;
    }
    protected boolean setHaveBuildListView(String[] names,/*String[] teamID, */Activity activity,int id){
        List<Map<String,Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i=0;i<names.length;i++){
            Map<String,Object> listItem=new HashMap<String,Object>();
            listItem.put("name",names[i]);
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter= new SimpleAdapter(activity,listItems,R.layout.search_have_build_circle,
                new String[]{"name"},new int[]{R.id.name});
        list=(ListView)activity.findViewById(id);
        list.setAdapter(simpleAdapter);
        return true;
    }


}
