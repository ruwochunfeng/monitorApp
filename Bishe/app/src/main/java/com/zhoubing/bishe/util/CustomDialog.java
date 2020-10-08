package com.zhoubing.bishe.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.zhoubing.bishe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/1/25.
 */

public class CustomDialog extends Dialog {


    private Context context;

    public TextView textView;


    public CustomDialog(Context context) {
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
        this.setContentView(R.layout.dialog_custom);
        Button but_quxiao= (Button) findViewById(R.id.quxiao);
        Button but_queding= (Button) findViewById(R.id.queding);
        but_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        but_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });




    }
    public CustomDialog setText(String shuju){

        textView.setText(shuju);
        return this;
    }



}
