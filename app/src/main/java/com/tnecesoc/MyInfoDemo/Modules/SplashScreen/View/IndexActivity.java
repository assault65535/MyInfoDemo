package com.tnecesoc.MyInfoDemo.Modules.SplashScreen.View;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.tnecesoc.MyInfoDemo.Modules.Homepage.View.MainActivity;
import com.tnecesoc.MyInfoDemo.Modules.Login.View.LoginActivity;
import com.tnecesoc.MyInfoDemo.Modules.SplashScreen.Presenter.IndexPresenter;
import com.tnecesoc.MyInfoDemo.R;
import com.tnecesoc.MyInfoDemo.Modules.Homepage.View.NavigateActivity;

public class IndexActivity extends AppCompatActivity implements ILoadingView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initializeUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new IndexPresenter(this).performIndexing(this);
    }

    @Override
    public void requestLogin() {
        startActivity(new Intent(IndexActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void showResume() {
        startActivity(new Intent(IndexActivity.this, MainActivity.class));
        finish();
    }

    private void initializeUI() {

        TextView lbl_hi = (TextView) findViewById(R.id.lbl_index_hi);
        TextView lbl_meet_with_your = (TextView) findViewById(R.id.lbl_index_meet_with_your);
        TextView lbl_neighbor = (TextView) findViewById(R.id.lbl_index_neighbors);

        if (lbl_hi != null) {
            lbl_hi.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/comic.ttf"));
        }
//        if (lbl_meet_with_your != null) {
//            lbl_meet_with_your.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/microsoft_yahei_light.ttf"));
//        }
//        if (lbl_neighbor != null) {
//            lbl_neighbor.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/microsoft_yahei_light.ttf"));
//        }

        findViewById(R.id.lbl_index_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(IndexActivity.this, getString(R.string.hint_onSkip), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
