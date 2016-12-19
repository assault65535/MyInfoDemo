package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import com.tnecesoc.MyInfoDemo.Bean.Container;
import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.LocalContactsHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Model.ViewFollowUserHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Model.ViewFollowerHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Model.ViewFriendListHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.View.IContactListView;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.List;

/**
 * Created by Tnecesoc on 2016/12/12.
 */
public class ViewContactsTask extends AsyncTask<String, Void, ViewContactsTask.Cond> {

    enum Cond {
        SUCCESS, NETWORK_FAILURE
    }

    private LocalContactsHelper helper;
    private IContactListView view;
    private List<ProfileBean> localData, remoteData;
    private ProfileBean.Category category;

    public ViewContactsTask(IContactListView view, Context context, ProfileBean.Category category) {
        this.view = view;
        this.category = category;
        this.helper = new LocalContactsHelper(context);
    }

    @Override
    protected Cond doInBackground(String... params) {

        final Container<Cond> res = new Container<Cond>(Cond.SUCCESS);
        localData = helper.getContactsListByCategory(category);

        publishProgress();

        String username = params[0];

        switch (category) {
            case FRIEND:
                remoteData = new ViewFriendListHelper(username).doQuery(new HttpUtil.HttpErrorListener() {
                    @Override
                    public void onError(Exception e) {
                        res.setValue(Cond.NETWORK_FAILURE);
                    }
                });
                break;
            case FOLLOW:
                remoteData = new ViewFollowUserHelper(username).doQuery(new HttpUtil.HttpErrorListener() {
                    @Override
                    public void onError(Exception e) {
                        res.setValue(Cond.NETWORK_FAILURE);
                    }
                });
                break;
            case FOLLOWER:
                remoteData = new ViewFollowerHelper(username).doQuery(new HttpUtil.HttpErrorListener() {
                    @Override
                    public void onError(Exception e) {
                        res.setValue(Cond.NETWORK_FAILURE);
                    }
                });
                break;
            default:
                break;
        }

        if (res.getValue() == Cond.NETWORK_FAILURE) {
            localData = helper.getContactsListByCategory(category);
        }

        res.setValue(Cond.SUCCESS);

        return res.getValue();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        view.showContactList(localData);
    }

    @Override
    protected void onPostExecute(Cond cond) {

        if (view == null) {
            return;
        }

        if (cond != Cond.NETWORK_FAILURE) {
            localData = remoteData;
        }

        view.showContactList(localData);

    }

}
