package com.zhoubing.bishe;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zhoubing.bishe.sqilite.MySQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    Button button ;
    EditText editText ,editText1;
    ProgressBar progressBar;
    Handler handler ;
    private MySQLiteHelper mySQLiteHelper;
    SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        handler = new Handler();
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.user);
        editText1 = (EditText) findViewById(R.id.mima);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        mySQLiteHelper = new MySQLiteHelper(this,"LoginHistort.db",null,1);
        progressBar.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(editText1.getText().toString().equals("zhoubing")&&editText.getText().toString().equals("zhoubing")) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            LoginActivity.this.startActivity(new Intent(LoginActivity.this, MoshiFour.class));
                            LoginActivity.this.finish();
                        }
                    },3000);
                    SQLiteDatabase db = mySQLiteHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    Date date = new Date(System.currentTimeMillis());
                    String str = formatter.format(date);
                    values.put("name","zhoubing");
                    values.put("time",str);
                    db.insert("history",null,values);

                }else if(editText1.getText().toString().equals("zhoubing")&&(!editText.getText().toString().equals("zhoubing"))){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,"账户不存在",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    },2000);

                }else if((!editText1.getText().toString().equals("zhoubing"))&&editText.getText().toString().equals("zhoubing")){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,"密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    },2000);

                }else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,"这就很尴尬了",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    },2000);

                }
            }
        });
    }
}
