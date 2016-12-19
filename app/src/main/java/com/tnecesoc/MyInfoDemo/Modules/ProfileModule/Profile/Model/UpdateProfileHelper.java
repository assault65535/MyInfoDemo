package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Profile.Model;

import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.RemoteModelImpl;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.HashMap;

/**
 * Created by Tnecesoc on 2016/12/7.
 */
public class UpdateProfileHelper extends RemoteModelImpl {

    public static final String URL = Host.URL + "/update-profile";

    public UpdateProfileHelper(final ProfileBean profileBean, final String password) {
        super(URL, new HashMap<String, String>(){{
            put("username", profileBean.getUsername());
            put("password", password);
            put("nickname", profileBean.getNickname());
            put("email", profileBean.getEmail());
            put("gender", profileBean.getGender());
            put("address", profileBean.getAddress());
            put("motto", profileBean.getMotto());
        }});
    }

    @Override
    public void doRequest(HttpUtil.HttpResponseListener listener) {
        super.doRequest(listener);
    }
}
