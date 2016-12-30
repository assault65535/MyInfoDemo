package com.tnecesoc.MyInfoDemo.Widget.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.tnecesoc.MyInfoDemo.BuildConfig.DEBUG;

/**
 * Created by Tnecesoc on 2016/12/16.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private List<String> mTitles;
    private List<View> mViews;

    public ViewPagerAdapter(Map<String, View> mTitleAndViews) {
        mTitles = new ArrayList<>(mTitleAndViews.keySet());
        mViews = new ArrayList<>(mTitleAndViews.values());
    }

    public View getItemByTitle(String title) {
        return mViews.get(mTitles.indexOf(title));
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(mViews.get(position));

        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
