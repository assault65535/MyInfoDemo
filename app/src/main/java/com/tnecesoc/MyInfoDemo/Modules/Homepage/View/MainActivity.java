package com.tnecesoc.MyInfoDemo.Modules.Homepage.View;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.R;
import com.tnecesoc.MyInfoDemo.Utils.*;
import com.tnecesoc.MyInfoDemo.Widget.Adapter.ViewPagerAdapter;
import com.tnecesoc.MyInfoDemo.Widget.Util.DisplayUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, InstantMessageService.MsgObserver {

    private int REQUEST_SEARCH_CONTACT = 1;
    private int REQUEST_NEW_POST = 2;

    private ViewPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;


    private HomeViewHolder mHomeViewHolder;
    private NavigateViewHolder mNavigateViewHolder;
    private MyCircleViewHolder mMyCircleViewHolder;

    private List<View> mTabViews;
    private List<ImageView> mIcons;
    private List<TextView> mTextViews;

    private float offset;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            InstantMessageService.Binder binder = (InstantMessageService.Binder) service;
            binder.setMsgObserver(MainActivity.this);
            binder.setNeedToNotifyNewMsg(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onNewMsg(Message msg) {

        new SyncMsgCntTask(this, mNavigateViewHolder).execute();

    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = (TabLayout) findViewById(R.id.layout_main_bottom_nav);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_main);

        initPages();

        initTabs();

        Intent startIntent = new Intent(this, InstantMessageService.class);
        startService(startIntent);
    }

    @Override
    public void onClick(View v) {
        mNavigateViewHolder.onClick(this, v);
        mMyCircleViewHolder.onClick(this, v);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNavigateViewHolder.onResume(mPagerAdapter.getItemByTitle(getString(R.string.tab_me)));

        Intent bindIntend = new Intent(this, InstantMessageService.class);
        bindService(bindIntend, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SEARCH_CONTACT && resultCode == SearchContactActivity.RESULT_CONTACT_INTERESTED) {
            mNavigateViewHolder.onCreate(mPagerAdapter.getItemByTitle(getString(R.string.tab_me)));
        } else if (requestCode == REQUEST_NEW_POST && resultCode == 25252) {
            mHomeViewHolder.sync(this, mPagerAdapter.getItemByTitle(getString(R.string.tab_home)));
        }
    }

    @Override
    protected void onStop() {

        unbindService(serviceConnection);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Intent stopIntent = new Intent(this, InstantMessageService.class);
        stopService(stopIntent);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.option_main_new_neighbor_chat:
                mMyCircleViewHolder.doCircleSearch(this);
                break;
            case R.id.option_main_new_contact:
                startActivityForResult(new Intent(MainActivity.this, SearchContactActivity.class), REQUEST_SEARCH_CONTACT);
                break;
            case R.id.option_main_new_post:
                mHomeViewHolder.doNewPost(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @SuppressLint("InflateParams")
    private void initPages() {

        final View homePageView = getLayoutInflater().inflate(R.layout.piece_home, null);
        mHomeViewHolder = new HomeViewHolder(this, homePageView);

        final View chatsPageView = getLayoutInflater().inflate(R.layout.piece_my_circle, null);
        mMyCircleViewHolder = new MyCircleViewHolder(this, chatsPageView, this);

        final View mePageView = getLayoutInflater().inflate(R.layout.activity_navigate, null);
        mNavigateViewHolder = new NavigateViewHolder(mePageView, this);

        mPagerAdapter = new ViewPagerAdapter(new LinkedHashMap<String, View>() {{
            put(getString(R.string.tab_home), homePageView);
            put(getString(R.string.tab_chats), chatsPageView);
            put(getString(R.string.tab_me), mePageView);
        }});

        mViewPager.setAdapter(mPagerAdapter);

    }

    @SuppressLint("InflateParams")
    @SuppressWarnings("ConstantConditions")
    private void initTabs() {

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(Color.WHITE);

        mIcons = new ArrayList<>();
        mTextViews = new ArrayList<>();
        mTabViews = new ArrayList<>();

        mTabViews.add(getLayoutInflater().inflate(R.layout.tab_main_home, null));
        mTabViews.add(getLayoutInflater().inflate(R.layout.tab_main_chat, null));
        mTabViews.add(getLayoutInflater().inflate(R.layout.tab_main_me, null));

        for (int i = 0; i < 3; i++) {
            mIcons.add((ImageView) mTabViews.get(i).findViewById(R.id.img_fill_tab_main));
            mTextViews.add((TextView) mTabViews.get(i).findViewById(R.id.lbl_tab_main));
            mTextViews.get(i).setText(mTabLayout.getTabAt(i).getText());
            mTabLayout.getTabAt(i).setCustomView(mTabViews.get(i));
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (positionOffset > 0) {


                    offset = (float) DisplayUtil.MyMath.clamp(positionOffset, 0.0, 0.5);
                    offset = 1 - 2 * offset;

                    mIcons.get(position).setAlpha(offset);
                    mTextViews.get(position).setTextColor(DisplayUtil.getHalftonePrimary(offset));

                    if (position + 1 < mTabLayout.getTabCount()) {

                        offset = (float) DisplayUtil.MyMath.clamp(positionOffset, 0.5, 1.0);
                        offset = 2 * offset - 1;

                        mIcons.get(position + 1).setAlpha(offset);
                        mTextViews.get(position + 1).setTextColor(DisplayUtil.getHalftonePrimary(positionOffset));
                    }

                }

            }

            @Override
            public void onPageSelected(int position) {
                mIcons.get(position).setAlpha(1.0f);
                mTextViews.get(position).setTextColor(DisplayUtil.getHalftonePrimary(1.0f));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mIcons.get(mTabLayout.getSelectedTabPosition()).setAlpha(1.0f);
        mTextViews.get(mTabLayout.getSelectedTabPosition()).setTextColor(DisplayUtil.getHalftonePrimary(1.0f));
    }
}
