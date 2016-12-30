package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Profile.Tasks;

import android.os.AsyncTask;
import com.tnecesoc.MyInfoDemo.Entity.Container;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Profile.Model.UpdateProfileHelper;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

/**
 * Created by Tnecesoc on 2016/12/7.
 * automatically wrote the Cond class.
 */
public class PostProfileTask extends AsyncTask<String, Void, PostProfileTask.Cond> {

    enum Cond {
        SUCCESS, NETWORK_FAILURE, POSTING
    }

    private Profile data;

    public PostProfileTask(Profile data) {
        this.data = data;
    }

    @Override
    protected Cond doInBackground(String... params) {

        final Container<Cond> ans = new Container<>(Cond.POSTING);

        String password = params[0];

        new UpdateProfileHelper(data, password).doRequest(new HttpUtil.HttpResponseListener() {
            @Override
            public void onSuccess(String responseContent) {
                ans.setValue(Cond.SUCCESS);
            }

            @Override
            public void onError(Exception e) {
                ans.setValue(Cond.NETWORK_FAILURE);
            }
        });

        return ans.getValue();
    }
}
