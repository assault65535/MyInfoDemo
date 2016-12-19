package com.tnecesoc.MyInfoDemo.Modules.Homepage.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import com.tnecesoc.MyInfoDemo.Bean.Container;
import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.LocalContactsHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Model.ViewFollowUserHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Model.ViewFollowerHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Model.ViewFriendListHelper;
import com.tnecesoc.MyInfoDemo.Modules.Homepage.View.IMainPageView;

import java.util.ArrayList;

/**
 * Created by Tnecesoc on 2016/12/12.
 */
public class FetchContactsCountTask extends AsyncTask<String, Void, Integer> {

    IMainPageView view;
    LocalContactsHelper helper;

    public FetchContactsCountTask(IMainPageView view, Context context) {
        this.view = view;
        this.helper = new LocalContactsHelper(context);
    }

    @Override
    protected Integer doInBackground(String... params) {

        final Container<Integer> res = new Container<>(0);
        String username = params[0];

        ArrayList<ProfileBean> data = new ArrayList<ProfileBean>();

        helper.putAllProfileInCategory(new ViewFriendListHelper(username).doQuery(null), ProfileBean.Category.FRIEND);
        helper.putAllProfileInCategory(new ViewFollowUserHelper(username).doQuery(null), ProfileBean.Category.FOLLOW);
        helper.putAllProfileInCategory(new ViewFollowerHelper(username).doQuery(null), ProfileBean.Category.FOLLOWER);

        res.setValue(helper.getContactsListByCategory(ProfileBean.Category.ARBITRARY).size());

        return res.getValue();
    }

    @Override
    protected void onPostExecute(Integer integer) {

        view.showCurrentContacts(integer, false);
        helper.close();

    }
}
