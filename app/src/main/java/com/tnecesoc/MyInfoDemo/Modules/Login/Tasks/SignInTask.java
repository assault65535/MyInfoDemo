package com.tnecesoc.MyInfoDemo.Modules.Login.Tasks;

import android.os.AsyncTask;
import com.tnecesoc.MyInfoDemo.Bean.Container;
import com.tnecesoc.MyInfoDemo.Modules.Login.Model.LoginHelper;
import com.tnecesoc.MyInfoDemo.Modules.Login.View.ILoginView;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public class SignInTask extends AsyncTask<String, Void, SignInTask.Cond> {

    enum Cond {
        NETWORK_FAILURE, ACCESS_DENIED, SUCCESS
    }

    private ILoginView view;

    public SignInTask(ILoginView view) {
        this.view = view;
    }

    @Override
    protected Cond doInBackground(String... params) {

        final Container<Cond> ans = new Container<>();

        String username = params[0];
        String password = params[1];

        new LoginHelper(username, password).doQuery(new HttpUtil.HttpResponseListener() {
            @Override
            public void onSuccess(String responseContent) {
                if (Boolean.valueOf(responseContent)) {
                    ans.setValue(Cond.SUCCESS);
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
