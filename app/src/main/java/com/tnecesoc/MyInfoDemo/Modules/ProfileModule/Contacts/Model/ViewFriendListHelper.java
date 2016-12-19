package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Model;

import com.google.gson.reflect.TypeToken;
import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.RemoteModelImpl;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tnecesoc on 2016/12/12.
 */
public class ViewFriendListHelper extends RemoteModelImpl {

    public static final String URL = Host.URL + "/view-friend-list";

    public static final Class CLASS_TOKEN = new ArrayList<ProfileBean>().getClass();

    public ViewFriendListHelper(final String username) {
        super(URL, new HashMap<String, String>(){{
            put("username", username);
        }});
    }

    @Override @Deprecated
    public void doRequest(HttpUtil.HttpResponseListener listener) {
        super.doRequest(listener);
    }

    @SuppressWarnings("unchecked")
    public List<ProfileBean> doQuery(HttpUtil.HttpErrorListener listener) {
        List<ProfileBean> res = super.doQueryForObject(new TypeToken<List<ProfileBean>>() {}, listener);
        return res == null ? new ArrayList<ProfileBean>() : res;
    }
}
