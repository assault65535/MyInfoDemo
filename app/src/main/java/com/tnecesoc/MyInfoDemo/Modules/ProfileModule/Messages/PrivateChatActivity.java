package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Presenter.PrivateChatPresenter;
import com.tnecesoc.MyInfoDemo.R;
import com.tnecesoc.MyInfoDemo.Utils.InstantMessageService;

import java.util.Date;
import java.util.List;

public class PrivateChatActivity extends AppCompatActivity implements IPrivateChatView, InstantMessageService.MsgObserver, View.OnClickListener {

    public static final String KEY_CONTACT_PROFILE_TALK_WITH = "contact_talk_with";

    private PrivateChatAdapter privateChatAdapter;
    private ListView list_msg_show;

    private EditText txt_content;

    private PrivateChatPresenter presenter;

    private InstantMessageService.Binder sender;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sender = (InstantMessageService.Binder) service;
            sender.setMsgObserver(PrivateChatActivity.this);
            sender.setNeedToNotifyNewMsg(false);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            sender = null;
        }
    };

    private String me, other;

    private boolean isSetup = false;

    @Override
    public void setupMsg(List<Message> msgList) {
        if (isSetup) {
            privateChatAdapter.clear();
        }

        privateChatAdapter.setupWithMsg(msgList);
        list_msg_show.setSelection(privateChatAdapter.getCount());
    }

    @Override
    public void showNewMsg(Message message) {
        privateChatAdapter.addNewMsg(message);
        list_msg_show.setSelection(privateChatAdapter.getCount());
    }

    @Override
    public void onNewMsg(final Message msg) {
        if (other.equals(msg.theManTalkWith(me))) {
            presenter.performAddMessage(this, msg);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_pm_msg_send) {

            Message msg = new Message();
            msg.setFrom(me);
            msg.setTo(other);
            msg.setContent(txt_content.getText().toString());
            msg.setDate(new Date());
            sender.send(msg);

            txt_content.setText("");

        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);

        Profile talkTo = getIntent().getParcelableExtra(KEY_CONTACT_PROFILE_TALK_WITH);

        if (talkTo == null) {
            finish();
        }

        Profile myProfile = new SessionHelper(this).getEntireProfile();

        this.me = myProfile.getUsername();
        this.other = talkTo.getUsername();

        privateChatAdapter = new PrivateChatAdapter(getLayoutInflater(), me, myProfile.getNickname(), talkTo.getNickname());

        list_msg_show = (ListView) findViewById(R.id.list_pm_msg_show);

        list_msg_show.setAdapter(privateChatAdapter);

        presenter = new PrivateChatPresenter(this);
        presenter.performSetupMessage(this, talkTo);

        txt_content = (EditText) findViewById(R.id.txt_pm_msg_input);

        findViewById(R.id.btn_pm_msg_send).setOnClickListener(this);

        setTitle(talkTo.getNickname());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent bindIntent = new Intent(PrivateChatActivity.this, InstantMessageService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        unbindService(serviceConnection);
        presenter.performReadMessage(this);
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
