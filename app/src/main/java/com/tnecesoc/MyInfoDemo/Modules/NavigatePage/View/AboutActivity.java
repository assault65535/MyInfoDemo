package com.tnecesoc.MyInfoDemo.Modules.NavigatePage.View;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.tnecesoc.MyInfoDemo.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener, IAboutView {

    private AlertDialog.Builder dialog;

    @Override
    public void showFeatures() {
        dialog.setTitle(R.string.gui_features);
        dialog.setMessage("Small but continuous change is happening...");
        dialog.show();
    }

    @Override
    public void showHelpAndFeedback() {
        dialog.setTitle("For help and feedback");
        dialog.setMessage("Contact yourself");
        dialog.show();
    }

    @Override
    public void showVersionCheck() {
        dialog.setTitle("Version");
        dialog.setMessage("Alpha test work in progress");
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        dialog = new AlertDialog.Builder(this);
        dialog.setPositiveButton("CONFIRM", null);

        initializeUICallbacks();
    }

    private void initializeUICallbacks() {
        findViewById(R.id.layout_about_page_features).setOnClickListener(this);
        findViewById(R.id.layout_about_page_help_and_feedback).setOnClickListener(this);
        findViewById(R.id.layout_about_page_check_version).setOnClickListener(this);
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
