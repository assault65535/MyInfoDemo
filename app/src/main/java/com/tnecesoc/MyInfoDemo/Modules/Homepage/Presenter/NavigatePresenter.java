package com.tnecesoc.MyInfoDemo.Modules.Homepage.Presenter;

import android.content.Context;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;
import com.tnecesoc.MyInfoDemo.Modules.Homepage.Tasks.FetchContactsCountTask;
import com.tnecesoc.MyInfoDemo.Modules.Homepage.View.IMainPageView;

/**
 * Created by Tnecesoc on 2016/11/10.
 */
public class NavigatePresenter {

    IMainPageView view;

    public NavigatePresenter(IMainPageView navigateActivity) {
        this.view = navigateActivity;
    }

    public void performFetchUserInfo(Context context) {
        SessionHelper session = new SessionHelper(context);
        view.showUserInfo(session.getEntireProfile());

        performFetchUserCommunicationInfo(context);
    }

    public void performFetchUserCommunicationInfo(Context context) {
        String username = new SessionHelper(context).getSessionAttribute(SessionHelper.KEY_USERNAME);
        new FetchContactsCountTask(view, context).execute(username);
    }

}
