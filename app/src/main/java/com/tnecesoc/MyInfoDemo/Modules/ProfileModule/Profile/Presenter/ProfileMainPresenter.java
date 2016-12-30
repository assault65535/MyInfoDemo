package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Profile.Presenter;

import android.graphics.Bitmap;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;
import com.tnecesoc.MyInfoDemo.GlobalView.Tasks.PostAvatarTask;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Profile.Tasks.PostProfileTask;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Profile.View.IShowUserProfileView;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Profile.View.ProfileMainActivity;
import com.tnecesoc.MyInfoDemo.GlobalView.Interfaces.IUpdateAvatarView;

/**
 * Created by Tnecesoc on 2016/12/7.
 */
public class ProfileMainPresenter {

    private IShowUserProfileView showProfileView;
    private IUpdateAvatarView updateAvatarView;
    private SessionHelper sessionHelper;

    public ProfileMainPresenter(ProfileMainActivity activity) {
        this.showProfileView = activity;
        this.updateAvatarView = activity;
        sessionHelper = new SessionHelper(updateAvatarView.getContext());
    }

    public void performFetchProfile() {
        SessionHelper session = new SessionHelper(updateAvatarView.getContext());
        showProfileView.showProfile(session.getEntireProfile());
    }

    public void performUpdateProfile(Profile newProfile) {
        new PostProfileTask(newProfile).execute(sessionHelper.getSessionAttribute(SessionHelper.KEY_PASSWORD));
        sessionHelper.updateSession(newProfile);
    }

    public void performUpdateAvatar(Bitmap newAvatar) {
        new PostAvatarTask(updateAvatarView, newAvatar).execute(sessionHelper.getSessionAttribute(SessionHelper.KEY_USERNAME));
    }

}
