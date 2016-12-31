package com.example.nero.zlylproject;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nilu on 2016/11/9.
 */
public class SetListView {
    protected ListView list;

    protected boolean setListView(String[] names,/*String[] teamID, */Activity activity, int id) {
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("post", names[i]);
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(activity, listItems, R.layout.link_post,
                new String[]{"post"}, new int[]{R.id.post_name});
        list = (ListView) activity.findViewById(id);
        list.setAdapter(simpleAdapter);
        return true;
    }

    protected boolean setListView(String[] names,/*String[] teamID, */View holder, int id) {
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("post", names[i]);
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(holder.getContext(), listItems, R.layout.link_post,
                new String[]{"post"}, new int[]{R.id.post_name});
        list = (ListView) holder.findViewById(id);
        list.setAdapter(simpleAdapter);
        return true;
    }

}
