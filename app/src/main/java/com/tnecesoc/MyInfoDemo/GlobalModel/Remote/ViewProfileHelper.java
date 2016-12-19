package com.tnecesoc.MyInfoDemo.GlobalModel.Remote;

import com.google.gson.reflect.TypeToken;
import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Tnecesoc on 2016/11/21.
 */
public class ViewProfileHelper extends RemoteModelImpl {

    private static final String URL = Host.URL + "/view-profile";

    public ViewProfileHelper(final String username) {
        super(URL, new HashMap<String, String>(){{
            put("username", username);
        }});
    }

    @Override @Deprecated
    public void doRequest(HttpUtil.HttpResponseListener listener) {
        super.doRequest(listener);
    }

    public ProfileBean viewProfile(final HttpUtil.HttpErrorListener listener) {
        return super.doQueryForObject(new TypeToken<ProfileBean>(){}, listener);
    }
}
