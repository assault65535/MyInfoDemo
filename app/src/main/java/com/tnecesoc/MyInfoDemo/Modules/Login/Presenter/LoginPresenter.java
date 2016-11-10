package com.tnecesoc.MyInfoDemo.Modules.Login.Presenter;

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

    public void performSignIn(String username, String password) {

        new SignInTask(view).execute(username, password);

    }

    public void performForceSignIn() {
        if (++counter >= 5) {
            view.showLoginSuccessful();
            counter = 0;
        }
    }

}
