package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Task;

import android.content.Context;
import android.os.AsyncTask;
import com.tnecesoc.MyInfoDemo.Entity.Container;
import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.LocalContactsHelper;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.ViewProfileHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Model.LocalMsgHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Model.MsgHelper;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.*;

/**
 * Created by Tnecesoc on 2016/12/25.
 */
public class SyncMsgTask extends AsyncTask<Void, Void, SyncMsgTask.Cond> {

    protected enum Cond {
        SUCCESS, NETWORK_FAILURE
    }

    protected LocalMsgHelper localMsgHelper;
    protected LocalContactsHelper localContactsHelper;
    List<Message> newMsg;
    String me;

    public SyncMsgTask(Context context) {
        localMsgHelper = new LocalMsgHelper(context);
        localContactsHelper = new LocalContactsHelper(context);

        me = localMsgHelper.getSelfUsername();
    }

    @Override
    protected Cond doInBackground(Void... params) {
        final Container<Cond> ans = new Container<>(Cond.SUCCESS);

        Date lastDate = localMsgHelper.getLastMsgDate();
        HttpUtil.HttpErrorListener listener = new HttpUtil.HttpErrorListener() {
            @Override
            public void onError(Exception e) {
                ans.setValue(Cond.NETWORK_FAILURE);
            }
        };

        if (lastDate.getTime() == Long.MIN_VALUE) {
            newMsg = new MsgHelper(localMsgHelper.getSelfUsername()).getMsgList(listener);
        } else {
            newMsg = new MsgHelper(localMsgHelper.getSelfUsername(), localMsgHelper.getLastMsgDate()).getMsgList(listener);
        }

        for (Message msg : newMsg) {
            String other = msg.theManTalkWith(me);
            Profile otherProfile = localContactsHelper.getProfileByUsername(other);
            if (otherProfile == null) {
                otherProfile = new ViewProfileHelper(other).viewProfile(null);
            }

            if (otherProfile == null) {
                otherProfile = new Profile();
                otherProfile.setUsername(other);
            }

            localContactsHelper.putProfile(otherProfile, Profile.Category.UNKNOWN);
        }

        localMsgHelper.addAllMsg(newMsg);

        return ans.getValue();
    }

    @Override
    protected void onPostExecute(Cond cond) {
        localMsgHelper.close();
        localContactsHelper.close();
    }
}
