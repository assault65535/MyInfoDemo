package com.tnecesoc.MyInfoDemo.GlobalView.Interfaces;

import android.content.Context;
import com.tnecesoc.MyInfoDemo.Entity.Profile;

/**
 * Created by Tnecesoc on 2016/12/14.
 */
public interface IShowProfileView {

    void showProfile(Profile profile, Profile.Category category);

    void showCommunicationData(int contacts, int messages, int posts);

    void showFetchDataFailed();

    Context getContext();

}
