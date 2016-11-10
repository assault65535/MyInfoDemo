package com.tnecesoc.MyInfoDemo.Modules.Login.Model;

import com.tnecesoc.MyInfoDemo.Bean.Container;
import com.tnecesoc.MyInfoDemo.GlobalModel.RemoteModel;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public class InvitationHelper extends RemoteModel {

    private static final String URL = "http://172.22.213.109:8080/check-invitation";

    public InvitationHelper(final String community, final String phone) {
        super(URL, new HashMap<String, String>(){{
            put("community", community);
            put("phone", phone);
        }});
    }
}
