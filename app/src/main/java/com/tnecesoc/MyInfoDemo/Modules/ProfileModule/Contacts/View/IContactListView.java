package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.View;

import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;

import java.util.List;

/**
 * Created by Tnecesoc on 2016/12/12.
 */
public interface IContactListView {

    void showContactList(List<ProfileBean> list);

    void showFetchDataFailed();

}
