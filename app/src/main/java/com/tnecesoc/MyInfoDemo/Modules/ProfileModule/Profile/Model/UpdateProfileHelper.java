package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Profile.Model;

import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.RemoteModelImpl;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.HashMap;

/**
 * Created by Tnecesoc on 2016/12/7.
 */
public class UpdateProfileHelper extends RemoteModelImpl {

    public static final String URL = Host.SERVER_HOST + "/update-profile";

    public UpdateProfileHelper(final Profile profile, final String password) {
        super(URL, new HashMap<String, String>(){{
            put("username", profile.getUsername());
            put("password", password);
            put("nickname", profile.getNickname());
            put("email", profile.getEmail());
            put("gender", profile.getGender());
            put("address", profile.getAddress());
            put("motto", profile.getMotto());
        }});
    }

    @Override
    public void doRequest(HttpUtil.HttpResponseListener listener) {
        super.doRequest(listener);
    }
}
