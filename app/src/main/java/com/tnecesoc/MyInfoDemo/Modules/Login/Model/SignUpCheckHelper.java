package com.tnecesoc.MyInfoDemo.Modules.Login.Model;

import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.RemoteModelImpl;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.HashMap;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public class SignUpCheckHelper extends RemoteModelImpl {

    public static final String URL = Host.URL + "/check-repetition";

    public SignUpCheckHelper(final String username) {
        super(URL, new HashMap<String, String>(){{
            put("username", username);
        }});
    }

    @Override
    public void doRequest(HttpUtil.HttpResponseListener listener) {
        super.doRequest(listener);
    }
}
