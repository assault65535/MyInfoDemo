package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages;

import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.Entity.Profile;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Tnecesoc on 2016/12/24.
 */
public interface IRecentChatsView {

    void setupWithData(LinkedList<Message> latestMsg, Map<String, Profile> contactsInvolved, Map<String, Integer> unreadMsgCount);

    void showNewMsg(Message newMsg, Profile talkTo);

    void showNetworkFailure();
}
