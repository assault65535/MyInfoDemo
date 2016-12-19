package com.tnecesoc.MyInfoDemo.GlobalModel.Local;

import android.graphics.Bitmap;
import android.os.Environment;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Tnecesoc on 2016/11/18.
 */
public class LocalFileHelper {

    private static final String PATH = "Neighbours";

    private File avatarImageDirectory;

    public LocalFileHelper() {
        avatarImageDirectory = new File(Environment.getExternalStorageDirectory(), PATH + "/user-avatars");
        if (!avatarImageDirectory.exists()) {
            avatarImageDirectory.mkdirs();
        }
    }

    public void saveAvatarImage(Bitmap avatar, String username) {

        File file = new File(avatarImageDirectory, username + ".png");

        try {
            FileOutputStream out = new FileOutputStream(file);
            avatar.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isAvatarImageExist(String username) {
        return new File(avatarImageDirectory, username + ".png").exists();
    }

    @Deprecated
    public Bitmap loadAvatarImage(String username) {
        return HttpUtil.downloadPicture(PATH + "/user-avatars/" + username + ".png");
    }

    public File loadAvatarFile(String username) {
        return new File(avatarImageDirectory, username + ".png");
    }

}
