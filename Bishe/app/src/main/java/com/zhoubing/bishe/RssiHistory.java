package com.zhoubing.bishe;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.zhoubing.bishe.adapter.HistoryAdapter;
import com.zhoubing.bishe.adapter.RssiHistoryAdapter;
import com.zhoubing.bishe.sqilite.MySQLiteHelper;
import com.zhoubing.bishe.sqilite.RssiSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class RssiHistory extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> list_name;
    List<String> list_date;
    private RssiSQLiteHelper mySQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssi_history);
        init();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_rssi);
        RssiHistoryAdapter historyAdapter = new RssiHistoryAdapter(list_name,list_date,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(historyAdapter);
    }

    public void init(){
        list_date = new ArrayList<>();
        list_name = new ArrayList<>();
        mySQLiteHelper = new RssiSQLiteHelper(this,"RssiValue.db",null,1);
        SQLiteDatabase db = mySQLiteHelper.getReadableDatabase();
        Cursor cursor = db.query("rssi",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex("location"));
                String time = cursor.getString(cursor.getColumnIndex("rssishuzu"));
                list_name.add(name);
                list_date.add(time);

            }while(cursor.moveToNext());


        }
        cursor.close();


    }
}
