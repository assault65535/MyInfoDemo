package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.View.Utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.View.ContactsFragment;

import java.util.List;

/**
 * Created by Tnecesoc on 2016/12/12.
 */
public class ContactFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<ContactsFragment> mFragment;
    private Context mContext;

    public ContactFragmentPagerAdapter(FragmentManager fm, List<ContactsFragment> fragments, Context context) {
        super(fm);
        mFragment = fragments;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragment.get(position).getCategory(mContext);
    }

}
