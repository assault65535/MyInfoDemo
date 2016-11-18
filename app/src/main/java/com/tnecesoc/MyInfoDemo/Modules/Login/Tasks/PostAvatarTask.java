package com.tnecesoc.MyInfoDemo.Modules.Login.Tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by Tnecesoc on 2016/11/18.
 */
public class PostAvatarTask extends AsyncTask<Bitmap, Void, PostAvatarTask.Cond> {

    enum Cond {
        SUCCESS, NETWORK_FAILURE
    }

    @Override
    protected Cond doInBackground(Bitmap... params) {
        return null;
    }
}
