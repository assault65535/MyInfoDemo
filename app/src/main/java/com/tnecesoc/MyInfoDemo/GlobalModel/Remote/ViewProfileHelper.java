package com.tnecesoc.MyInfoDemo.GlobalModel.Remote;

import com.google.gson.reflect.TypeToken;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.HashMap;

/**
 * Created by Tnecesoc on 2016/11/21.
 */
public class ViewProfileHelper extends RemoteModelImpl {

    private static final String URL = Host.SERVER_HOST + "/view-profile";

    public ViewProfileHelper(final String username) {
        super(URL, new HashMap<String, String>(){{
            put("username", username);
        }});
    }

    @Override @Deprecated
    public void doRequest(HttpUtil.HttpResponseListener listener) {
        super.doRequest(listener);
    }

    public Profile viewProfile(final HttpUtil.HttpErrorListener listener) {
        return super.doQueryForObject(new TypeToken<Profile>(){}, listener);
    }
}
