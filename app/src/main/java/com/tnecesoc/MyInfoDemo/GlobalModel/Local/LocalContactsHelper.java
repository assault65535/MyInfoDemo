package com.tnecesoc.MyInfoDemo.GlobalModel.Local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tnecesoc.MyInfoDemo.Entity.Profile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tnecesoc on 2016/12/15.
 */
@SuppressWarnings("SqlNoDataSourceInspection")
public class LocalContactsHelper extends LocalSQLiteModelImpl {

    private static final String DB_NAME = "neighbor";
    private static final String TABLE_NAME = "contacts";

    public LocalContactsHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( id INTEGER PRIMARY KEY AUTOINCREMENT,");

        for (Field f : Profile.class.getDeclaredFields()) {
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

    public Profile getProfileByUsername(String username) {

        Cursor cursor = dbIn.query(TABLE_NAME, null, "username = ?", new String[]{username}, null, null, "nickname");

        Profile ans;

        if (cursor.moveToFirst()) {
            ans = fromCurrentCursor(cursor);
        } else {
            ans = null;
        }

        cursor.close();

        return ans;

    }

    public List<Profile> getContactsListByCategory(Profile.Category category) {

        List<Profile> res = new ArrayList<>();

        Cursor cursor;

        if (category == Profile.Category.ARBITRARY) {
            cursor = dbIn.query(TABLE_NAME, null, "category <> ?", new String[]{Profile.Category.UNKNOWN.toString()}, null, null, "category, nickname");
        } else {
            cursor = dbIn.query(TABLE_NAME, null, "category = ?", new String[]{category.toString()}, null, null, "nickname");
        }

        if (cursor.moveToFirst()) {
            do {

                Profile item = fromCurrentCursor(cursor);

                res.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return res;
    }

    public Profile.Category getCategoryByUsername(String username) {
        Cursor cursor = dbIn.query(TABLE_NAME, new String[]{"category"}, "username = ?", new String[]{username}, null, null, "nickname");

        Profile.Category res;

        if (cursor.moveToFirst()) {
            res = Profile.Category.valueOf(cursor.getString(cursor.getColumnIndex("category")));
        } else {
            res = Profile.Category.ARBITRARY;
        }

        cursor.close();

        return res;
    }

    public void putAllProfileInCategory(List<Profile> profiles, Profile.Category category) {

        for (Profile profile: profiles) {
            putProfile(profile, category);
        }

    }

    public synchronized void putProfile(Profile profile, Profile.Category category) {

        Cursor cursor = dbIn.query(TABLE_NAME, new String[]{"id"}, "username = ?", new String[]{profile.getUsername()}, null, null, "nickname");

        if (cursor.moveToFirst()) {
            updateRow(toContentValues(profile, category));
        } else {
            addRow(toContentValues(profile, category));
        }

        cursor.close();
    }

    private Profile fromCurrentCursor(Cursor cursor) {

        Profile res = new Profile();
        try {
            for (Field f : Profile.class.getDeclaredFields()) {
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

    private ContentValues toContentValues(Profile profile, Profile.Category category) {
        ContentValues row = new ContentValues();

        try {
            for (Field f : Profile.class.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.getType().equals(String.class)) {
                    row.put(f.getName(), (String)f.get(profile));
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
