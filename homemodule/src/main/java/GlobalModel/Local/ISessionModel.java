package GlobalModel.Local;

import Bean.ProfileBean;

/**
 * Created by Nero on 2016/12/29.
 */
interface ISessionModel {

    boolean isOnline();

    void beginSession(String username, String password, ProfileBean profileBean);

    void updateSession(ProfileBean profileBean);

    void terminateSession();

    String getSessionAttribute(String key);

}
