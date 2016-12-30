package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Task;

import android.content.Context;
import android.os.AsyncTask;
import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.LocalContactsHelper;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.ViewProfileHelper;

/**
 * Created by Tnecesoc on 2016/12/30.
 */
public class WhoIsTalkingWithMeTask extends AsyncTask<Message, Void, Message[]> {


    protected LocalContactsHelper localContactsHelper;
    protected String me;

    public WhoIsTalkingWithMeTask(Context context) {
        me = new SessionHelper(context).getSessionAttribute(SessionHelper.KEY_USERNAME);
        localContactsHelper = new LocalContactsHelper(context);
    }

    @Override
    protected Message[] doInBackground(Message... params) {


        for (Message msg : params) {

            Profile talkTo = localContactsHelper.getProfileByUsername(msg.theManTalkWith(me));

            if (talkTo == null) {

                talkTo = new ViewProfileHelper(msg.theManTalkWith(me)).viewProfile(null);

                if (talkTo != null) {
                    localContactsHelper.putProfile(talkTo, Profile.Category.UNKNOWN);
                }

            }
        }

        return params;
    }

    @Override
    protected void onPostExecute(Message[] messages) {
        localContactsHelper.close();
    }

    @Override
    protected void onCancelled() {
        localContactsHelper.close();
    }
}
