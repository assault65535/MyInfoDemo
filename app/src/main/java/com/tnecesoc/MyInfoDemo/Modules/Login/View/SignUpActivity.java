package com.tnecesoc.MyInfoDemo.Modules.Login.View;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.tnecesoc.MyInfoDemo.R;

public class SignUpActivity extends AppCompatActivity implements ISignUpView {

    private AlertDialog.Builder dialogWarning;

    @Override
    public void showSignInSuccessful() {

    }

    @Override
    public void showUsernameConflicted() {
        dialogWarning.setMessage("The username has been taken");
        dialogWarning.show();
    }

    @Override
    public void showNotInvited() {
        dialogWarning.setMessage("The phone hasn't invited by the community");
        dialogWarning.show();
    }

    @Override
    public void showSignInFailed() {
        dialogWarning.setMessage("Network unavailable");
        dialogWarning.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dialogWarning = new AlertDialog.Builder(this);
    }
}
