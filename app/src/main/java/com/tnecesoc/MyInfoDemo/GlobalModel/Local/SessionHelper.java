package com.tnecesoc.MyInfoDemo.GlobalModel.Local;

import android.content.Context;
import android.content.SharedPreferences;
import com.tnecesoc.MyInfoDemo.Entity.Profile;

import java.lang.reflect.Field;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public class SessionHelper implements ISessionModel {

    private static final String INTERNAL_NAME = "ACCOUNT";
    public static final String KEY_USERNAME = "USER";
    public static final String KEY_PASSWORD = "PASSWORD";
    public static final String KEY_ONLINE = "ONLINE";

    public static final String KEY_COMMUNITY = "COMMUNITY";
    public static final String KEY_PHONE = "PHONE";
    public static final String KEY_NICKNAME = "NICKNAME";
    public static final String KEY_EMAIL = "EMAIL";
    public static final String KEY_GENDER = "GENDER";
    public static final String KEY_ADDRESS = "ADDRESS";
    public static final String KEY_MOTTO = "MOTTO";

    private SharedPreferences sharedPreferences;

    public SessionHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(INTERNAL_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isOnline() {
        return sharedPreferences.getBoolean(KEY_ONLINE, false);
    }

    @Override
    public void beginSession(String username, String password, Profile profile) {
        sharedPreferences.edit().putString(KEY_USERNAME, username).apply();
        sharedPreferences.edit().putString(KEY_PASSWORD, password).apply();
        sharedPreferences.edit().putBoolean(KEY_ONLINE, true).apply();

        updateSession(profile);
    }

    @Override
    public void updateSession(Profile profile) {
        for (Field f : Profile.class.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.get(profile) == null) {
                    sharedPreferences.edit().remove(f.getName().toUpperCase()).apply();
                } else {
                    sharedPreferences.edit().putString(f.getName().toUpperCase(), f.get(profile) + "").apply();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void terminateSession() {

        for (Field f : Profile.class.getDeclaredFields()) {
            sharedPreferences.edit().remove(f.getName().toUpperCase()).apply();
        }

        sharedPreferences.edit().putBoolean(KEY_ONLINE, false).apply();
    }

    @Override
    public String getSessionAttribute(String key) {
        return sharedPreferences.getString(key, null);
    }

    public Profile getEntireProfile() {
        Profile res = new Profile();
        for (Field f : Profile.class.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                String info = sharedPreferences.getString(f.getName().toUpperCase(), null);
                if (f.getType().equals(String.class)) {
                    f.set(res, info);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

}
