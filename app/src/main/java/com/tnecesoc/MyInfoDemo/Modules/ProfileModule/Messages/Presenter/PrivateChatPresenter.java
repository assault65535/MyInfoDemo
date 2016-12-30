package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Presenter;

import android.content.Context;
import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.LocalContactsHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.IPrivateChatView;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Model.LocalMsgHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Task.ShowNewWordTask;

import java.util.List;

/**
 * Created by Tnecesoc on 2016/12/28.
 */
public class PrivateChatPresenter {

    private IPrivateChatView view;

    private Profile who;

    public PrivateChatPresenter(IPrivateChatView view) {
        this.view = view;
    }

    public void performSetupMessage(Context context, Profile who) {

        this.who = who;

        LocalMsgHelper localMsgHelper = new LocalMsgHelper(context);
        List<Message> msgList = localMsgHelper.getMsgByContact(who.getUsername());
        view.setupMsg(msgList);
        localMsgHelper.close();

        LocalContactsHelper localContactsHelper = new LocalContactsHelper(context);
        if (localContactsHelper.getProfileByUsername(who.getUsername()) == null) {
            localContactsHelper.putProfile(who, Profile.Category.UNKNOWN);
        }
        localContactsHelper.close();
    }

    public void performReadMessage(Context context) {

        LocalMsgHelper localMsgHelper = new LocalMsgHelper(context);
        localMsgHelper.markAllUnreadMsgOf(who.getUsername());
        localMsgHelper.close();

    }

    public void performAddMessage(Context context, Message msg) {

        new ShowNewWordTask(context, view).execute(msg);

    }

}
