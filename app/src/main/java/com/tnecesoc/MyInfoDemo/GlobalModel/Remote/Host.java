package com.tnecesoc.MyInfoDemo.GlobalModel.Remote;

/**
 * Created by Tnecesoc on 2016/11/10.
 */
public class Host {

    public static final String SERVER_HOST = "http://172.22.213.109:8080";

    public static final String BROKER_HOST = "tcp://172.22.213.109:1883";

    public static final String findAvatarUrlByUsername(String username) {
        return SERVER_HOST + "/user-avatars/" + username + ".png";
    }

}
