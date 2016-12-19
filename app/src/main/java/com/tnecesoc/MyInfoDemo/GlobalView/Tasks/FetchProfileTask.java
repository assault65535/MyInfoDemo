package com.tnecesoc.MyInfoDemo.GlobalView.Tasks;

import android.os.AsyncTask;
import com.tnecesoc.MyInfoDemo.Bean.Container;
import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.LocalContactsHelper;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.ViewProfileHelper;
import com.tnecesoc.MyInfoDemo.GlobalView.Interfaces.IShowProfileView;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Model.ViewFollowUserHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Model.ViewFollowerHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Model.ViewFriendListHelper;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

/**
 * Created by Tnecesoc on 2016/12/15.
 */
public class FetchProfileTask extends AsyncTask<String, Void, FetchProfileTask.Cond> {

    enum Cond {
        SUCCESS, NETWORK_FAILURE
    }

    private IShowProfileView showProfileView;
    private LocalContactsHelper helper;
    private ProfileBean profile;
    private int contacts = 0, messages = 0, posts = 0;

    public FetchProfileTask(IShowProfileView showProfileView) {
        this.showProfileView = showProfileView;
        helper = new LocalContactsHelper(showProfileView.getContext());
    }

    @Override
    protected Cond doInBackground(String... params) {

        final Container<Cond> res = new Container<>(Cond.SUCCESS);

        HttpUtil.HttpErrorListener listener = new HttpUtil.HttpErrorListener() {
            @Override
            public void onError(Exception e) {
                res.setValue(Cond.NETWORK_FAILURE);
            }
        };

        String username = params[0];

        profile = new ViewProfileHelper(username).viewProfile(listener);

        if (profile == null) {
            profile = new ProfileBean();
        }

        contacts += new ViewFollowUserHelper(username).doQuery(listener).size();
        contacts += new ViewFriendListHelper(username).doQuery(listener).size();
        contacts += new ViewFollowerHelper(username).doQuery(listener).size();

        // TODO also get the count of messages and posts.

        return res.getValue();
    }

    @Override
    protected void onPostExecute(Cond cond) {

        if (!profile.isEmpty() && cond == Cond.SUCCESS) {
            showProfileView.showProfile(profile, helper.getCategoryByUsername(profile.getUsername()));
            showProfileView.showCommunicationData(contacts, messages, posts);
        } else {
            showProfileView.showFetchDataFailed();
        }

    }
}
