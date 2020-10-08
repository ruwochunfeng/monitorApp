package com.zhoubing.bishe.diaglog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhoubing.bishe.R;

/**
 * Created by dell on 2018/1/25.
 */

public class DuojiDiaglog extends Dialog {


    private Context context;

    public TextView textView;


    public DuojiDiaglog(Context context) {
        super(context,R.style.dialogstyle);
        // 获取dialog的window对象
//        Window window = getWindow();
//        // 获取布局参数
//        WindowManager.LayoutParams params = window.getAttributes();
//        // 设置位置在最下面 左右居中
//        // 更新布局参数
//        window.setAttributes(params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_duoji);
//        Croller croller = (Croller) findViewById(R.id.croller);
//        croller.setIndicatorWidth(10);
//        croller.setBackCircleColor(Color.parseColor("#EDEDED"));
//        croller.setMainCircleColor(Color.WHITE);
//        croller.setMax(50);
//        croller.setStartOffset(45);
//        croller.setIsContinuous(false);
//        croller.setLabelColor(Color.BLACK);
//        croller.setProgressPrimaryColor(Color.parseColor("#0B3C49"));
//        croller.setIndicatorColor(Color.parseColor("#0B3C49"));
//        croller.setProgressSecondaryColor(Color.parseColor("#EEEEEE"));
    }



}
