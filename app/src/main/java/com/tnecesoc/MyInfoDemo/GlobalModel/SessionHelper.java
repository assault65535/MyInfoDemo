package com.tnecesoc.MyInfoDemo.GlobalModel;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public class SessionHelper implements ISessionModel {

    private static final String INTERNAL_NAME = "ACCOUNT";
    public static final String KEY_USER = "USER";
    public static final String KEY_PASSWORD = "PASSWORD";
    public static final String KEY_ONLINE = "ONLINE";

    private SharedPreferences sharedPreferences;

    public SessionHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(INTERNAL_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isOnline() {
        return sharedPreferences.getBoolean(KEY_ONLINE, false);
    }

    @Override
    public void beginSession(String username, String password) {
        sharedPreferences.edit().putString(KEY_USER, username).apply();
        sharedPreferences.edit().putString(KEY_PASSWORD, password).apply();
        sharedPreferences.edit().putBoolean(KEY_ONLINE, true).apply();
    }

    @Override
    public void terminateSession() {
        sharedPreferences.edit().putBoolean(KEY_ONLINE, false).apply();
    }

    @Override
    public String getSessionAttribute(String key) {
        return sharedPreferences.getString(key, null);
    }
}
