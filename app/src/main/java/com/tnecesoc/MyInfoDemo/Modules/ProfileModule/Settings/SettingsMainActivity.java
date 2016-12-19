package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;
import com.tnecesoc.MyInfoDemo.Modules.SplashScreen.View.IndexActivity;
import com.tnecesoc.MyInfoDemo.R;

public class SettingsMainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_main);

        findViewById(R.id.layout_settings_log_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                dialog.setTitle("You sure?")
                        .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new SessionHelper(v.getContext()).terminateSession();
                                startActivity(new Intent(SettingsMainActivity.this, IndexActivity.class));
                                finishAffinity();
                            }
                        }).setNegativeButton("Cancel", null).show();

            }
        });

        dialog = new AlertDialog.Builder(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
