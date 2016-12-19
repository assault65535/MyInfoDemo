package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Presenter;

import android.content.Context;
import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.LocalContactsHelper;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Tasks.ViewContactsTask;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.View.ContactsFragment;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.View.IContactListView;

/**
 * Created by Tnecesoc on 2016/12/12.
 */
public class ContactsPagePresenter {

    IContactListView view;

    public ContactsPagePresenter(IContactListView view) {
        this.view = view;
    }

    public void performFetchContactsInfo(Context context, ProfileBean.Category category) {
        String username = new SessionHelper(context).getSessionAttribute(SessionHelper.KEY_USERNAME);

        new ViewContactsTask(view, context, category).execute(username);
    }
}
