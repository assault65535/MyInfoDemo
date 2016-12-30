package com.tnecesoc.MyInfoDemo.Modules.Login.Model;

import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.RemoteModelImpl;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.HashMap;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public class InvitationHelper extends RemoteModelImpl {

    private static final String URL = Host.SERVER_HOST + "/check-invitation";

    public InvitationHelper(final String community, final String phone) {
        super(URL, new HashMap<String, String>(){{
            put("community", community);
            put("phone", phone);
        }});
    }

    @Override
    public void doRequest(HttpUtil.HttpResponseListener listener) {
        super.doRequest(listener);
    }
}
