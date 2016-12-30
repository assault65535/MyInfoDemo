package com.tnecesoc.MyInfoDemo.Modules.Homepage.Tasks;

import android.os.AsyncTask;
import com.tnecesoc.MyInfoDemo.Entity.Container;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.ViewProfileHelper;
import com.tnecesoc.MyInfoDemo.GlobalView.Interfaces.IShowProfileView;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

/**
 * Created by Tnecesoc on 2016/12/23.
 */
public class SearchContactTask extends AsyncTask<String, Void, SearchContactTask.Cond> {

    enum Cond {
        SUCCESS, NETWORK_FAILURE, NO_SUCH_USER
    }

    private IShowProfileView view;
    private Profile profile;
    private String username;

    public SearchContactTask(IShowProfileView view) {
        this.view = view;
    }

    @Override
    protected Cond doInBackground(String... params) {

        final Container<Cond> res = new Container<>(Cond.SUCCESS);

        username = params[0];

        profile = new ViewProfileHelper(username).viewProfile(new HttpUtil.HttpErrorListener() {
            @Override
            public void onError(Exception e) {
                res.setValue(Cond.NETWORK_FAILURE);
            }
        });

        if (res.getValue() == Cond.SUCCESS && !username.equals(profile.getUsername())) {
            res.setValue(Cond.NO_SUCH_USER);
        }

        return res.getValue();
    }

    @Override
    protected void onPostExecute(Cond cond) {

        if (cond == Cond.SUCCESS) {
            view.showProfile(profile, Profile.Category.ARBITRARY);
        } else {
            view.showFetchDataFailed();
        }

    }
}
