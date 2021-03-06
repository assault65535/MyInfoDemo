package com.tnecesoc.MyInfoDemo.Modules.Login.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import com.tnecesoc.MyInfoDemo.Entity.Container;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.ViewProfileHelper;
import com.tnecesoc.MyInfoDemo.Modules.Login.Model.LoginHelper;
import com.tnecesoc.MyInfoDemo.Modules.Login.View.ILoginView;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public class SignInTask extends AsyncTask<String, Void, SignInTask.Cond> {

    enum Cond {
        NETWORK_FAILURE, ACCESS_DENIED, SUCCESS;
    }

    private ILoginView view;
    private Context context;

    public SignInTask(Context context, ILoginView view) {
        this.view = view;
        this.context = context;
    }

    @Override
    protected Cond doInBackground(String... params) {

        final Container<Cond> ans = new Container<>();

        final String username = params[0];
        final String password = params[1];

        final Container<Profile> profileBeanContainer = new Container<>();

        new LoginHelper(username, password).doRequest(new HttpUtil.HttpResponseListener() {
            @Override
            public void onSuccess(String responseContent) {
                if (Boolean.valueOf(responseContent)) {
                    ans.setValue(Cond.SUCCESS);
                    profileBeanContainer.setValue(new ViewProfileHelper(username).viewProfile(new HttpUtil.HttpErrorListener() {
                        @Override
                        public void onError(Exception e) {
                            ans.setValue(Cond.NETWORK_FAILURE);
                        }
                    }));

                    if (ans.getValue() == Cond.SUCCESS) {
                        new SessionHelper(context).beginSession(username, password, profileBeanContainer.getValue());
                    }

                } else {
                    ans.setValue(Cond.ACCESS_DENIED);
                }
            }

            @Override
            public void onError(Exception e) {
                ans.setValue(Cond.NETWORK_FAILURE);
            }
        });

        return ans.getValue();
    }

    @Override
    protected void onPostExecute(Cond result) {
        if (result == Cond.SUCCESS) {
            view.showLoginSuccessful();
        } else if (result == Cond.ACCESS_DENIED) {
            view.showAccessDenied();
        } else {
            view.showNetworkFailure();
        }
    }
}
