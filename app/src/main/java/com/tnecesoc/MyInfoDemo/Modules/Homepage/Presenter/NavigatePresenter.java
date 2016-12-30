package com.tnecesoc.MyInfoDemo.Modules.Homepage.Presenter;

import android.content.Context;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;
import com.tnecesoc.MyInfoDemo.Modules.Homepage.Tasks.SyncContactsCountTask;
import com.tnecesoc.MyInfoDemo.Modules.Homepage.View.INavView;
import com.tnecesoc.MyInfoDemo.Utils.SyncMsgCntTask;

/**
 * Created by Tnecesoc on 2016/11/10.
 */
public class NavigatePresenter {

    INavView view;

    public NavigatePresenter(INavView navigateActivity) {
        this.view = navigateActivity;
    }

    public void performFetchUserInfo(Context context) {
        SessionHelper session = new SessionHelper(context);
        view.showUserInfo(session.getEntireProfile());

        performFetchUserCommunicationInfo(context);
    }

    public void performFetchUserCommunicationInfo(Context context) {
        String username = new SessionHelper(context).getSessionAttribute(SessionHelper.KEY_USERNAME);
        new SyncContactsCountTask(view, context).execute(username);
        new SyncMsgCntTask(context, view).execute();
    }

}
