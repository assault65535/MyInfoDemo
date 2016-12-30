package com.tnecesoc.MyInfoDemo.GlobalModel.Local;

import com.tnecesoc.MyInfoDemo.Entity.Profile;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
interface ISessionModel {

    boolean isOnline();

    void beginSession(String username, String password, Profile profile);

    void updateSession(Profile profile);

    void terminateSession();

    String getSessionAttribute(String key);

}
