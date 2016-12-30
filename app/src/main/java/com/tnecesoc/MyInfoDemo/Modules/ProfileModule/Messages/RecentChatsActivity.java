package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Presenter.ConversationPresenter;
import com.tnecesoc.MyInfoDemo.R;
import com.tnecesoc.MyInfoDemo.Utils.InstantMessageService;

import java.util.LinkedList;
import java.util.Map;

import static com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.PrivateChatActivity.KEY_CONTACT_PROFILE_TALK_WITH;

public class RecentChatsActivity extends AppCompatActivity
        implements IRecentChatsView, InstantMessageService.MsgObserver, AdapterView.OnItemClickListener {

    private ListView list_message;
    private RecentChatsAdapter recentChatsAdapter;

    private ConversationPresenter presenter;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            InstantMessageService.Binder binder = (InstantMessageService.Binder) service;
            binder.setMsgObserver(RecentChatsActivity.this);
            binder.setNeedToNotifyNewMsg(false);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private boolean isSetup = false;

    @Override
    public void onNewMsg(Message msg) {

        presenter.performAddNewMsg(this, msg);

    }

    @Override
    public void setupWithData(LinkedList<Message> latestMsg, Map<String, Profile> contactsInvolved, Map<String, Integer> unreadMsgCount) {

        if (isSetup) {
            recentChatsAdapter.clear();
        }

        recentChatsAdapter.setUp(latestMsg, contactsInvolved, unreadMsgCount);
        isSetup = true;
    }

    @Override
    public void showNewMsg(Message newMsg, Profile talkTo) {
        recentChatsAdapter.addNewMsg(newMsg, talkTo);
    }

    @Override
    public void showNetworkFailure() {
        Toast.makeText(this, getString(R.string.hint_network_unavailable), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_main);

        list_message = (ListView) findViewById(R.id.lst_messages_main);

        String curUsername = new SessionHelper(this).getSessionAttribute(SessionHelper.KEY_USERNAME);

        recentChatsAdapter = new RecentChatsAdapter(curUsername, getLayoutInflater());
        list_message.setAdapter(recentChatsAdapter);

        list_message.setOnItemClickListener(this);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new ConversationPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.performSyncMsg(this);
        Intent bindIntent = new Intent(RecentChatsActivity.this, InstantMessageService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        unbindService(serviceConnection);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(RecentChatsActivity.this, PrivateChatActivity.class);

        Profile contactClicked = recentChatsAdapter.getUsernameAt(position);

        intent.putExtra(KEY_CONTACT_PROFILE_TALK_WITH, contactClicked);
        presenter.performMarkAllMsgRead(RecentChatsActivity.this, contactClicked.getUsername());
        recentChatsAdapter.markAllMsgReadFor(position);
        startActivity(intent);

    }

}
