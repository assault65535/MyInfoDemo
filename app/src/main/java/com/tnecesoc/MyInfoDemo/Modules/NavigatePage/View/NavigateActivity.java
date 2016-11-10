package com.tnecesoc.MyInfoDemo.Modules.NavigatePage.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.tnecesoc.MyInfoDemo.R;

public class NavigateActivity extends AppCompatActivity implements IMainPageView, View.OnClickListener {

    //TODO implementation of reminder
    @Override
    public void showNewContacts() {

    }

    @Override
    public void showNewMessages() {

    }

    @Override
    public void showNewPosts() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        initializeUICallbacks();
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    private void initializeUICallbacks() {
        findViewById(R.id.layout_main_page_about).setOnClickListener(this);
        findViewById(R.id.layout_main_page_download_files).setOnClickListener(this);
        findViewById(R.id.layout_main_page_favourite).setOnClickListener(this);
        findViewById(R.id.layout_main_page_profile).setOnClickListener(this);
        findViewById(R.id.layout_main_page_settings).setOnClickListener(this);
        findViewById(R.id.layout_main_page_posts).setOnClickListener(this);
        findViewById(R.id.layout_main_page_contacts).setOnClickListener(this);
        findViewById(R.id.layout_main_page_messages).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_main_page_about:
                startActivity(new Intent(NavigateActivity.this, AboutActivity.class));
                break;
            case R.id.layout_main_page_profile:
//                startActivity(new Intent(NavigateActivity.this, ProfileMainActivity.class));
                break;
            case R.id.layout_main_page_favourite:
//                startActivity(new Intent(NavigateActivity.this, FavouriteMainActivity.class));
                break;
            case R.id.layout_main_page_download_files:
//                startActivity(new Intent(NavigateActivity.this, DownloadFilesMainActivity.class));
                break;
            case R.id.layout_main_page_settings:
//                startActivity(new Intent(NavigateActivity.this, SettingsMainActivity.class));
                break;
            case R.id.layout_main_page_contacts:
//                startActivity(new Intent(NavigateActivity.this, ContactsMainActivity.class));
                break;
            case R.id.layout_main_page_messages:
//                startActivity(new Intent(NavigateActivity.this, MessagesMainActivity.class));
                break;
            case R.id.layout_main_page_posts:
//                startActivity(new Intent(NavigateActivity.this, PostsMainActivity.class));
                break;
            default:
                break;
        }
    }
}
