package com.tnecesoc.MyInfoDemo.Utils;

import android.content.Context;
import com.tnecesoc.MyInfoDemo.Modules.Homepage.View.INavView;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Task.SyncMsgTask;

/**
 * Created by Tnecesoc on 2016/12/28.
 */
public class SyncMsgCntTask extends SyncMsgTask {

    INavView view;

    public SyncMsgCntTask(Context context, INavView view) {
        super(context);
        this.view = view;
    }

    @Override
    protected void onPostExecute(Cond cond) {

        view.showCurrentMessages(localMsgHelper.getUnreadMsgTotalCnt(), false);

        super.onPostExecute(cond);
    }
}
