package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.R;
import com.tnecesoc.MyInfoDemo.Utils.FlexDateFormat;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Tnecesoc on 2016/12/24.
 */
public class RecentChatsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private LinkedList<Message> latestMsg = new LinkedList<>();
    private Map<String, Profile> contactsInvolved = new Hashtable<>();
    private Map<String, Integer> unreadMsgCount = new Hashtable<>();

    private String me;

    public RecentChatsAdapter(String selfUsername, LayoutInflater mInflater) {
        this.mInflater = mInflater;
        this.me = selfUsername;
    }

    public void setUp(LinkedList<Message> latestMsg, Map<String, Profile> contactsInvolved, Map<String, Integer> unreadMsgCount) {
        this.latestMsg = latestMsg;
        this.contactsInvolved = contactsInvolved;
        this.unreadMsgCount = unreadMsgCount;
        notifyDataSetChanged();
    }

    public void notifyNewMsgOf(String talkWith) {
        if (unreadMsgCount.get(talkWith) == null) {
            unreadMsgCount.put(talkWith, 1);
        } else {
            unreadMsgCount.put(talkWith, unreadMsgCount.get(talkWith) + 1);
        }
        notifyDataSetChanged();
    }

    public void markAllMsgReadFor(int position) {
        unreadMsgCount.put(latestMsg.get(position).theManTalkWith(me), 0);
    }

    public void addNewMsg(Message message) {
        String other = message.theManTalkWith(me);


        Profile tmp = new Profile();
        tmp.setUsername(other);

        addNewMsg(message, tmp);
    }


    public void addNewMsg(Message message, Profile talkWith) {

        if (!contactsInvolved.containsValue(talkWith)) {
            contactsInvolved.put(message.theManTalkWith(me), talkWith);
        }

        removeOldMsgOf(talkWith.getUsername());
        latestMsg.addFirst(message);
        notifyNewMsgOf(talkWith.getUsername());
    }

    public int size() {
        return latestMsg.size();
    }

    public Message getItem(int position) {
        return latestMsg.get(position);
    }

    public void clear() {

        if (latestMsg != null) {
            latestMsg.clear();
        }

        if (contactsInvolved != null) {
            contactsInvolved.clear();
        }

        if (unreadMsgCount != null) {
            unreadMsgCount.clear();
        }

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return latestMsg.size();
    }

    private void removeOldMsgOf(String talkWith) {

        if (talkWith.equals(me)) {
            throw new IllegalArgumentException("removing all old messages");
        }

        for (Message msg : latestMsg) {
            if (msg.getFrom().equals(talkWith) || msg.getTo().equals(talkWith)) {
                latestMsg.remove(msg);
                break;
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Profile getUsernameAt(int position) {
        String other = latestMsg.get(position).theManTalkWith(me);
        return contactsInvolved.get(other);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_conversation, null);
        }
        Message now = getItem(position);

        String username = now.theManTalkWith(me);
        Profile talkTo = contactsInvolved.get(username);

        ((TextView) convertView.findViewById(R.id.lbl_list_item_msg_nickname)).setText(talkTo == null ? username : talkTo.getNickname());
        ((SimpleDraweeView) convertView.findViewById(R.id.img_list_item_msg_avatar)).setImageURI(Uri.parse(Host.findAvatarUrlByUsername(username)));

        if (now.getContent().length() >= 20) {
            now.setContent(now.getContent().substring(0, 20) + "...");
        }

        ((TextView) convertView.findViewById(R.id.lbl_list_item_latest_msg_content)).setText(now.getContent());
        ((TextView) convertView.findViewById(R.id.lbl_list_item_latest_msg_time)).setText(new FlexDateFormat().format(now.getDate()));

        if (getUnreadMsgCntOf(username) <= 0) {
            convertView.findViewById(R.id.lbl_list_item_new_msg_count).setVisibility(View.GONE);
        } else if (unreadMsgCount.get(username) >= 100) {
            convertView.findViewById(R.id.lbl_list_item_new_msg_count).setVisibility(View.VISIBLE);
            ((TextView) convertView.findViewById(R.id.lbl_list_item_new_msg_count)).setText("99+");
        } else {
            convertView.findViewById(R.id.lbl_list_item_new_msg_count).setVisibility(View.VISIBLE);
            ((TextView) convertView.findViewById(R.id.lbl_list_item_new_msg_count)).setText(Integer.toString(getUnreadMsgCntOf(username)));
        }

        return convertView;
    }

    private int getUnreadMsgCntOf(String username) {
        return unreadMsgCount.get(username) == null ? 0 : unreadMsgCount.get(username);
    }

}
