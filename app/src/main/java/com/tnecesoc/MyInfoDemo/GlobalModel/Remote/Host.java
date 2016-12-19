package com.tnecesoc.MyInfoDemo.GlobalModel.Remote;

/**
 * Created by Tnecesoc on 2016/11/10.
 */
public class Host {

    public static final String URL = "http://172.22.213.109:8080";

    public static final String findAvatarUrlByUsername(String username) {
        return URL + "/user-avatars/" + username + ".png";
    }

}
