package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;
import com.tnecesoc.MyInfoDemo.GlobalView.ShowProfileViewImpl;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.Presenter.ContactsPagePresenter;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.View.Utils.ContactsListAdapter;
import com.tnecesoc.MyInfoDemo.R;

import java.util.ArrayList;
import java.util.List;

import static com.tnecesoc.MyInfoDemo.Bean.ProfileBean.Category.*;

/**
 * Created by Tnecesoc on 2016/12/12.
 */
public class ContactsFragment extends ListFragment implements IContactListView {

    public static final String REQUEST_SYNC = "sync";

    private ProfileBean.Category mCategory;
    private ContactsListAdapter mContactsListAdapter;
    private ContactsPagePresenter mPresenter;

    @Override
    public void showContactList(@NonNull List<ProfileBean> list) {

        if (getActivity() == null) {
            return;
        }

        if (list.isEmpty()) {
            setEmptyText(getString(R.string.hint_no_such_contact));
        }

        mContactsListAdapter = new ContactsListAdapter(new ArrayList<ProfileBean>(), getActivity().getLayoutInflater());
        mContactsListAdapter.addAllItem(list);
        setListAdapter(mContactsListAdapter);
    }

    @Override
    public void showFetchDataFailed() {

        if (getActivity() == null) {
            return;
        }

        setEmptyText(getString(R.string.hint_network_unavailable));
        setListAdapter(mContactsListAdapter);
    }

    public ContactsFragment() {
        mCategory = ARBITRARY;
    }

    public ContactsFragment setCategory(ProfileBean.Category category) {
        mCategory = category;
        return this;
    }

    public String getCategory(Context context) {
        switch (mCategory) {
            case FOLLOW:
                return context.getString(R.string.tab_title_follow);
            case FOLLOWER:
                return context.getString(R.string.tab_title_follower);
            case FRIEND:
                return context.getString(R.string.tab_title_friend);
            default:
                return ARBITRARY.name();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = new ContactsPagePresenter(this);
        mPresenter.performFetchContactsInfo(context, mCategory);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ShowProfileViewImpl.RESULT_FOLLOW_STATE_CHANGED) {
            getActivity().sendBroadcast(new Intent(REQUEST_SYNC));
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ProfileBean profile = (ProfileBean) l.getAdapter().getItem(position);

        Intent intent = new Intent(getActivity(), ShowProfileViewImpl.class);
        intent.putExtra("username", profile.getUsername());
        startActivityForResult(intent, mCategory.ordinal());
    }



}
