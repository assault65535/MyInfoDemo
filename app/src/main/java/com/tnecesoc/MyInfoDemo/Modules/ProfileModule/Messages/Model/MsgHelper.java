package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Model;

import com.google.gson.reflect.TypeToken;
import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.RemoteModelImpl;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tnecesoc on 2016/12/24.
 */
public class MsgHelper extends RemoteModelImpl {

    public static final String URL = Host.SERVER_HOST + "/get-messages";

    public MsgHelper(final String username) {
        super(URL, new HashMap<String, String>(){{
            put("username", username);
        }});
    }

    public MsgHelper(final String username, final Date laterThan) {
        super(URL, new HashMap<String, String>() {{
            put("username", username);
            put("later-than", String.valueOf(laterThan.getTime()));
        }});
    }

    public List<Message> getMsgList(HttpUtil.HttpErrorListener listener) {

        List<Message> res = super.doQueryForObject(new TypeToken<List<Message>>() {}, listener);

        return res != null ? res : new ArrayList<Message>();
    }
}
