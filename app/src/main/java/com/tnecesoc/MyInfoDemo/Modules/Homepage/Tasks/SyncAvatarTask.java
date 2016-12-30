package com.tnecesoc.MyInfoDemo.Modules.Homepage.Tasks;

import android.net.Uri;
import android.os.AsyncTask;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;

/**
 * Created by Tnecesoc on 2016/12/27.
 */
public class SyncAvatarTask extends AsyncTask<Void, Void, Void> {

    private SimpleDraweeView img_avatar;
    private Uri avatarNetSource;

    public SyncAvatarTask(SimpleDraweeView img_avatar, Uri avatarNetSource) {
        this.img_avatar = img_avatar;
        this.avatarNetSource = avatarNetSource;
    }

    @Override
    protected void onPreExecute() {
        img_avatar.setImageURI(avatarNetSource);
    }

    @Override
    protected Void doInBackground(Void... params) {

        ImagePipeline imgLine = Fresco.getImagePipeline();

        imgLine.evictFromMemoryCache(avatarNetSource);
        imgLine.evictFromDiskCache(avatarNetSource);

        boolean evicting = true;

        while (evicting) {
            evicting = imgLine.isInDiskCacheSync(avatarNetSource);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void o) {
        img_avatar.setImageURI(avatarNetSource);
    }
}
