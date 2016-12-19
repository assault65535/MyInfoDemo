package com.tnecesoc.MyInfoDemo.GlobalModel.Remote;

import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.RemoteModelImpl;

import java.util.HashMap;

/**
 * Created by Tnecesoc on 2016/12/11.
 */
public class RemoveFollowRelationshipHelper extends RemoteModelImpl {

    public static final String URL = Host.URL + "/remove-follow-relationship";

    public RemoveFollowRelationshipHelper(final String me, final String other) {
        super(URL, new HashMap<String, String>(){{
            put("username1", me);
            put("username2", other);
        }});
    }
}
