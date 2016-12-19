package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.MenuItem;
import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;
import com.tnecesoc.MyInfoDemo.GlobalView.ShowProfileViewImpl;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.clear();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager_contacts_main);

        fragments = new ArrayList<ContactsFragment>();
        fragments.add(new ContactsFragment().setCategory(ProfileBean.Category.FRIEND));
        fragments.add(new ContactsFragment().setCategory(ProfileBean.Category.FOLLOW));
        fragments.add(new ContactsFragment().setCategory(ProfileBean.Category.FOLLOWER));

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

}
