package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.View.Utils.ContactFragmentPagerAdapter;
import com.tnecesoc.MyInfoDemo.R;

import java.util.ArrayList;
import java.util.List;

import static com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.View.ContactsFragment.REQUEST_SYNC;

public class ContactsMainActivity extends AppCompatActivity {

    private ContactFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private List<ContactsFragment> fragments;

    private ContactBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.clear();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_main);

        broadcastReceiver = new ContactBroadcastReceiver();
        registerReceiver(broadcastReceiver, new IntentFilter(REQUEST_SYNC));

        viewPager = (ViewPager) findViewById(R.id.viewpager_contacts_main);

        fragments = new ArrayList<ContactsFragment>();
        fragments.add(new ContactsFragment().setCategory(Profile.Category.FRIEND));
        fragments.add(new ContactsFragment().setCategory(Profile.Category.FOLLOW));
        fragments.add(new ContactsFragment().setCategory(Profile.Category.FOLLOWER));

        fragments.get(0);

        FragmentManager fm = getSupportFragmentManager();

        pagerAdapter = new ContactFragmentPagerAdapter(fm, fragments, this);

        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.layout_tab_contacts_main);

        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private class ContactBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            for (ContactsFragment f : fragments) {
                if (f.getContext() != null) {
                    f.sync();
                } else {
                    f.onAttach(ContactsMainActivity.this);
                }
            }
        }
    }

}
