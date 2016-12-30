package com.tnecesoc.MyInfoDemo.Modules.Homepage.View;

import com.tnecesoc.MyInfoDemo.Entity.Profile;

/**
 * Created by Tnecesoc on 2016/11/10.
 */
public interface INavView {

    void showCurrentContacts(int count, boolean shouldEmphasize);

    void showCurrentMessages(int count, boolean shouldEmphasize);

    void notifyMessagesAdded();

    void showCurrentPosts(int count, boolean shouldEmphasize);

    void showUserInfo(Profile profile);

}
