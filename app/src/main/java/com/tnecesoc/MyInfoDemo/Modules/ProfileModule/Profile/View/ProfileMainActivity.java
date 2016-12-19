package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Profile.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.GlobalView.UpdateAvatarViewImpl;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Profile.Presenter.ProfileMainPresenter;
import com.tnecesoc.MyInfoDemo.R;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Map;

public class ProfileMainActivity extends UpdateAvatarViewImpl implements View.OnClickListener, IShowUserProfileView {

    public static final int RESULT_NOTHING_CHANGED = 0;
    public static final int RESULT_PROFILE_CHANGED = 1;

    public static boolean isProfileChanged = false;

    private AlertDialog.Builder mEditGenderDialog, mWarningDialog, mEditTextDialog;
    private AlertDialog mEditTextDialogRef;

    private SimpleDraweeView img_avatar;
    private Uri avatar_uri;

    private ProfileMainPresenter presenter;

    private ProfileBean backup, newProfile;

    private Map<String, TextView> info;

    @Override
    public void showProfile(ProfileBean profileBean) {

        backup = profileBean;

        Field field;
        for (Map.Entry<String, TextView> entry : info.entrySet()){
            try {
                field = ProfileBean.class.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                String content = (String) field.get(profileBean);
                if (content != null) {
                    entry.getValue().setText(content);
                } else {
                    entry.getValue().setText("");
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        avatar_uri = Uri.parse(Host.URL + "/user-avatars/" + profileBean.getUsername() + ".png");

        img_avatar.setImageURI(avatar_uri);

        newProfile = getProfileFromInput();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        img_avatar = (SimpleDraweeView) findViewById(R.id.img_profile_avatar);

        setAvatarView(img_avatar);

        initializeUI();

        initializeUICallbacks();
        presenter = new ProfileMainPresenter(this);
        presenter.performFetchProfile();
        initDialog();

        setResult(RESULT_NOTHING_CHANGED);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStop() {

        info.clear();
        mEditTextDialogRef = null;

        super.onStop();
    }

    @Override
    public void refreshAvatarBitmap(Bitmap bitmap) {
        Fresco.getImagePipeline().evictFromCache(avatar_uri);
        Uri new_uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
        img_avatar.setImageURI(new_uri);
        isProfileChanged = true;
    }

    private void initializeUI() {

        info = new Hashtable<String, TextView>(){{
            put("nickname", (TextView) findViewById(R.id.lbl_profile_nickname));
            put("motto", (TextView) findViewById(R.id.lbl_profile_motto));
            put("email", (TextView) findViewById(R.id.lbl_profile_email));
            put("address", (TextView) findViewById(R.id.lbl_profile_address));
            put("phone", (TextView) findViewById(R.id.lbl_profile_phone));
            put("community", (TextView) findViewById(R.id.lbl_profile_community));
            put("gender", (TextView) findViewById(R.id.lbl_profile_gender));
        }};

    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    private void initializeUICallbacks() {

        findViewById(R.id.img_profile_avatar).setOnClickListener(this);
        findViewById(R.id.layout_profile_nickname).setOnClickListener(this);
        findViewById(R.id.layout_profile_motto).setOnClickListener(this);
        findViewById(R.id.layout_profile_email).setOnClickListener(this);
        findViewById(R.id.layout_profile_address).setOnClickListener(this);
        findViewById(R.id.layout_profile_phone).setOnClickListener(this);
        findViewById(R.id.layout_profile_community).setOnClickListener(this);
        findViewById(R.id.layout_profile_password).setOnClickListener(this);
        findViewById(R.id.layout_profile_gender).setOnClickListener(this);
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
            case R.id.layout_profile_nickname:
                doEditProfile("nickname");
                break;
            case R.id.layout_profile_motto:
                doEditProfile("motto");
                break;
            case R.id.layout_profile_gender:
                mEditGenderDialog.show();
                break;
            case R.id.layout_profile_email:
                doEditProfile("email");
                break;
            case R.id.layout_profile_address:
                doEditProfile("address");
                break;
            case R.id.layout_profile_phone:
            case R.id.layout_profile_community:
            case R.id.layout_profile_password:
                mWarningDialog.show();
                break;
            case R.id.img_profile_avatar:
                doUpdateAvatar();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == PHOTO_REQUEST_CROP) {
            Bitmap bitmap = data.getParcelableExtra("data");
            presenter.performUpdateAvatar(bitmap);
        }
    }

    private void notifyProfileChanged() {
        newProfile = getProfileFromInput();
        isProfileChanged = true;
        presenter.performUpdateProfile(newProfile);
        setResult(RESULT_PROFILE_CHANGED);
    }

    private ProfileBean getProfileFromInput() {
        ProfileBean res = new ProfileBean();
        Field f;
        try {
            for (Map.Entry<String, TextView> entry : info.entrySet()) {
                f = ProfileBean.class.getDeclaredField(entry.getKey());
                f.setAccessible(true);
                f.set(res, entry.getValue().getText().toString());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        res.setUsername(backup.getUsername());

        return res;
    }

    private void doEditProfile(final String key) {
        mEditTextDialog.setTitle("New " + key);
        mEditTextDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final EditText text = (EditText) mEditTextDialogRef.findViewById(R.id.txt_dialog_edittext);
                assert text != null;
                info.get(key).setText(text.getText().toString());
                notifyProfileChanged();
            }
        });

        mEditTextDialogRef = mEditTextDialog.show();
        CharSequence defaultAttr = info.get(key).getText();
        final EditText text = (EditText) mEditTextDialogRef.findViewById(R.id.txt_dialog_edittext);

        assert text != null;
        text.setText(defaultAttr);
        text.setSelection(defaultAttr.length());
    }

    private void initDialog() {

        mWarningDialog = new AlertDialog.Builder(this);
        mWarningDialog.setTitle("To change protected profile");
        mWarningDialog.setMessage("Please find the the governor of your community");
        mWarningDialog.setPositiveButton("Confirm", null);

        final String[] choices = new String[]{"MALE", "FEMALE", "SECRET"};
        int defaultChoice = 0;
        switch (backup.getGender()) {
            case "MALE":
                break;
            case "FEMALE":
                defaultChoice = 1;
                break;
            case "SECRET":
                defaultChoice = 2;
                break;
            default:
                break;
        }
        mEditGenderDialog = new AlertDialog.Builder(this);
        mEditGenderDialog.setTitle("What's Your gender?");
        mEditGenderDialog.setSingleChoiceItems(choices, defaultChoice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                info.get("gender").setText(choices[which]);
                notifyProfileChanged();
            }
        });
        mEditGenderDialog.setPositiveButton("Confirm", null);

        mEditTextDialog = new AlertDialog.Builder(this);
        mEditTextDialog.setView(R.layout.dialog_edittext_view);
    }

}
