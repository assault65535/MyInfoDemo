package com.tnecesoc.MyInfoDemo.Modules.Login.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.tnecesoc.MyInfoDemo.Modules.Login.Presenter.SignUpPresenter;
import com.tnecesoc.MyInfoDemo.R;

import java.io.File;

public class SignUpActivity extends AppCompatActivity implements ISignUpView {

    private static final int PHOTO_REQUEST_CAMERA = 1;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private static final int PHOTO_REQUEST_CROP = 3;

    private AlertDialog.Builder dialogWarning;

    private TextInputLayout txt_community;
    private TextInputLayout txt_username;
    private TextInputLayout txt_password;

    private EditText txt_phone_area;
    private EditText txt_phone_number;

    private ImageView img_avatar;

    private SignUpPresenter presenter;

    private File tempFile;

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

        dialogWarning = new AlertDialog.Builder(this);

        txt_phone_area = (EditText) findViewById(R.id.txt_sign_up_phone_area);
        txt_phone_number = (EditText) findViewById(R.id.txt_sign_up_phone);
        txt_community = (TextInputLayout) findViewById(R.id.txt_sign_up_community);
        txt_username = (TextInputLayout) findViewById(R.id.txt_sign_up_username);
        txt_password = (TextInputLayout) findViewById(R.id.txt_sign_up_password);

        img_avatar = (ImageView) findViewById(R.id.img_sign_up_avatar);

        findViewById(R.id.btn_sign_up_sign_up).setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View v) {

                String community = txt_community.getEditText().getText().toString();
                String phone = txt_phone_number.getText().toString();
                String username = txt_username.getEditText().getText().toString();
                String password = txt_password.getEditText().getText().toString();

                presenter.performSignUp(community, phone, username, password);
            }
        });

        findViewById(R.id.img_sign_up_avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogWarning.setTitle("Pick a picture to change avatar");
                dialogWarning.setItems(new String[] {"Take photo", "Choose from album"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                fetchPhotoFromCamera();
                                break;
                            case 1:
                                fetchPhotoFromGallery();
                                break;
                            default:
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                dialogWarning.show();
            }
        });

        presenter = new SignUpPresenter(this);

        txt_phone_number.requestFocus();
    }

    private void fetchPhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    private void fetchPhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        tempFile = new File(Environment.getExternalStorageDirectory(), "___photo");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    private void crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);

        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);

        intent.putExtra("outputFormat", "PNG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CROP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                Uri uri = data.getData();
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            crop(Uri.fromFile(tempFile));
        } else if (requestCode == PHOTO_REQUEST_CROP) {
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                img_avatar.setImageBitmap(bitmap);
            }

            tempFile.delete();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
