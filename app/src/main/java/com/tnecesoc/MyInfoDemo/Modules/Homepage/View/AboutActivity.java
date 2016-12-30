package com.tnecesoc.MyInfoDemo.Modules.Homepage.View;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.tnecesoc.MyInfoDemo.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener, IAboutView {

    private AlertDialog.Builder dialog;

    @Override
    public void showFeatures() {
        dialog.setTitle(R.string.gui_features);
        dialog.setMessage(R.string.info_new_features);
        dialog.show();
    }

    @Override
    public void showHelpAndFeedback() {
        dialog.setTitle(R.string.gui_help_and_feedback);
        dialog.setMessage(R.string.info_help_and_feedback);
        dialog.show();
    }

    @Override
    public void showVersionCheck() {
        dialog.setTitle(R.string.gui_version_check);
        dialog.setMessage(R.string.info_version_check);
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        dialog = new AlertDialog.Builder(this);
        dialog.setPositiveButton(R.string._continue, null);

        initializeUICallbacks();

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeUICallbacks() {
        findViewById(R.id.layout_about_page_features).setOnClickListener(this);
        findViewById(R.id.layout_about_page_help_and_feedback).setOnClickListener(this);
        findViewById(R.id.layout_about_page_check_version).setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_about_page_features:
                showFeatures();
                break;
            case R.id.layout_about_page_help_and_feedback:
                showHelpAndFeedback();
                break;
            case R.id.layout_about_page_check_version:
                showVersionCheck();
                break;
            default:
                break;
        }
    }
}
