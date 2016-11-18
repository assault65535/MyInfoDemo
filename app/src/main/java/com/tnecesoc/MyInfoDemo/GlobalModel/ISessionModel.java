package com.tnecesoc.MyInfoDemo.GlobalModel;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
interface ISessionModel {

    boolean isOnline();

    void beginSession(String username, String password);

    void terminateSession();

    String getSessionAttribute(String key);

}
