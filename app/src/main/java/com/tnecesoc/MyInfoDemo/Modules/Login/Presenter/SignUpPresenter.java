package com.tnecesoc.MyInfoDemo.Modules.Login.Presenter;

import android.graphics.Bitmap;
import com.tnecesoc.MyInfoDemo.Modules.Login.Tasks.SignUpTask;
import com.tnecesoc.MyInfoDemo.Modules.Login.View.ISignUpView;

/**
 * Created by Tnecesoc on 2016/11/18.
 */
public class SignUpPresenter {

    private ISignUpView view;

    private boolean isPhoneEntered = false;

    public SignUpPresenter(ISignUpView view) {
        this.view = view;
    }

    public void performSignUp(String community, String phone, String username, String password, Bitmap avatar) {

        if (!isPhoneEntered && phone != null && !phone.isEmpty()) {
            isPhoneEntered = true;
            view.showPhoneEntered();
            return;
        }

        if (isPhoneEntered) {
            new SignUpTask(view, avatar).execute(community, phone, username, password);
        } else {
            view.showPhoneNotEntered();
        }
    }

}
