package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Model;

import com.google.gson.reflect.TypeToken;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.RemoteModelImpl;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tnecesoc on 2016/12/11.
 */
public class ViewFollowUserHelper extends RemoteModelImpl {

    public static final String URL = Host.SERVER_HOST + "/view-follow-list";

    public ViewFollowUserHelper(final String username) {
        super(URL, new HashMap<String, String>() {{
            put("username", username);
        }});
    }

    @Override @Deprecated
    public void doRequest(HttpUtil.HttpResponseListener listener) {
        super.doRequest(listener);
    }

    @SuppressWarnings("unchecked")
    public List<Profile> doQuery(HttpUtil.HttpErrorListener listener) {
        List<Profile> res = super.doQueryForObject(new TypeToken<List<Profile>>() {}, listener);
        return res == null ? new ArrayList<Profile>() : res;
    }
}
