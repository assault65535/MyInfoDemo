package com.tnecesoc.MyInfoDemo.Modules.Homepage.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.tnecesoc.MyInfoDemo.R;
import com.tnecesoc.MyInfoDemo.Utils.NavigateViewHolder;

@Deprecated
public class NavigateActivity extends AppCompatActivity implements View.OnClickListener {

    private NavigateViewHolder mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);
        mViewModel = new NavigateViewHolder(this, this);
    }

    @Override
    public void onClick(View v) {
        mViewModel.onClick(this, v);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.onActivityResult(this, requestCode, resultCode, data);
    }
}
