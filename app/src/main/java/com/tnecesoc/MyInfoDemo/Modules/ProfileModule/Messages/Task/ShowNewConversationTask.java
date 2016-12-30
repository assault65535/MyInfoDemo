package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Task;

import android.content.Context;
import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.IRecentChatsView;

/**
 * Created by Tnecesoc on 2016/12/30.
 */
public class ShowNewConversationTask extends WhoIsTalkingWithMeTask {

    IRecentChatsView recentChatsView;

    public ShowNewConversationTask(Context context, IRecentChatsView recentChatsView) {
        super(context);
        this.recentChatsView = recentChatsView;
    }

    @Override
    protected void onPostExecute(Message[] messages) {

        for (Message msg : messages) {
            recentChatsView.showNewMsg(msg, localContactsHelper.getProfileByUsername(msg.theManTalkWith(me)));
        }

        super.onPostExecute(messages);
    }
}
