package com.tnecesoc.MyInfoDemo.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import com.google.gson.Gson;
import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.Entity.Profile;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.LocalContactsHelper;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.Host;
import com.tnecesoc.MyInfoDemo.GlobalModel.Remote.ViewProfileHelper;
import com.tnecesoc.MyInfoDemo.Modules.Homepage.View.MainActivity;
import com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Model.LocalMsgHelper;
import com.tnecesoc.MyInfoDemo.R;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import static com.tnecesoc.MyInfoDemo.BuildConfig.DEBUG;

public class InstantMessageService extends Service {

    private static final int INIT_NOTIFY_ID = 0;
    private static final int MSG_NOTIFY_ID = 1;

    private static final String PRIVATE_CHAT_TOPIC = "messages";

    // will not be saved in both server and local.
    private static final String DEBUG_TOPIC = "debug";

    public interface MsgObserver {

        void onNewMsg(Message msg);

    }

    private MqttClient client;
    private MemoryPersistence memPer;

    private Notification msgArrivedNotify;
    private NotificationManager notificationManager;

    private String me;
    private Gson gson = new Gson();

    private MsgObserver observer;

    private boolean needToNotifyNewMsg = true;

    private void saveNewMsgToModel(Message msg) {

        LocalMsgHelper localMsgHelper = new LocalMsgHelper(this);
        LocalContactsHelper localContactsHelper = new LocalContactsHelper(this);

        String other = msg.theManTalkWith(me);

        if (localContactsHelper.getProfileByUsername(other) == null) {
            Profile otherProfile = new ViewProfileHelper(other).viewProfile(null);

            if (otherProfile == null) {
                otherProfile = new Profile();
                otherProfile.setUsername(other);
                otherProfile.setNickname("who is this?");
            }

            localContactsHelper.putProfile(otherProfile, Profile.Category.UNKNOWN);
        }

        localMsgHelper.addMsg(msg);
        localMsgHelper.close();

    }

    @Override
    public void onCreate() {
        super.onCreate();

        memPer = new MemoryPersistence();

        me = new SessionHelper(this).getSessionAttribute(SessionHelper.KEY_USERNAME);

        try {

            client = new MqttClient(Host.BROKER_HOST, me, memPer);

            MqttConnectOptions opt = new MqttConnectOptions();
            opt.setAutomaticReconnect(false);
            opt.setConnectionTimeout(2);
            client.connect(opt);


            client.subscribe(PRIVATE_CHAT_TOPIC, 1, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {

                    final Message msg = gson.fromJson(new String(message.getPayload()), Message.class);

                    System.out.println("message arrived: " + msg);

                    saveNewMsgToModel(msg);

                    if (observer != null) {

                        if (msg.getFrom().equals(me) || msg.getTo().equals(me)) {
                            observer.onNewMsg(msg);
                        }

                    }

                    if (needToNotifyNewMsg) {
                        notificationManager.notify(MSG_NOTIFY_ID, msgArrivedNotify);
                    }

                }
            });

            client.subscribe(DEBUG_TOPIC, 1, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {

                    Message msg = gson.fromJson(new String(message.getPayload()), Message.class);

                    if (DEBUG) {
                        System.out.println("debug: message arrived: " + msg);
                    }

                    if (observer != null) {

                        if (msg.getFrom().equals(me) || msg.getTo().equals(me)) {
                            observer.onNewMsg(msg);
                        }

                    }

                    if (needToNotifyNewMsg) {
                        notificationManager.notify(MSG_NOTIFY_ID, msgArrivedNotify);
                    }

                }
            });

        } catch (MqttException e) {
            e.printStackTrace();

            Toast.makeText(this, R.string.hint_network_unavailable, Toast.LENGTH_SHORT).show();

        }

        initForForeground();

    }

    @Override
    public void onDestroy() {
        try {
            notificationManager.cancelAll();
            notificationManager = null;
            observer = null;
            if (client != null) {
                client.unsubscribe(PRIVATE_CHAT_TOPIC);
                client.disconnect();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }


    public class Binder extends android.os.Binder {

        public void send(Message msg) {
            byte[] toSend = gson.toJson(msg).getBytes();

            try {
                client.publish(PRIVATE_CHAT_TOPIC, toSend, 1, false);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        public void setNeedToNotifyNewMsg(boolean expression) {
            InstantMessageService.this.needToNotifyNewMsg = expression;
        }

        public void setMsgObserver(MsgObserver observer) {
            InstantMessageService.this.observer = observer;
            System.out.println("binded to service");
        }

    }

    private void initForForeground() {

        PendingIntent pendingIntent = PendingIntent.getService(this, 0, new Intent(this, MainActivity.class), 0);

        startForeground(INIT_NOTIFY_ID, new Notification());

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        msgArrivedNotify = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_mode_comment_white_48dp)
                .setContentTitle(getString(R.string.notify_new_msg))
                .setContentTitle(getString(R.string.gui_from_neighbors))
                .setAutoCancel(true)
                .setTicker(getString(R.string.notify_new_msg))
                .setContentIntent(pendingIntent)
                .build();
    }

    private void cancelMessageNotify() {
        notificationManager.cancel(MSG_NOTIFY_ID);
    }

}

