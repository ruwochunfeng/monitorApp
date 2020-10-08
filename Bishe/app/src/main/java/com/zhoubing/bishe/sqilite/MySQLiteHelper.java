package com.zhoubing.bishe.sqilite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dell on 2018/3/23.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    final String CREATE_TABLE_SQL="create table history(_id integer primary key autoincrement,name text,time text)";

    public MySQLiteHelper(Context context, String name,
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
