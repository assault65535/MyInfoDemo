package com.tnecesoc.MyInfoDemo.Modules.Homepage.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import com.tnecesoc.MyInfoDemo.Entity.Container;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.LocalContactsHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Model.ViewFollowUserHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Model.ViewFollowerHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Model.ViewFriendListHelper;
import com.tnecesoc.MyInfoDemo.Modules.Homepage.View.INavView;

import java.util.ArrayList;

/**
 * Created by Tnecesoc on 2016/12/12.
 */
public class SyncContactsCountTask extends AsyncTask<String, Void, Integer> {

    INavView view;
    LocalContactsHelper helper;

    public SyncContactsCountTask(INavView view, Context context) {
        this.view = view;
        this.helper = new LocalContactsHelper(context);
    }

    @Override
    protected void onPreExecute() {

        view.showCurrentContacts(helper.getContactsListByCategory(Profile.Category.ARBITRARY).size(), false);

    }

    @Override
    protected Integer doInBackground(String... params) {

        final Container<Integer> res = new Container<>(0);
        String username = params[0];

        ArrayList<Profile> data = new ArrayList<Profile>();

        helper.putAllProfileInCategory(new ViewFriendListHelper(username).doQuery(null), Profile.Category.FRIEND);
        helper.putAllProfileInCategory(new ViewFollowUserHelper(username).doQuery(null), Profile.Category.FOLLOW);
        helper.putAllProfileInCategory(new ViewFollowerHelper(username).doQuery(null), Profile.Category.FOLLOWER);

        res.setValue(helper.getContactsListByCategory(Profile.Category.ARBITRARY).size());

        return res.getValue();
    }

    @Override
    protected void onPostExecute(Integer integer) {

        view.showCurrentContacts(integer, false);
        helper.close();

    }
}
