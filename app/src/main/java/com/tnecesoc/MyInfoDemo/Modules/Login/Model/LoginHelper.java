package com.tnecesoc.MyInfoDemo.Modules.Login.Model;

import com.tnecesoc.MyInfoDemo.Bean.Container;
import com.tnecesoc.MyInfoDemo.GlobalModel.ExceptionDetector;
import com.tnecesoc.MyInfoDemo.GlobalModel.RemoteModel;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil.HttpResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tnecesoc on 2016/11/4.
 */
public class LoginHelper extends RemoteModel {

    private static final String URL = "http://172.22.213.109:8080/sign-in";

    public LoginHelper(final String username, final String password) {
        super(URL, new HashMap<String, String>(){{
            put("username", username);
            put("password", password);
        }});
    }

}
