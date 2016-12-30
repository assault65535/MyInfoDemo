package com.tnecesoc.MyInfoDemo.Modules.Login.Presenter;

import android.content.Context;
import android.os.AsyncTask;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;
import com.tnecesoc.MyInfoDemo.Modules.Login.Tasks.SignInTask;
import com.tnecesoc.MyInfoDemo.Modules.Login.View.ILoginView;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Task.SyncMsgTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.tnecesoc.MyInfoDemo.BuildConfig.DEBUG;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public class LoginPresenter {

    private ILoginView view;
    private int counter = 0;

    private LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();
    private ExecutorService exec = new ThreadPoolExecutor(
                    1, 1, 0L, TimeUnit.MILLISECONDS, blockingQueue
    );

    public LoginPresenter(ILoginView view) {
        this.view = view;
    }

    public void performSignIn(Context context, String username, String password) {

        new SignInTask(context, view).executeOnExecutor(exec, username, password);

    }

    public void performAutoComplete(Context context) {
        SessionHelper session = new SessionHelper(context);
        String username = session.getSessionAttribute(SessionHelper.KEY_USERNAME);
        view.showAutoComplete(username);
    }

    public void performForceSignIn() {
        if (++counter >= 5 && DEBUG) {
            view.showLoginSuccessful();
            counter = 0;
        }
    }

}
