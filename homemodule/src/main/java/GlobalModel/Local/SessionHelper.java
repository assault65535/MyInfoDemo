package GlobalModel.Local;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Field;

import Bean.ProfileBean;

/**
 * Created by Nero on 2016/12/29.
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
    public void beginSession(String username, String password, ProfileBean profileBean) {
        sharedPreferences.edit().putString(KEY_USERNAME, username).apply();
        sharedPreferences.edit().putString(KEY_PASSWORD, password).apply();
        sharedPreferences.edit().putBoolean(KEY_ONLINE, true).apply();

        updateSession(profileBean);
    }

    @Override
    public void updateSession(ProfileBean profileBean) {
        for (Field f : ProfileBean.class.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.get(profileBean) == null) {
                    sharedPreferences.edit().remove(f.getName().toUpperCase()).apply();
                } else {
                    sharedPreferences.edit().putString(f.getName().toUpperCase(), f.get(profileBean) + "").apply();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void terminateSession() {

        for (Field f : ProfileBean.class.getDeclaredFields()) {
            sharedPreferences.edit().remove(f.getName().toUpperCase()).apply();
        }

        sharedPreferences.edit().putBoolean(KEY_ONLINE, false).apply();
    }

    @Override
    public String getSessionAttribute(String key) {
        return sharedPreferences.getString(key, null);
    }

    public ProfileBean getEntireProfile() {
        ProfileBean res = new ProfileBean();
        for (Field f : ProfileBean.class.getDeclaredFields()) {
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

