package com.tnecesoc.MyInfoDemo.GlobalModel.Remote;

import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.RemoteModelImpl;

import java.util.HashMap;

/**
 * Created by Tnecesoc on 2016/12/11.
 */
public class BlockUserHelper extends RemoteModelImpl {

    public static final String URL = Host.URL + "/block-user";

    public BlockUserHelper(final String me, final String other) {
        super(URL, new HashMap<String, String>(){{
            put("username", me);
            put("blocked_username", other);
        }});
    }
}
