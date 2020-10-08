package com.zhoubing.bishe;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.zhoubing.bishe.adapter.HistoryAdapter;
import com.zhoubing.bishe.sqilite.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class LoginHistory extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> list_name;
    List<String> list_date;
    private MySQLiteHelper mySQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_history);
        init();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_history);
        HistoryAdapter historyAdapter = new HistoryAdapter(list_name,list_date);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(historyAdapter);
    }

    public void init(){
        list_date = new ArrayList<>();
        list_name = new ArrayList<>();
        mySQLiteHelper = new MySQLiteHelper(this,"LoginHistort.db",null,1);
        SQLiteDatabase db = mySQLiteHelper.getReadableDatabase();
        Log.e("数据库路径",db.getPath());
        Cursor cursor = db.query("history",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                list_name.add(name);
                list_date.add(time);

            }while(cursor.moveToNext());


        }
        cursor.close();


    }
}
