package com.tnecesoc.MyInfoDemo.Modules.Login.Model;

import com.tnecesoc.MyInfoDemo.GlobalModel.Host;
import com.tnecesoc.MyInfoDemo.GlobalModel.RemoteModel;

import java.util.HashMap;

/**
 * Created by Tnecesoc on 2016/11/4.
 */
public class LoginHelper extends RemoteModel {

    private static final String URL = Host.URL + "/sign-in";

    public LoginHelper(final String username, final String password) {
        super(URL, new HashMap<String, String>(){{
            put("username", username);
            put("password", password);
        }});
    }

}
