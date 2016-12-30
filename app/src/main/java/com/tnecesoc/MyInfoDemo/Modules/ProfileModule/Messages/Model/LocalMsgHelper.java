package com.tnecesoc.MyInfoDemo.Modules.ProfileModule.Messages.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tnecesoc.MyInfoDemo.Entity.Message;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.LocalSQLiteModelImpl;
import com.tnecesoc.MyInfoDemo.GlobalModel.Local.SessionHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Tnecesoc on 2016/12/24.
 */
@SuppressWarnings("SqlNoDataSourceInspection")
public class LocalMsgHelper extends LocalSQLiteModelImpl {

    @interface Sorted {}

    private static final String DB_NAME = "neighbor_messages";
    private static final String TABLE_NAME = "messages";

    private String me;

    private SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());

    public LocalMsgHelper(Context context) {
        super(context, DB_NAME, null, 1);
        me = new SessionHelper(context).getSessionAttribute(SessionHelper.KEY_USERNAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                " id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " to_user TEXT NOT NULL , " +
                " from_user TEXT NOT NULL , " +
                " content TEXT, " +
                " is_read INTEGER DEFAULT 0, " +
                " send_time DATETIME NOT NULL ); "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS neighbors_messages");
        onCreate(db);
    }

    public String getSelfUsername() {
        return me;
    }

    public void markAllUnreadMsgOf(String talkWith) {
        dbOut.execSQL("UPDATE " + TABLE_NAME + " SET is_read = 1 WHERE to_user = ? OR from_user = ?", new String[]{talkWith, talkWith});
    }

    public int getUnreadMsgTotalCnt() {

        int ans = 0;

        Cursor cursor = dbIn.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE is_read <> 1", null);

        ans = cursor.getCount();

        cursor.close();

        return ans;

    }

    public Map<String, Integer> getUnreadMsgCntOfAll() {
        Map<String, Integer> ans = new Hashtable<>();

        Cursor cursor = dbIn.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE is_read <> 1", null);

        try {
            if (cursor.moveToFirst()) {
                do {

                    Message now = fromCurrentCursor(cursor);
                    String talkWith = now.theManTalkWith(me);
                    if (ans.get(talkWith) == null) {
                        ans.put(talkWith, 1);
                    } else {
                        ans.put(talkWith, ans.get(talkWith) + 1);
                    }

                } while (cursor.moveToNext());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cursor.close();

        return ans;
    }

    public LinkedList<Message> getLatestMsgOfAll() {

        LinkedList<Message> ans = new LinkedList<Message>();
        HashSet<String> nameSet = new HashSet<String>();

        Cursor cursor = dbIn.rawQuery("SELECT * FROM " + TABLE_NAME +
                " WHERE send_time IN (SELECT MAX(send_time) " +
                " FROM " + TABLE_NAME +
                " GROUP BY from_user, to_user " +
                ") ORDER BY send_time DESC", null);
        try {

            if (cursor.moveToFirst()) {
                do {
                    Message now = fromCurrentCursor(cursor);

                    String talkWith = now.theManTalkWith(me);

                    if (!nameSet.contains(talkWith)) {
                        ans.add(now);
                        nameSet.add(talkWith);
                    }

                } while (cursor.moveToNext());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cursor.close();

        return ans;
    }

    public List<Message> getMsgByContact(String username) {
        Cursor cursor = dbIn.query(TABLE_NAME, null, "from_user = ? OR to_user = ?", new String[]{username, username}, null, null, "send_time");

        List<Message> res = new ArrayList<>();

        res = toList(cursor, res);

        cursor.close();
        return res;

    }

    public Date getLastMsgDate() {
        Cursor cursor = dbIn.query(TABLE_NAME, null, null, null, null, null, "send_time");
        Date ans;
        if (cursor.moveToFirst()) {
            cursor.moveToLast();
            try {
                ans = sqlDateFormat.parse(cursor.getString(cursor.getColumnIndex("send_time")));
            } catch (ParseException e) {
                e.printStackTrace();
                ans = new Date(Long.MIN_VALUE);
            }
        } else {
            ans = new Date(Long.MIN_VALUE);
        }
        cursor.close();
        return ans;
    }

    public void addAllMsg(@Sorted List<Message> messages) {

        if (messages.isEmpty() || messages.get(0).getDate().before(getLastMsgDate())) {
            return;
        }

        for (Message msg : messages) {
            addMsg(msg);
        }
    }

    public synchronized void addMsg(Message message) {
        dbOut.insert(TABLE_NAME, null, toContentValues(message));
    }

    private List<Message> toList(Cursor cursor, List<Message> res) {
        if (res == null) {
            res = new ArrayList<>();
        }

        try {
            if (cursor.moveToFirst()) {
                do {

                    res.add(fromCurrentCursor(cursor));

                } while (cursor.moveToNext());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return res;
    }

    private Message fromCurrentCursor(Cursor cursor) throws ParseException {

        Message res = new Message();

        res.setFrom(cursor.getString(cursor.getColumnIndex("from_user")));
        res.setTo(cursor.getString(cursor.getColumnIndex("to_user")));
        res.setContent(cursor.getString(cursor.getColumnIndex("content")));
        res.setDate(sqlDateFormat.parse(cursor.getString(cursor.getColumnIndex("send_time"))));

        return res;
    }

    private ContentValues toContentValues(Message message) {
        ContentValues ans = new ContentValues();
        ans.put("to_user", message.getTo());
        ans.put("from_user", message.getFrom());
        ans.put("content", message.getContent());
        ans.put("send_time", sqlDateFormat.format(message.getDate()));

        if (message.getFrom().equals(me)) {
            ans.put("is_read", 1);
        }

        return ans;
    }
}
