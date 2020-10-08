package com.zhoubing.bishe;


import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhoubing.bishe.fragment.DiliFragment;
import com.zhoubing.bishe.fragment.JibenFragment;
import com.zhoubing.bishe.fragment.LupingFragment;
import com.zhoubing.bishe.fragment.ShipinyuanFragment;
import com.zhoubing.bishe.fragment.TitleFragment;
import com.zhoubing.bishe.fragment.lingmingduFragment;

public class SettingActivitu extends FragmentActivity implements View.OnClickListener {


    private DiliFragment dili ;
    private JibenFragment jiben;
    private lingmingduFragment lingm;
    private ShipinyuanFragment shipin;
    private TitleFragment title;
    private LupingFragment lupin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_activitu);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button_retu = (Button) findViewById(R.id.retu);
        dili = new DiliFragment();
        jiben = new JibenFragment();
        lingm = new lingmingduFragment();
        shipin = new ShipinyuanFragment();
        title = new TitleFragment();
        lupin = new LupingFragment();


        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button_retu.setOnClickListener(this);
        replaceFragment(jiben);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                replaceFragment(dili);
                break;
            case R.id.button2:
                replaceFragment(shipin);
                break;
            case R.id.button3:
                replaceFragment(lingm);
                break;
            case R.id.button4:
                replaceFragment(lupin);
                break;
            case R.id.button5:
                replaceFragment(dili);
                break;
            case R.id.retu:
                ReturnLastActivity();
            default:
                break;
        }

    }

    private void ReturnLastActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString("jiben","ceshi");
//        jiben.setArguments(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.comment,fragment);
        transaction.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
