package com.tnecesoc.MyInfoDemo.Utils;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionBarContextView;
import android.view.MenuItem;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.lang.reflect.Method;

/**
 * Created by Tnecesoc on 2016/11/22.
 */
public class ProfileApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
