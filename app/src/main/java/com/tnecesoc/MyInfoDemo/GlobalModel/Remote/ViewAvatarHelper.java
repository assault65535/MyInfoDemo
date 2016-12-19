package com.tnecesoc.MyInfoDemo.GlobalModel.Remote;

import android.graphics.Bitmap;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.LocalFileHelper;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

/**
 * Created by Tnecesoc on 2016/11/24.
 */
@Deprecated
public class ViewAvatarHelper extends RemoteModelImpl {

    public static final String URL = Host.URL + "/user-avatars";

    public String username;

    public ViewAvatarHelper(String username) {
        super(URL + "/" + username + ".png", null);
        this.username = username;
    }

    @Override @Deprecated
    public void doRequest(HttpUtil.HttpResponseListener listener) {
        super.doRequest(listener);
    }

    public Bitmap doQueryAvatar() {
        LocalFileHelper localFileHelper = new LocalFileHelper();

        if (localFileHelper.isAvatarImageExist(username)) {
            return localFileHelper.loadAvatarImage(username);
        } else {
            Bitmap ans = HttpUtil.downloadPicture(getUrl());
            if (ans != null) {
                localFileHelper.saveAvatarImage(ans, username);
            }
            return ans;
        }

    }

}
