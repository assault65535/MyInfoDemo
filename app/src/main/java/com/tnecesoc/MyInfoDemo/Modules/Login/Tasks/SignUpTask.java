package com.tnecesoc.MyInfoDemo.Modules.Login.Tasks;

import android.os.AsyncTask;
import com.tnecesoc.MyInfoDemo.Bean.Container;
import com.tnecesoc.MyInfoDemo.Modules.Login.Model.InvitationHelper;
import com.tnecesoc.MyInfoDemo.Modules.Login.Model.SignUpCheckHelper;
import com.tnecesoc.MyInfoDemo.Modules.Login.Model.SignUpHelper;
import com.tnecesoc.MyInfoDemo.Modules.Login.View.ISignUpView;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

/**
 * Created by Tnecesoc on 2016/11/5.
 */

public class SignUpTask extends AsyncTask<String, Void, SignUpTask.Cond> {

    enum Cond {
        CHECKING, NOT_INVITED, USERNAME_CONFLICT, NETWORK_FAILURE, SUCCESS
    }

    private ISignUpView view;

    public SignUpTask(ISignUpView view) {
        this.view = view;
    }

    @Override
    protected Cond doInBackground(String... params) {

        final Container<SignUpTask.Cond> ans = new Container<>(Cond.CHECKING);

        String community = params[0];
        String phone = params[1];
        String username = params[2];
        String password = params[3];

        new InvitationHelper(community, phone).doQuery(new HttpUtil.HttpResponseListener() {
            @Override
            public void onSuccess(String responseContent) {
                if (!Boolean.valueOf(responseContent)) {
                    ans.setValue(Cond.NOT_INVITED);
                }
            }

            @Override
            public void onError(Exception e) {
                ans.setValue(Cond.NETWORK_FAILURE);
            }
        });

        if (ans.getValue() == Cond.CHECKING) {
            new SignUpCheckHelper(username).doQuery(new HttpUtil.HttpResponseListener() {
                @Override
                public void onSuccess(String responseContent) {
                    if (Boolean.valueOf(responseContent)) {
                        ans.setValue(Cond.USERNAME_CONFLICT);
                    }
                }

                @Override
                public void onError(Exception e) {
                    ans.setValue(Cond.NETWORK_FAILURE);
                }
            });
        }

        if (ans.getValue() == Cond.CHECKING) {
            new SignUpHelper(community, phone, username, password).doQuery(new HttpUtil.HttpResponseListener() {
                @Override
                public void onSuccess(String responseContent) {
                    if (!Boolean.valueOf(responseContent)) {
                        ans.setValue(Cond.NETWORK_FAILURE);
                    } else {
                        ans.setValue(Cond.SUCCESS);
                    }
                }

                @Override
                public void onError(Exception e) {
                    ans.setValue(Cond.NETWORK_FAILURE);
                }
            });
        }

        return ans.getValue();
    }

    @Override
    protected void onPostExecute(Cond cond) {
        if (cond == Cond.SUCCESS) {
            view.showSignInSuccessful();
        } else if (cond == Cond.USERNAME_CONFLICT) {
            view.showUsernameConflicted();
        } else if (cond == Cond.NOT_INVITED) {
            view.showNotInvited();
        } else {
            view.showNetworkFailure();
        }
    }
}
