package com.tnecesoc.MyInfoDemo.Modules.Index.Presenter;

import com.tnecesoc.MyInfoDemo.GlobalModel.SessionHelper;
import com.tnecesoc.MyInfoDemo.Modules.Index.View.ILoadingView;
import com.tnecesoc.MyInfoDemo.Modules.Index.View.IndexActivity;

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

    public void performIndexing() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                doIndex();
            }
        }, 2000);

    }

    private void doIndex() {
        if (helper.isOnline()) {
            view.showResume();
        } else {
            view.requestLogin();
        }
    }


}
