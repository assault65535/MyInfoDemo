package com.tnecesoc.MyInfoDemo.GlobalModel.Local;

import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
interface ISessionModel {

    boolean isOnline();

    void beginSession(String username, String password, ProfileBean profileBean);

    void updateSession(ProfileBean profileBean);

    void terminateSession();

    String getSessionAttribute(String key);

}
