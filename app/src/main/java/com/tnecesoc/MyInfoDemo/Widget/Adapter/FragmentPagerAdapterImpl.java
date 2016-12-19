package com.tnecesoc.MyInfoDemo.Widget.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Tnecesoc on 2016/12/16.
 */
public class FragmentPagerAdapterImpl extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public FragmentPagerAdapterImpl(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
