package com.tnecesoc.MyInfoDemo.GlobalModel.Local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tnecesoc on 2016/12/24.
 */
public abstract class LocalSQLiteModelImpl extends SQLiteOpenHelper {

    protected SQLiteDatabase dbIn, dbOut;

    public LocalSQLiteModelImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        dbIn = getReadableDatabase();
        dbOut = getWritableDatabase();
    }

    @Override
    public synchronized void close() {
        dbIn.close();
        dbOut.close();
        super.close();
    }
}
