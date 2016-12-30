package com.tnecesoc.MyInfoDemo.GlobalModel.Remote;

import android.graphics.Bitmap;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.LocalFileHelper;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;
import com.tnecesoc.MyInfoDemo.Utils.OkHttpUtil;

import java.util.HashMap;

/**
 * Created by Tnecesoc on 2016/11/24.
 */
public class UpdateAvatarHelper extends RemoteModelImpl {

    public static final String URL = Host.SERVER_HOST + "/upload-avatar";

    private String username;
    private LocalFileHelper localFileHelper = new LocalFileHelper();


    public UpdateAvatarHelper(final String username, Bitmap newAvatar) {
        super(URL, null);
        this.username = username;
        localFileHelper.saveAvatarImage(newAvatar, username);
    }

    @Override
    public void doRequest(HttpUtil.HttpResponseListener listener) {
        HashMap<String, String> header = new HashMap<String, String>() {{
            put("username", username);
        }};

        OkHttpUtil.uploadPicture(getUrl(), header, localFileHelper.loadAvatarFile(username), listener);
    }
}
