package com.tnecesoc.MyInfoDemo.Modules.Homepage.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.GlobalView.Interfaces.IShowProfileView;
import com.tnecesoc.MyInfoDemo.GlobalView.ShowProfileViewImpl;
import com.tnecesoc.MyInfoDemo.Modules.Homepage.Tasks.SearchContactTask;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Profile.View.ProfileMainActivity;
import com.tnecesoc.MyInfoDemo.R;

public class SearchContactActivity extends AppCompatActivity implements IShowProfileView {

    private static final int REQUEST_SHOW_PROFILE = 1;

    public static final int RESULT_CONTACT_INTERESTED = 1;
    public static final int RESULT_CONTACT_NOT_INTERESTED = 0;

    private TextView emptyText;
    private View resView;
    private LinearLayout mLayout;
    private EditText txt_username_search;

    private Animation anim_result_found;
    private Animation anim_result_replaced;

    private String curUsername;

    @Override
    public void showProfile(final Profile profile, Profile.Category category) {
        ((SimpleDraweeView) resView.findViewById(R.id.img_list_item_contact_avatar)).setImageURI(Host.findAvatarUrlByUsername(curUsername));
        ((TextView) resView.findViewById(R.id.lbl_list_item_contact_nickname)).setText(profile.getNickname());
        ((TextView) resView.findViewById(R.id.lbl_list_item_contact_motto)).setText(profile.getMotto());

        resView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curUsername.equals(new SessionHelper(SearchContactActivity.this).getSessionAttribute(SessionHelper.KEY_USERNAME))) {
                    startActivity(new Intent(SearchContactActivity.this, ProfileMainActivity.class));
                } else {
                    startActivityForResult(new Intent(SearchContactActivity.this, ShowProfileViewImpl.class).putExtra("username", curUsername), REQUEST_SHOW_PROFILE);
                }
            }
        });

        emptyText.startAnimation(anim_result_replaced);
        emptyText.setVisibility(View.GONE);
        resView.startAnimation(anim_result_found);
        resView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCommunicationData(int contacts, int messages, int posts) {

    }

    @Override
    public void showFetchDataFailed() {
        resView.startAnimation(anim_result_replaced);
        resView.setVisibility(View.GONE);
        emptyText.setVisibility(View.VISIBLE);
        emptyText.startAnimation(anim_result_found);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar_search_contact);

        txt_username_search = (EditText) getSupportActionBar().getCustomView().findViewById(R.id.txt_toolbar_search_contact);

        getSupportActionBar().getCustomView().findViewById(R.id.btn_toolbar_search_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curUsername = txt_username_search.getText().toString();
                System.out.println(txt_username_search.getText().toString());
                new SearchContactTask(SearchContactActivity.this).execute(curUsername);
            }
        });

        mLayout = new LinearLayout(this);
        mLayout.setGravity(Gravity.CENTER);
        mLayout.setBackgroundColor(0xFFECECEC);

        addContentView(mLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        emptyText = new TextView(this);
        emptyText.setText(R.string.hint_username_not_exist);
        emptyText.setVisibility(View.GONE);
        mLayout.addView(emptyText);

        resView = getLayoutInflater().inflate(R.layout.list_item_contact, null, false);
        resView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ((LinearLayout.LayoutParams) resView.getLayoutParams()).setMarginStart(16);
        ((LinearLayout.LayoutParams) resView.getLayoutParams()).setMarginEnd(16);
        resView.setBackgroundResource(R.drawable.layout_selector);
        resView.setVisibility(View.GONE);
        mLayout.addView(resView);

        anim_result_found = AnimationUtils.loadAnimation(this, R.anim.fade_in_from_top_to_center);
        anim_result_found.setDuration(300);
        anim_result_found.setFillAfter(true);

        anim_result_replaced = AnimationUtils.loadAnimation(this, R.anim.fade_out_from_center_to_bottom);
        anim_result_replaced.setDuration(300);
        anim_result_replaced.setFillAfter(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SHOW_PROFILE) {
            setResult(RESULT_CONTACT_INTERESTED);
        } else {
            setResult(RESULT_CONTACT_NOT_INTERESTED);
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
