package com.tnecesoc.MyInfoDemo.Modules.Homepage.View;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tnecesoc.MyInfoDemo.R;
import com.tnecesoc.MyInfoDemo.Utils.MyCircleFrame;
import com.tnecesoc.MyInfoDemo.Utils.NavigateFrame;
import com.tnecesoc.MyInfoDemo.Widget.Adapter.ViewPagerAdapter;
import com.tnecesoc.MyInfoDemo.Widget.Util.DisplayUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private NavigateFrame mNavigateFrame;
    private MyCircleFrame mMyCircleFrame;

    private List<View> mTabViews;
    private List<ImageView> mIcons;
    private List<TextView> mTextViews;

    private float offset;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = (TabLayout) findViewById(R.id.layout_main_bottom_nav);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_main);

        initPages();

        initTabs();

    }

    @Override
    public void onClick(View v) {
        mNavigateFrame.onClick(this, v);
        mMyCircleFrame.onClick(this, v);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.option_main_new_neighbor_chat) {
            mMyCircleFrame.doCircleSearch(this);
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

        final View homePageView = new TextView(this);
        ((TextView) homePageView).setText("首页模块入口");
        ((TextView) homePageView).setGravity(Gravity.CENTER);

        final View chatsPageView = getLayoutInflater().inflate(R.layout.piece_my_circle, null);
        mMyCircleFrame = new MyCircleFrame(this, chatsPageView, this);

        final View mePageView = getLayoutInflater().inflate(R.layout.activity_navigate, null);
        mNavigateFrame = new NavigateFrame(mePageView, this);

        mPagerAdapter = new ViewPagerAdapter(new LinkedHashMap<String, View>() {{
            put("Home", homePageView);
            put("Chats", chatsPageView);
            put("Me", mePageView);
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

        mIcons.get(0).setAlpha(1.0f);
        mTextViews.get(0).setTextColor(DisplayUtil.getHalftonePrimary(1.0f));
    }
}
