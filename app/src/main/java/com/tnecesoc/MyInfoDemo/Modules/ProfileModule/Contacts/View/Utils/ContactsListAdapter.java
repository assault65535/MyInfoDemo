package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.View.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.R;
import org.w3c.dom.Text;

import java.util.Collection;
import java.util.List;

/**
 * Created by Tnecesoc on 2016/12/12.
 */
public class ContactsListAdapter extends BaseAdapter {

    private List<ProfileBean> mContacts;
    private LayoutInflater mInflater;

    public ContactsListAdapter(List<ProfileBean> contacts, LayoutInflater inflater) {
        this.mContacts = contacts;
        this.mInflater = inflater;
    }

    public void addItem(ProfileBean profileBean) {
        mContacts.add(profileBean);
        notifyDataSetChanged();
    }

    public void addAllItem(Collection<? extends ProfileBean> collection) {
        mContacts.addAll(collection);
        notifyDataSetChanged();
    }

    public void clearItem() {
        mContacts.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    @Override
    public ProfileBean getItem(int position) {
        return mContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = mInflater.inflate(R.layout.list_item_contact, parent, false);
        }

        SimpleDraweeView img_avatar = (SimpleDraweeView) view.findViewById(R.id.img_list_item_contact_avatar);
        TextView lbl_nickname = (TextView) view.findViewById(R.id.lbl_list_item_contact_nickname);
        TextView lbl_motto = (TextView) view.findViewById(R.id.lbl_list_item_contact_motto);

        img_avatar.setImageURI(Host.findAvatarUrlByUsername(mContacts.get(position).getUsername()));
        lbl_nickname.setText(mContacts.get(position).getNickname());
        lbl_motto.setText(mContacts.get(position).getMotto());

        return view;
    }
}
