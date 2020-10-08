package com.zhoubing.bishe.diaglog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhoubing.bishe.R;

/**
 * Created by dell on 2018/1/25.
 */

public class ShipinDialog1 extends Dialog {


    private Context context;

    public TextView textView;


    public ShipinDialog1(Context context) {
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
        this.setContentView(R.layout.shipin_diaglog);
        Button button_dis = (Button) findViewById(R.id.quxiao);
        Button button_ok = (Button) findViewById(R.id.queding);
        button_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });







    }
    public ShipinDialog1 setText(String shuju){

        return this;
    }



}
