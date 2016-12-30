package com.tnecesoc.MyInfoDemo.Modules.SplashScreen.Presenter;

import android.content.Context;
import android.os.AsyncTask;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Task.SyncMsgTask;
import com.tnecesoc.MyInfoDemo.Modules.SplashScreen.View.ILoadingView;
import com.tnecesoc.MyInfoDemo.Modules.SplashScreen.View.IndexActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public class IndexPresenter{

    private ILoadingView view;
    private SessionHelper helper;

    public IndexPresenter(IndexActivity activity) {
        helper = new SessionHelper(activity);
        view = activity;
    }

    public void performIndexing(final Context context) {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                doIndex(context);
            }
        }, 2000);

    }

    private void doIndex(Context context) {
        if (helper.isOnline()) {

            view.showResume();

        } else {

            view.requestLogin();

        }
    }


}
