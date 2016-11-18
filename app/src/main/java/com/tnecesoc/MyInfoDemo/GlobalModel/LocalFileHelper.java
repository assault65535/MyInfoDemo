package com.tnecesoc.MyInfoDemo.GlobalModel;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Tnecesoc on 2016/11/18.
 */
public class LocalFileHelper {

    private static final String PATH = "Neighbours";

    private File ImageDirectory;

    public LocalFileHelper() {
        ImageDirectory = new File(Environment.getExternalStorageDirectory(), PATH + "/Images");
        if (!ImageDirectory.exists()) {
            ImageDirectory.mkdirs();
        }
    }

    public void saveImageFile(Bitmap bmp, String name) {

        File file = new File(ImageDirectory, name + ".png");
        try {
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
