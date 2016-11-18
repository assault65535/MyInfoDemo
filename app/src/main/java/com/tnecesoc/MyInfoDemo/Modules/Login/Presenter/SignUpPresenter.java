package com.tnecesoc.MyInfoDemo.Modules.Login.Presenter;

import android.graphics.Bitmap;
import com.tnecesoc.MyInfoDemo.Modules.Login.Tasks.PostAvatarTask;
import com.tnecesoc.MyInfoDemo.Modules.Login.Tasks.SignUpTask;
import com.tnecesoc.MyInfoDemo.Modules.Login.View.ISignUpView;

/**
 * Created by Tnecesoc on 2016/11/18.
 */
public class SignUpPresenter {

    private ISignUpView view;
    private Bitmap mAvatar;

    private boolean isPhoneEntered = false;

    public SignUpPresenter(ISignUpView view) {
        this.view = view;
        mAvatar = null;
    }

    public void performSignUp(String community, String phone, String username, String password) {

        if (!isPhoneEntered && phone != null && !phone.isEmpty()) {
            isPhoneEntered = true;
            view.showPhoneEntered();
            return;
        }

        if (isPhoneEntered) {
            new SignUpTask(view).execute(community, phone, username, password);
            if (mAvatar != null) {
                new PostAvatarTask().execute(mAvatar);
            }
        } else {
            view.showPhoneNotEntered();
        }
    }

    public void performChangeAvatar(Bitmap newAvatar) {
        mAvatar = newAvatar;
    }

}
