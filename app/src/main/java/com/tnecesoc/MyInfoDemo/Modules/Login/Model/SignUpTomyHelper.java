package com.tnecesoc.MyInfoDemo.Modules.Login.Model;

import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.RemoteModelImpl;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tnecesoc on 2016/12/23.
 */
public class SignUpTomyHelper extends RemoteModelImpl {

    public static final String URL = "http://172.22.30.48:8080/firstProject_war_exploded/server-plus-demo";

    private Map<String, String> mHeader;

    public SignUpTomyHelper(final String phone, final String password) {
        super(URL, null);
        mHeader = new HashMap<String, String>() {{
            put("para1", phone);
            put("para2", password);
            put("para3", "signup");
        }};
    }

    @Override
    public void doRequest(HttpUtil.HttpResponseListener listener) {
        HttpUtil.sendPostRequestWithExtraHeader(URL, mHeader, new HashMap<String, String>(), listener);

    }
}
