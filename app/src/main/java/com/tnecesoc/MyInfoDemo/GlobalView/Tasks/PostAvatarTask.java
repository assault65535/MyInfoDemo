package com.tnecesoc.MyInfoDemo.GlobalView.Tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.tnecesoc.MyInfoDemo.Bean.Container;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.UpdateAvatarHelper;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;
import com.tnecesoc.MyInfoDemo.GlobalView.Interfaces.IUpdateAvatarView;

/**
 * Created by Tnecesoc on 2016/11/24.
 */
public class PostAvatarTask extends AsyncTask<String, Void, PostAvatarTask.Cond> {

    enum Cond {
        SUCCESS, NETWORK_FAILURE
    }

    private Bitmap bitmap;
    private IUpdateAvatarView context;

    public PostAvatarTask(IUpdateAvatarView context, Bitmap bitmap) {
        this.context = context;
        this.bitmap = bitmap;
    }

    @Override
    protected Cond doInBackground(String... params) {

        String username = params[0];

        if (username == null) {
            username = new SessionHelper(context.getContext()).getSessionAttribute(SessionHelper.KEY_USERNAME);
        }

        final Container<Cond> ans = new Container<>(Cond.NETWORK_FAILURE);

        new UpdateAvatarHelper(username, bitmap).doRequest(new HttpUtil.HttpResponseListener() {
            @Override
            public void onSuccess(String responseContent) {
                ans.setValue(Boolean.valueOf(responseContent) ? Cond.SUCCESS : Cond.NETWORK_FAILURE);
            }

            @Override
            public void onError(Exception e) {
                ans.setValue(Cond.NETWORK_FAILURE);
            }
        });

        return ans.getValue();
    }

    @Override
    protected void onPostExecute(Cond cond) {
        if (cond == Cond.SUCCESS) {
            context.showUpdateSuccessful();
        } else {
            context.showUpdateFailed();
        }
    }

}
