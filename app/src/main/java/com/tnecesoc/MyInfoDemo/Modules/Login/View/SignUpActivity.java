package com.tnecesoc.MyInfoDemo.Modules.Login.View;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.tnecesoc.MyInfoDemo.Modules.Login.Presenter.SignUpPresenter;
import com.tnecesoc.MyInfoDemo.R;
import com.tnecesoc.MyInfoDemo.GlobalView.UpdateAvatarViewImpl;

public class SignUpActivity extends UpdateAvatarViewImpl implements ISignUpView {

    private TextInputLayout txt_community;
    private TextInputLayout txt_username;
    private TextInputLayout txt_password;

    private EditText txt_phone_area;
    private EditText txt_phone_number;

    private ImageView img_avatar;

    private SignUpPresenter presenter;

    private boolean isAvatarChanged = false;

    @Override
    public void doUpdateAvatar() {
        super.doUpdateAvatar();
        isAvatarChanged = true;
    }

    @Override
    public void showSignInSuccessful() {
        Toast.makeText(SignUpActivity.this, "Signed up successfully.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showUsernameConflicted() {
        txt_username.setError("Username already exist.");
        txt_username.setErrorEnabled(true);
    }

    @Override
    public void showNotInvited() {
        txt_community.setError("You are not invited by this community.");
        txt_community.setErrorEnabled(true);
    }

    @Override
    public void showNetworkFailure() {
        Toast.makeText(SignUpActivity.this, "The Network is not available.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPhoneEntered() {
        txt_community.setVisibility(View.VISIBLE);
        txt_username.setVisibility(View.VISIBLE);
        txt_password.setVisibility(View.VISIBLE);
        img_avatar.setVisibility(View.VISIBLE);
        ((Button) findViewById(R.id.btn_sign_up_sign_up)).setText(R.string.sign_up);
    }

    @Override
    public void showPhoneNotEntered() {
        Toast.makeText(SignUpActivity.this, "Please Enter your phone first.", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txt_phone_area = (EditText) findViewById(R.id.txt_sign_up_phone_area);
        txt_phone_number = (EditText) findViewById(R.id.txt_sign_up_phone);
        txt_community = (TextInputLayout) findViewById(R.id.txt_sign_up_community);
        txt_username = (TextInputLayout) findViewById(R.id.txt_sign_up_username);
        txt_password = (TextInputLayout) findViewById(R.id.txt_sign_up_password);

        img_avatar = (ImageView) findViewById(R.id.img_sign_up_avatar);
        setAvatarView(img_avatar);

        findViewById(R.id.btn_sign_up_sign_up).setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View v) {

                String community = txt_community.getEditText().getText().toString();
                String phone = txt_phone_number.getText().toString();
                String username = txt_username.getEditText().getText().toString();
                String password = txt_password.getEditText().getText().toString();

                presenter.performSignUp(community, phone, username, password, getCurrentAvatarImage());
            }
        });

        findViewById(R.id.img_sign_up_avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdateAvatar();
            }
        });

        presenter = new SignUpPresenter(this);
        txt_phone_number.requestFocus();

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
