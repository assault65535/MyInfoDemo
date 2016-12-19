package com.tnecesoc.MyInfoDemo.GlobalView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;
import com.tnecesoc.MyInfoDemo.GlobalView.Interfaces.IUpdateAvatarView;

import java.io.File;

/**
 * Created by Tnecesoc on 2016/11/24.
 */
public class UpdateAvatarViewImpl extends AppCompatActivity implements IUpdateAvatarView {

    public static final int PHOTO_REQUEST_CAMERA = 1;
    public static final int PHOTO_REQUEST_GALLERY = 2;
    public static final int PHOTO_REQUEST_CROP = 3;

    private File tempFile;
    private AlertDialog.Builder mDialog;
    private ImageView img_avatar;
    private Bitmap currentAvatarImage;

    @Override
    public void showUpdateSuccessful() {
        Toast.makeText(this, "Upload succeed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUpdateFailed() {
        Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mDialog = new AlertDialog.Builder(this);
        tempFile = new File(Environment.getExternalStorageDirectory(), "___photo");

        mDialog.setTitle("Pick a picture to change avatar");
        mDialog.setItems(new String[] {"Take photo", "Choose from album"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        fetchPhotoFromGallery();
                        break;
                    case 1:
                        fetchPhotoFromCamera();
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }
        });
    }

    public Bitmap getCurrentAvatarImage() {
        return currentAvatarImage;
    }

    public void setAvatarView(ImageView view) {
        this.img_avatar = view;
    }

    public void refreshAvatarBitmap(Bitmap bitmap) {
        img_avatar.setImageBitmap(bitmap);
    }

    public void doUpdateAvatar() {
        mDialog.show();
    }

    private void fetchPhotoFromCamera() {
        Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    private void fetchPhotoFromGallery() {
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
            if (tempFile.exists()) {
                crop(Uri.fromFile(tempFile));
            }
        } else if (requestCode == PHOTO_REQUEST_CROP) {
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                refreshAvatarBitmap(bitmap);
                currentAvatarImage = bitmap;
                tempFile.delete();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
