package com.tnecesoc.MyInfoDemo.Modules.Index.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import com.tnecesoc.MyInfoDemo.Modules.Login.View.LoginActivity;
import com.tnecesoc.MyInfoDemo.Modules.Index.Presenter.IndexPresenter;
import com.tnecesoc.MyInfoDemo.R;
import com.tnecesoc.MyInfoDemo.Modules.NavigatePage.View.NavigateActivity;

public class IndexActivity extends AppCompatActivity implements ILoadingView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new IndexPresenter(this).performIndexing();

    }

    @Override
    public void requestLogin() {
        startActivity(new Intent(IndexActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void showResume() {
        startActivity(new Intent(IndexActivity.this, NavigateActivity.class));
        finish();
    }
}
