package com.zhoubing.bishe.sqilite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dell on 2018/3/23.
 */

public class RssiSQLiteHelper extends SQLiteOpenHelper {

    final String CREATE_TABLE_SQL="create table rssi(_id integer primary key autoincrement,location text,rssishuzu text)";

    public RssiSQLiteHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);


    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
