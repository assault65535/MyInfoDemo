package com.tnecesoc.MyInfoDemo.Modules.Homepage.View;

import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;

/**
 * Created by Tnecesoc on 2016/11/10.
 */
public interface IMainPageView {

    void showCurrentContacts(int count, boolean shouldEmphasize);

    void showCurrentMessages(int count, boolean shouldEmphasize);

    void showCurrentPosts(int count, boolean shouldEmphasize);

    void showUserInfo(ProfileBean profileBean);

}
