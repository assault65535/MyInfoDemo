package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Task;

import android.content.Context;
import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.IRecentChatsView;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Tnecesoc on 2016/12/28.
 */
public class SyncAndShowTask extends SyncMsgTask {

    IRecentChatsView msgView;

    public SyncAndShowTask(Context context, IRecentChatsView msgView) {
        super(context);
        this.msgView = msgView;
    }

    @Override
    protected void onPreExecute() {
        LinkedList<Message> latestMsg = localMsgHelper.getLatestMsgOfAll();
        Map<String, Profile> involvedProfiles = new Hashtable<>();

        setupProfilesToMemory(latestMsg, involvedProfiles);

        msgView.setupWithData(latestMsg, involvedProfiles, localMsgHelper.getUnreadMsgCntOfAll());
    }

    @Override
    protected void onPostExecute(Cond cond) {

        LinkedList<Message> latestMsg = localMsgHelper.getLatestMsgOfAll();
        Map<String, Profile> involvedProfiles = new Hashtable<>();


        setupProfilesToMemory(latestMsg, involvedProfiles);

        if (cond == Cond.SUCCESS) {
            msgView.setupWithData(latestMsg, involvedProfiles, localMsgHelper.getUnreadMsgCntOfAll());
        } else {
            msgView.showNetworkFailure();
        }

        super.onPostExecute(cond);
    }

    private void setupProfilesToMemory(List<Message> latestMsg, Map<String, Profile> memPer) {
        for (Message msg : latestMsg) {

            String other = msg.theManTalkWith(me);

            Profile profile = localContactsHelper.getProfileByUsername(other);

            if (profile != null) {
                memPer.put(other, profile);
            }
        }
    }

}
