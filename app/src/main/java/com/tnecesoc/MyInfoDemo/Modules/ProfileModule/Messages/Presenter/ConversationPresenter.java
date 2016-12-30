package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Presenter;

import android.content.Context;
import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.IRecentChatsView;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Model.LocalMsgHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Task.ShowNewConversationTask;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Task.SyncAndShowTask;

/**
 * Created by Tnecesoc on 2016/12/25.
 */
public class ConversationPresenter {

    private IRecentChatsView mView;

    public ConversationPresenter(IRecentChatsView mView) {

        this.mView = mView;

    }

    public void performSyncMsg(Context context) {

        new SyncAndShowTask(context, mView).execute();

    }

    public void performMarkAllMsgRead(Context context, String other) {

        LocalMsgHelper localMsgHelper = new LocalMsgHelper(context);

        localMsgHelper.markAllUnreadMsgOf(other);

        localMsgHelper.close();

    }

    public void performAddNewMsg(final Context context, final Message msg) {

        new ShowNewConversationTask(context, mView).execute(msg);

    }


}
