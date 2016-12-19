package com.tnecesoc.MyInfoDemo.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.Modules.Homepage.Presenter.NavigatePresenter;
import com.tnecesoc.MyInfoDemo.Modules.Homepage.View.AboutActivity;
import com.tnecesoc.MyInfoDemo.Modules.Homepage.View.IMainPageView;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Contacts.View.ContactsMainActivity;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Profile.View.ProfileMainActivity;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Settings.SettingsMainActivity;
import com.tnecesoc.MyInfoDemo.R;

import java.util.Locale;

/**
 * Created by Tnecesoc on 2016/12/16.
 */
public class NavigateFrame implements IMainPageView {

    public static final int REQUEST_CHANGE_PROFILE = 1;

    private TextView txt_username;
    private TextView txt_nickname;
    private TextView txt_motto;
    private TextView txt_contact_count, txt_message_count, txt_post_count;
    private SimpleDraweeView img_avatar;
    private ImageView img_gender;

    private NavigatePresenter presenter;

    public NavigateFrame(View holder, View.OnClickListener listener) {
        onCreate(holder);
        registerOnClickListener(holder, listener);
    }

    public NavigateFrame(Activity holder, View.OnClickListener listener) {
        this(holder.getWindow().getDecorView().getRootView(), listener);
    }

    // TODO implementation of the reminder
    @Override
    public void showCurrentContacts(int count, boolean shouldEmphasize) {
        txt_contact_count.setText(String.format(Locale.getDefault(), "%d", count));
    }

    @Override
    public void showCurrentMessages(int count, boolean shouldEmphasize) {
        txt_message_count.setText(String.format(Locale.getDefault(), "%d", count));
    }

    @Override
    public void showCurrentPosts(int count, boolean shouldEmphasize) {
        txt_post_count.setText(String.format(Locale.getDefault(), "%d", count));
    }

    @SuppressWarnings("Duplicates")
    @SuppressLint("SetTextI18n")
    @Override
    public void showUserInfo(ProfileBean profileBean) {
        txt_username.setText("@" + profileBean.getUsername());
        txt_nickname.setText(profileBean.getNickname());
        txt_motto.setText(profileBean.getMotto());

        Uri avatar_uri = Uri.parse(Host.URL + "/user-avatars/" + profileBean.getUsername() + ".png");

        img_avatar.setImageURI(avatar_uri);

        switch (profileBean.getGender()) {
            case "MALE":
                img_gender.setImageResource(R.drawable.male);
                break;
            case "FEMALE":
                img_gender.setImageResource(R.drawable.female);
                break;
            default:
                img_gender.setVisibility(View.GONE);
                break;
        }
    }


    public void onCreate(View holder) {

        presenter = new NavigatePresenter(this);

        txt_username = (TextView) holder.findViewById(R.id.lbl_profile_page_username);
        txt_nickname = (TextView) holder.findViewById(R.id.lbl_profile_page_nickname);
        txt_motto = (TextView) holder.findViewById(R.id.lbl_profile_page_motto);
        txt_contact_count = (TextView) holder.findViewById(R.id.lbl_profile_page_contacts_count);
        txt_message_count = (TextView) holder.findViewById(R.id.lbl_profile_page_message_count);
        txt_post_count = (TextView) holder.findViewById(R.id.lbl_profile_page_posts_count);
        img_avatar = (SimpleDraweeView) holder.findViewById(R.id.img_profile_page_avatar);
        img_gender = (ImageView) holder.findViewById(R.id.img_profile_page_gender);

        presenter.performFetchUserInfo(holder.getContext());
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    public void registerOnClickListener(View holder, View.OnClickListener listener) {
        holder.findViewById(R.id.layout_main_page_about).setOnClickListener(listener);
        holder.findViewById(R.id.layout_main_page_download_files).setOnClickListener(listener);
        holder.findViewById(R.id.layout_main_page_favourite).setOnClickListener(listener);
        holder.findViewById(R.id.layout_main_page_profile).setOnClickListener(listener);
        holder.findViewById(R.id.layout_main_page_settings).setOnClickListener(listener);

        holder.findViewById(R.id.layout_profile_page_posts).setOnClickListener(listener);
        holder.findViewById(R.id.layout_profile_page_contacts).setOnClickListener(listener);
        holder.findViewById(R.id.layout_profile_page_messages).setOnClickListener(listener);
        holder.findViewById(R.id.img_profile_page_avatar).setOnClickListener(listener);
    }

    public void onClick(Activity holder, View v) {
        switch (v.getId()) {
            case R.id.layout_main_page_about:
                holder.startActivity(new Intent(holder, AboutActivity.class));
                break;
            case R.id.layout_main_page_profile:
                holder.startActivityForResult(new Intent(holder, ProfileMainActivity.class), REQUEST_CHANGE_PROFILE);
                break;
            case R.id.layout_main_page_favourite:
//                holder.startActivity(new Intent(NavigateActivity.this, FavouriteMainActivity.class));
                break;
            case R.id.layout_main_page_download_files:
//                holder.startActivity(new Intent(NavigateActivity.this, DownloadFilesMainActivity.class));
                break;
            case R.id.layout_main_page_settings:
                holder.startActivity(new Intent(holder, SettingsMainActivity.class));
                break;
            case R.id.layout_profile_page_contacts:
                holder.startActivity(new Intent(holder, ContactsMainActivity.class));
                break;
            case R.id.layout_profile_page_messages:
//                holder.startActivity(new Intent(NavigateActivity.this, MessagesMainActivity.class));
                break;
            case R.id.layout_profile_page_posts:
//                holder.startActivity(new Intent(NavigateActivity.this, PostsMainActivity.class));
                break;
            case R.id.img_profile_page_avatar:
                // TODO view avatar widget
                break;
            default:
                break;
        }
    }

    public void onActivityResult(Activity holder, int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHANGE_PROFILE) {
            if (resultCode != ProfileMainActivity.RESULT_NOTHING_CHANGED) {
                presenter.performFetchUserInfo(holder.getApplicationContext());
            }
        }
    }

}
