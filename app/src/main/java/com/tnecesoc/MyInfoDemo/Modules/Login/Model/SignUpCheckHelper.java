package com.tnecesoc.MyInfoDemo.Modules.Login.Model;

import com.tnecesoc.MyInfoDemo.Bean.Container;
import com.tnecesoc.MyInfoDemo.GlobalModel.Host;
import com.tnecesoc.MyInfoDemo.GlobalModel.RemoteModel;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public class SignUpCheckHelper extends RemoteModel {

    public static final String URL = Host.URL + "/check-repetition";

    public SignUpCheckHelper(final String username) {
        super(URL, new HashMap<String, String>(){{
            put("username", username);
        }});
    }

}
