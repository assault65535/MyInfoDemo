package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;

/**
 * Created by Tnecesoc on 2016/12/28.
 */
public class PrivateChatAdapter extends BaseAdapter {

    private @interface Nickname {}

    public static final int TYPE_ME = 1;
    public static final int TYPE_OTHER = 0;

    private List<Message> msgList = new ArrayList<>();
    private LayoutInflater inflater;
    private String me, other;
    private String myUsername;

    public PrivateChatAdapter(LayoutInflater inflater, String myUsername, @Nickname String me, @Nickname String other) {
        this.inflater = inflater;
        this.me = me;
        this.other = other;
        this.myUsername = myUsername;
    }

    public void clear() {
        if (msgList != null) {
            msgList.clear();
        }
    }

    public void setupWithMsg(List<Message> msgList) {
        this.msgList = msgList;
        notifyDataSetChanged();
    }

    public void addNewMsg(Message msg) {
        msgList.add(msg);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return msgList.size();
    }

    @Override
    public Object getItem(int position) {
        return msgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return myUsername.equals(msgList.get(position).getFrom()) ? TYPE_ME : TYPE_OTHER;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message msg = (Message) getItem(position);

        ViewHolder.Builder viewHolder;
        View view;

        if (convertView == null) {

            if (getItemViewType(position) == TYPE_ME) {
                view = inflater.inflate(R.layout.list_item_msg_special, null, false);
            } else {
                view = inflater.inflate(R.layout.list_item_msg, null, false);
            }

            viewHolder = new ViewHolder.Builder()
                    .avatar(view.findViewById(R.id.img_list_item_msg_avatar))
                    .nickname(view.findViewById(R.id.lbl_list_item_msg_nickname))
                    .content(view.findViewById(R.id.lbl_list_item_msg_content))
                    .time(view.findViewById(R.id.lbl_list_item_msg_send_time));

            view.setTag(viewHolder);

        } else {

            view = convertView;
            viewHolder = (ViewHolder.Builder) convertView.getTag();
        }

        viewHolder
                .showTimeWhen(position % 3 == 0)
                .message(msg)
                .author(myUsername.equals(msg.getFrom()) ? me : other)
                .show();

        return view;
    }


    private static class ViewHolder {

        private View img_avatar, lbl_nickname, lbl_content, lbl_time;

        private ViewHolder() {}

        static class Builder {

            private ViewHolder v;
            private String reader;
            private Message msg;

            boolean displayTime = false;

            public Builder() {
                v = new ViewHolder();
            }

            public ViewHolder show() {

                if (v.img_avatar == null || v.lbl_nickname == null || v.lbl_time == null || v.lbl_content == null) {
                    throw new IllegalStateException("Where to show all the details?");
                }

                v.lbl_time.setVisibility(displayTime ? View.VISIBLE : View.GONE);

                v.showMsg(msg, reader);

                return v;

            }

            public Builder avatar(View view) {
                v.img_avatar = view;
                return this;
            }

            public Builder nickname(View view) {
                v.lbl_nickname = view;
                return this;
            }

            public Builder content(View view) {
                v.lbl_content = view;
                return this;
            }

            public Builder time(View view) {
                v.lbl_time = view;
                return this;
            }

            public Builder message(Message msg) {
                this.msg = msg;
                return this;
            }

            public Builder author(@Nickname String who) {
                this.reader = who;
                return this;
            }

            public Builder hideTime() {
                displayTime = false;
                return this;
            }

            public Builder showTimeWhen() {
                displayTime = true;
                return this;
            }

            public Builder showTimeWhen(boolean expression) {
                displayTime = expression;
                return this;
            }

        }


        private void showMsg(Message msg, String talkWith) {

            ((SimpleDraweeView) img_avatar).setImageURI(Host.findAvatarUrlByUsername(msg.getFrom()));
            ((TextView) lbl_nickname).setText(talkWith);
            ((TextView) lbl_content).setText(msg.getContent());
            ((TextView) lbl_time).setText(new SimpleDateFormat("H:mm", Locale.getDefault()).format(msg.getDate()));

        }

    }


}
