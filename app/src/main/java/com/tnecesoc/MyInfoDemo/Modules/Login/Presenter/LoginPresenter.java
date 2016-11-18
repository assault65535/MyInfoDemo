package com.tnecesoc.MyInfoDemo.Modules.Login.Presenter;

import android.content.Context;
import com.tnecesoc.MyInfoDemo.Modules.Login.Tasks.SignInTask;
import com.tnecesoc.MyInfoDemo.Modules.Login.View.ILoginView;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public class LoginPresenter {

    private ILoginView view;
    private int counter = 0;

    public LoginPresenter(ILoginView view) {
        this.view = view;
    }

    public void performSignIn(Context context, String username, String password) {

        new SignInTask(context, view).execute(username, password);

    }



    public void performForceSignIn() {
        if (++counter >= 5) {
            view.showLoginSuccessful();
            counter = 0;
        }
    }

}
