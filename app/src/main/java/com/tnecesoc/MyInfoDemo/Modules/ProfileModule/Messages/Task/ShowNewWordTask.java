package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Task;

import android.content.Context;
import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.IPrivateChatView;

/**
 * Created by Tnecesoc on 2016/12/30.
 */
public class ShowNewWordTask extends WhoIsTalkingWithMeTask {

    private IPrivateChatView privateChatView;

    public ShowNewWordTask(Context context, IPrivateChatView privateChatView) {
        super(context);
        this.privateChatView = privateChatView;
    }

    @Override
    protected void onPostExecute(Message[] messages) {

        for (Message msg : messages) {
            privateChatView.showNewMsg(msg);
        }

        super.onPostExecute(messages);
    }
}
