package com.tnecesoc.MyInfoDemo.Modules.Login.Model;

import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.RemoteModelImpl;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.HashMap;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public class SignUpHelper extends RemoteModelImpl {

    private static final String URL = Host.URL + "/sign-up";

    public SignUpHelper(final String community, final String phone, final String username, final String password) {
        super(URL, new HashMap<String, String>(){{
            put("community", community);
            put("phone", phone);
            put("username", username);
            put("password", password);
        }});
    }

    @Override
    public void doRequest(HttpUtil.HttpResponseListener listener) {
        super.doRequest(listener);
    }
}
