package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages;

import com.tnecesoc.MyInfoDemo.Entity.Message;

import java.util.List;

/**
 * Created by Tnecesoc on 2016/12/28.
 */
public interface IPrivateChatView {

    void setupMsg(List<Message> msgList);

    void showNewMsg(Message message);

}
