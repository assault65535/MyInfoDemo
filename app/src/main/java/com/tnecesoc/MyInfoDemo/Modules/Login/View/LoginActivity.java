package com.tnecesoc.MyInfoDemo.Modules.Login.View;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.tnecesoc.MyInfoDemo.Modules.Homepage.View.MainActivity;
import com.tnecesoc.MyInfoDemo.Modules.Login.Presenter.LoginPresenter;
import com.tnecesoc.MyInfoDemo.R;

public class LoginActivity extends AppCompatActivity implements ILoginView{

    private EditText txt_username;
    private EditText txt_password;

    private AlertDialog.Builder dialogWarning;

    private LoginPresenter loginPresenter;

    public static final String TAG = "lo";

    @Override
    public void showLoginSuccessful() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void showAccessDenied() {

        dialogWarning.setMessage(R.string.hint_access_denied);
        dialogWarning.show();

    }

    @Override
    public void showNetworkFailure() {
        dialogWarning.setMessage(R.string.hint_network_unavailable);
        dialogWarning.show();
    }

    @Override
    public void showAutoComplete(String username) {
        txt_username.setText(username);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginPresenter(this);

        initializeUI();

        initializeUICallbacks();

        loginPresenter.performAutoComplete(this);
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    private void initializeUICallbacks() {

        findViewById(R.id.btn_login_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.performSignIn(LoginActivity.this, txt_username.getText().toString(), txt_password.getText().toString());
            }
        });

        findViewById(R.id.lbl_login_forget_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "That's funny.", Snackbar.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.lbl_login_new_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        findViewById(R.id.lbl_login_check_term_of_use).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogWarning.setMessage("That's funny.");
                dialogWarning.show();
            }
        });

        findViewById(R.id.img_login_avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.performForceSignIn();
            }
        });

        dialogWarning.setPositiveButton("CONFIRM", null);

        loginPresenter.performAutoComplete(this);

    }

    @SuppressWarnings("deprecation")
    private void initializeUI() {
        txt_username = (EditText) findViewById(R.id.txt_login_username);
        txt_password = (EditText) findViewById(R.id.txt_login_password);

        Drawable icon_input_username = getResources().getDrawable(R.drawable.input_username);
        Drawable icon_input_password = getResources().getDrawable(R.drawable.input_password);

        if (icon_input_username != null) {
            icon_input_username.setBounds(0, 0, txt_username.getLineHeight(), txt_username.getLineHeight());
            txt_username.setCompoundDrawables(icon_input_username, null, null, null);
        }

        if (icon_input_password != null) {
            icon_input_password.setBounds(0, 0, txt_password.getLineHeight(), txt_password.getLineHeight());
            txt_password.setCompoundDrawables(icon_input_password, null, null, null);
        }

        dialogWarning = new AlertDialog.Builder(this);

    }

}
