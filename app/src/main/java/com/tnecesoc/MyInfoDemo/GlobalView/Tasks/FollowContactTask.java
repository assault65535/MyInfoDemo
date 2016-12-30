package com.tnecesoc.MyInfoDemo.GlobalView.Tasks;

import android.os.AsyncTask;
import com.tnecesoc.MyInfoDemo.Entity.Container;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.LocalContactsHelper;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.ViewProfileHelper;
import com.tnecesoc.MyInfoDemo.GlobalView.Interfaces.IFollowView;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.AddFollowRelationshipHelper;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

/**
 * Created by Tnecesoc on 2016/12/14.
 */
public class FollowContactTask extends AsyncTask<String, Void, FollowContactTask.Cond> {

    enum Cond {
        SUCCESS, NETWORK_FAILURE
    }

    IFollowView view;
    Profile.Category category;
    LocalContactsHelper helper;

    public FollowContactTask(IFollowView view, LocalContactsHelper helper) {
        this.helper = helper;
        this.view = view;
    }

    @Override
    protected Cond doInBackground(String... params) {
        final Container<Cond> res = new Container<>(Cond.SUCCESS);


        String me = params[0];
        String other = params[1];

        category = helper.getCategoryByUsername(other);

        new AddFollowRelationshipHelper(me, other).doRequest(new HttpUtil.HttpResponseListener() {
            @Override
            public void onSuccess(String responseContent) {
                res.setValue(Cond.SUCCESS);
            }

            @Override
            public void onError(Exception e) {
                res.setValue(Cond.NETWORK_FAILURE);
            }
        });

        if (res.getValue() == Cond.SUCCESS) {

            Profile othersProfile = new ViewProfileHelper(other).viewProfile(new HttpUtil.HttpErrorListener() {
                @Override
                public void onError(Exception e) {
                    res.setValue(Cond.NETWORK_FAILURE);
                }
            });

            switch (category) {
                case FOLLOWER:
                case FOLLOW:
                case FRIEND:
                    category = Profile.Category.FRIEND;
                    break;
                case ARBITRARY:
                default:
                    category = Profile.Category.FOLLOW;
                    break;
            }

            helper.putProfile(othersProfile, category);
        }

        return res.getValue();
    }

    @Override
    protected void onPostExecute(Cond cond) {

        if (cond == Cond.SUCCESS) {
            view.showFollowSucceed();
        } else {
            view.showFollowFailed();
        }

    }
}
