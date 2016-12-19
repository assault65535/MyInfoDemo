package com.tnecesoc.MyInfoDemo.GlobalModel.Local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tnecesoc.MyInfoDemo.Bean.ProfileBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.tnecesoc.MyInfoDemo.BuildConfig.DEBUG;

/**
 * Created by Tnecesoc on 2016/12/15.
 */
@SuppressWarnings("SqlNoDataSourceInspection")
public class LocalContactsHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "neighbor";
    private static final String TABLE_NAME = "contacts";

    private Context mContext;

    private SQLiteDatabase dbIn = getReadableDatabase(), dbOut = getWritableDatabase();

    private LocalContactsHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    public LocalContactsHelper(Context context) {
        this(context.getApplicationContext(), DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( id INTEGER PRIMARY KEY AUTOINCREMENT,");

        for (Field f : ProfileBean.class.getDeclaredFields()) {
            if (f.getType().equals(String.class) || f.getType().isEnum()) {
                sql.append(f.getName()).append(" TEXT,");
            }
        }

        sql.append("category TEXT NOT NULL);");

        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS neighbors_contacts");
        onCreate(db);
    }

    public ProfileBean getProfileByUsername(String username) {

        Cursor cursor = dbIn.query(TABLE_NAME, null, "username = ?", new String[]{username}, null, null, "nickname");

        if (cursor.moveToFirst()) {
            return fromCurrentCursor(cursor);
        } else {
            return new ProfileBean();
        }
    }

    public List<ProfileBean> getContactsListByCategory(ProfileBean.Category category) {

        List<ProfileBean> res = new ArrayList<>();

        Cursor cursor;

        if (category == ProfileBean.Category.ARBITRARY) {
            cursor = dbIn.query(TABLE_NAME, null, null, null, null, null, "category, nickname");
        } else {
            cursor = dbIn.query(TABLE_NAME, null, "category = ?", new String[]{category.toString()}, null, null, "nickname");
        }

        if (cursor.moveToFirst()) {
            do {

                ProfileBean item = fromCurrentCursor(cursor);

                res.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return res;
    }

    public ProfileBean.Category getCategoryByUsername(String username) {
        Cursor cursor = dbIn.query(TABLE_NAME, new String[]{"category"}, "username = ?", new String[]{username}, null, null, "nickname");

        ProfileBean.Category res;

        if (cursor.moveToFirst()) {
            res = ProfileBean.Category.valueOf(cursor.getString(cursor.getColumnIndex("category")));
        } else {
            res = ProfileBean.Category.ARBITRARY;
        }

        cursor.close();

        return res;
    }

    public void putAllProfileInCategory(List<ProfileBean> profiles, ProfileBean.Category category) {

        for (ProfileBean profile: profiles) {
            putProfile(profile, category);
        }

    }

    public synchronized void putProfile(ProfileBean profileBean, ProfileBean.Category category) {

        Cursor cursor = dbIn.query(TABLE_NAME, new String[]{"id"}, "username = ?", new String[]{profileBean.getUsername()}, null, null, "nickname");

        System.out.println("hehe");

        if (cursor.moveToFirst()) {
            updateRow(toContentValues(profileBean, category));
        } else {
            addRow(toContentValues(profileBean, category));
        }

        cursor.close();
    }

    private ProfileBean fromCurrentCursor(Cursor cursor) {

        ProfileBean res = new ProfileBean();
        try {
            for (Field f : ProfileBean.class.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.getType().equals(String.class) || f.getType().isEnum()) {
                    f.set(res, cursor.getString(cursor.getColumnIndex(f.getName())));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return res;
    }

    private ContentValues toContentValues(ProfileBean profileBean, ProfileBean.Category category) {
        ContentValues row = new ContentValues();

        try {
            for (Field f : ProfileBean.class.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.getType().equals(String.class)) {
                    row.put(f.getName(), (String)f.get(profileBean));
                }
            }
            row.put("category", category.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return row;
    }

    private void addRow(ContentValues row) {
        dbOut.insert(TABLE_NAME, null, row);
    }

    private void updateRow(ContentValues row) {
        dbOut.update(TABLE_NAME, row, "username = ?", new String[]{row.getAsString("username")});
    }
}
