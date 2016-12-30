package com.tnecesoc.MyInfoDemo.Utils;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Tnecesoc on 2016/11/22.
 */
public class NeighborsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
