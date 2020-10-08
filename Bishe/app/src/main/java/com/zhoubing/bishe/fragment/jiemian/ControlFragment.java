package com.zhoubing.bishe.fragment.jiemian;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;
import com.zhoubing.bishe.MoshiFour;
import com.zhoubing.bishe.R;
import com.zhoubing.bishe.view.Yaokongqi;
import com.zhoubing.bishe.view.Yaokongqi1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by dell on 2018/2/26.
 */

public class ControlFragment extends Fragment {

    private Spinner spinner;
    Yaokongqi1 yaokongqi;

    MoshiFour moshiFour;
    int sudu1=10;
    private static String hexStr =  "0123456789ABCDEF";
    String sudu2 ="10";
    String Direc1,Direc2="00";
    double resultlng =0.0;
    double resultlat =0.0;
    double weidu =3044.88245;
    double jingdu =10355.55787;
    int duoji1 = 0;
    int duoji2 = 0;
    int duoji3 = 0;
    int duoji4 = 0;



    public  String url_1="http://192.168.1.100:8080/?action=stream";
    public  String control_1="192.168.1.100";
    public  String url_2="http://192.168.1.133:8080/?action=stream";
    public TextView tv1;
    private VerticalSeekBar seek_sudu,seek_zhuanjiao;
    private VerticalSeekBar seek_duoji1;
    private VerticalSeekBar seek_duoji2;
    private VerticalSeekBar seek_duoji3;
    private VerticalSeekBar seek_duoji4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.control,container,false);
        seek_sudu = (VerticalSeekBar) view.findViewById(R.id.mySeekBar);
        seek_zhuanjiao = (VerticalSeekBar) view.findViewById(R.id.mySeekBar1);
        seek_duoji1 = (VerticalSeekBar) view.findViewById(R.id.mySeekBar2);
        seek_duoji2 = (VerticalSeekBar) view.findViewById(R.id.mySeekBar3);
        seek_duoji3 = (VerticalSeekBar) view.findViewById(R.id.mySeekBar4);
        seek_duoji4 = (VerticalSeekBar) view.findViewById(R.id.mySeekBar5);
        tv1 = (TextView) view.findViewById(R.id.yanshi);
        spinner = (Spinner) view.findViewById(R.id.spinner_tongdao);
        String[] mItems = getResources().getStringArray(R.array.tongdao);

        resultlng = (int)(weidu/100) + (weidu/100.0 - (int)(weidu/100)) *100.0 / 60.0;
        resultlat = (int)(jingdu/100) + (jingdu/100.0 - (int)(jingdu/100)) *100.0 / 60.0;
        Log.e("测试结果看看"," "+resultlng+" "+resultlat);



        seek_sudu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               sudu1 =progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String zhuanjiao = Integer.toHexString(sudu1);
                        String jiaoyan  = Integer.toHexString((duoji4+4));
                        if(zhuanjiao.length()==1){

                            zhuanjiao = "0"+zhuanjiao;
                        }
                        if(jiaoyan.length()==1){

                            jiaoyan = "0"+jiaoyan;

                        }
                        try {
                            sendMsg2(HexStringToBinary("a55aff04"+zhuanjiao+jiaoyan));
                            Log.e("舵机数据","a55aff04"+zhuanjiao+jiaoyan);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        seek_zhuanjiao.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sudu2=String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek_duoji1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                duoji1 = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String zhuanjiao = Integer.toHexString(duoji1);
                        String jiaoyan  = Integer.toHexString((duoji1+1));
                        if(zhuanjiao.length()==1){

                            zhuanjiao = "0"+zhuanjiao;
                        }
                        if(jiaoyan.length()==1){

                            jiaoyan = "0"+jiaoyan;

                        }
                        try {
                            sendMsg2(HexStringToBinary("a55aff01"+zhuanjiao+jiaoyan));
                            Log.e("舵机数据","a55aff01"+zhuanjiao+jiaoyan);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        seek_duoji2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                duoji2 = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String zhuanjiao = Integer.toHexString(duoji2);
                        String jiaoyan  = Integer.toHexString((duoji2+2));
                        if(zhuanjiao.length()==1){

                            zhuanjiao = "0"+zhuanjiao;
                        }
                        if(jiaoyan.length()==1){

                            jiaoyan = "0"+jiaoyan;

                        }
                        try {
                            sendMsg2(HexStringToBinary("a55aff02"+zhuanjiao+jiaoyan));
                            Log.e("舵机数据","a55aff02"+zhuanjiao+jiaoyan);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        seek_duoji3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
               duoji3 = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String zhuanjiao = Integer.toHexString(duoji3);
                        String jiaoyan  = Integer.toHexString((duoji3+3));
                        if(zhuanjiao.length()==1){

                            zhuanjiao = "0"+zhuanjiao;
                        }
                        if(jiaoyan.length()==1){

                            jiaoyan = "0"+jiaoyan;

                        }
                        try {
                            sendMsg2(HexStringToBinary("a55aff03"+zhuanjiao+jiaoyan));
                            Log.e("舵机数据","a55aff03"+zhuanjiao+jiaoyan);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


        seek_duoji4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,final int progress, boolean fromUser) {
               duoji4 = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String zhuanjiao = Integer.toHexString(duoji4);
                        String jiaoyan  = Integer.toHexString((duoji4+4));
                        if(zhuanjiao.length()==1){

                            zhuanjiao = "0"+zhuanjiao;
                        }
                        if(jiaoyan.length()==1){

                            jiaoyan = "0"+jiaoyan;

                        }
                        try {
                            sendMsg2(HexStringToBinary("a55aff04"+zhuanjiao+jiaoyan));
                            Log.e("舵机数据","a55aff04"+zhuanjiao+jiaoyan);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems);
        //绑定 Adapter到控件
        spinner.setAdapter(_Adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        url_1 ="http://192.168.1.100:8080/?action=stream";
                        control_1="192.168.1.100";

                        break;
                    case 1:
                        url_1 ="http://192.168.1.134:8080/?action=stream";
                        control_1="192.168.1.134";
                        try {
                            moshiFour.chanel = SocketChannel.open(new InetSocketAddress(control_1,2001));
                            moshiFour.chanel.configureBlocking(false);
                            moshiFour.selector = Selector.open();
                            moshiFour.chanel.register( moshiFour.selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE|SelectionKey.OP_CONNECT);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        url_1 ="http://192.168.1.100:8080/?action=stream";
                        control_1="192.168.1.100";
                        try {
                            moshiFour.chanel = SocketChannel.open(new InetSocketAddress(control_1,2001));
                            moshiFour.chanel.configureBlocking(false);
                            moshiFour.selector = Selector.open();
                            moshiFour.chanel.register( moshiFour.selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE|SelectionKey.OP_CONNECT);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        url_1 ="http://192.168.1.100:8080/?action=stream";
                        control_1="192.168.1.100";
                        try {
                            moshiFour.chanel = SocketChannel.open(new InetSocketAddress(control_1,2001));
                            moshiFour.chanel.configureBlocking(false);
                            moshiFour.selector = Selector.open();
                            moshiFour.chanel.register( moshiFour.selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE|SelectionKey.OP_CONNECT);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                url_1="http://192.168.1.100:8080/?action=stream";
            }
        });
        yaokongqi = (Yaokongqi1) view.findViewById(R.id.yao);
        yaokongqi.setListener(new Yaokongqi1.MenuListener() {
            @Override
            public void onCenterCliched() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int i =Integer.valueOf(sudu1);
                            String sudu =Integer.toHexString(i);
                            if(sudu.length()==1){
                                sudu = "0"+sudu;
                            }
                            int k =Integer.valueOf(sudu2);
                            String zhuanjiao = Integer.toHexString(k);
                            if(zhuanjiao.length()==1){
                                zhuanjiao = "0"+zhuanjiao;
                            }
                            String jiaoyan1 =null;
                            int j =0;
                            if(Direc1.charAt(1)!=Direc1.charAt(0)){
                                j =i+1;
                            }else{
                                j=i;
                            }
                            String jiaoyan = Integer.toHexString(j+k);
                            if(jiaoyan.length()==1){
                                jiaoyan1 = "0"+jiaoyan;
                            }else{
                                jiaoyan1 = jiaoyan;
                            }
                            sendMsg2(HexStringToBinary("a55a00000000"));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }


            @Override
            public void onUpCliched() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int i =Integer.valueOf(sudu1);
                            String sudu =Integer.toHexString(i);
                            if(sudu.length()==1){
                                sudu = "0"+sudu;
                            }
                            int k =Integer.valueOf(sudu2);
                            String zhuanjiao = Integer.toHexString(k);
                            if(zhuanjiao.length()==1){
                                zhuanjiao = "0"+zhuanjiao;
                            }
                            Direc1="00";
                            String jiaoyan1 =null;
                            String jiaoyan = Integer.toHexString(Integer.valueOf((i+0+k)));
                            if(jiaoyan.length()==1){
                                jiaoyan1 = "0"+jiaoyan;
                            }else{
                                jiaoyan1 = jiaoyan;
                            }
                            Log.e("转角","转角"+zhuanjiao+"方向"+Direc1+"jiaoyan1"+jiaoyan1);
                            sendMsg2(HexStringToBinary("a55a"+sudu+zhuanjiao+Direc1+jiaoyan1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onRightCliched() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int i =Integer.valueOf(sudu1);
                            String sudu =Integer.toHexString(i);
                            if(sudu.length()==1){
                                sudu = "0"+sudu;
                            }
                            int k =Integer.valueOf(sudu2);
                            String zhuanjiao = Integer.toHexString(k);
                            if(zhuanjiao.length()==1){
                                zhuanjiao = "0"+zhuanjiao;
                            }
                            String jiaoyan1 =null;
                            int j =0;
                            if(Direc1.charAt(1)!=Direc1.charAt(0)){
                                j =i+1;
                            }else{
                                j=i;
                            }
                            String jiaoyan = Integer.toHexString(j+k);
                            if(jiaoyan.length()==1){
                                jiaoyan1 = "0"+jiaoyan;
                            }else{
                                jiaoyan1 = jiaoyan;
                            }
                            sendMsg2(HexStringToBinary("a55a"+sudu+zhuanjiao+Direc1+jiaoyan1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onDownCliched() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int i =Integer.valueOf(sudu1);
                            String sudu =Integer.toHexString(i);
                            if(sudu.length()==1){
                                sudu = "0"+sudu;
                            }
                            int k =Integer.valueOf(sudu2);
                            String zhuanjiao = Integer.toHexString(k);
                            if(zhuanjiao.length()==1){
                                zhuanjiao = "0"+zhuanjiao;
                            }
                            Direc1 ="01";
                            String jiaoyan1 =null;
                            String jiaoyan = Integer.toHexString(Integer.valueOf((i+1+k)));
                            if(jiaoyan.length()==1){
                                jiaoyan1 = "0"+jiaoyan;
                            }else{
                                jiaoyan1 = jiaoyan;
                            }

                            sendMsg2(HexStringToBinary("a55a"+sudu+zhuanjiao+Direc1+jiaoyan1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onLeftCliched() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int i =Integer.valueOf(sudu1);
                            String sudu =Integer.toHexString(i);
                            if(sudu.length()==1){
                                sudu = "0"+sudu;
                            }
                            int k =Integer.valueOf(sudu2);
                            String zhuanjiao = Integer.toHexString(k);
                            if(zhuanjiao.length()==1){
                                zhuanjiao = "0"+zhuanjiao;
                            }
                            String jiaoyan1 =null;
                            int j =0;
                            if(Direc1.charAt(1)!=Direc1.charAt(0)){
                                j =i+1;
                            }else{
                                j=i;
                            }
                            String jiaoyan = Integer.toHexString(j+k);
                            if(jiaoyan.length()==1){
                                jiaoyan1 = "0"+jiaoyan;
                            }else{
                                jiaoyan1 = jiaoyan;
                            }
                            sendMsg2(HexStringToBinary("a55a"+sudu+zhuanjiao+Direc1+jiaoyan1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        moshiFour = (MoshiFour) getActivity();
//        Log.e("时间","测试"+moshiFour.getYanshi());
//        tv1.setText("延时：" + moshiFour.getYanshi()+"ms");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onStop() {
        super.onStop();
        moshiFour= (MoshiFour) getActivity();
        Log.e("时间","测试"+moshiFour.getYanshi());
        tv1.setText("延时：" + moshiFour.getYanshi());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public void sendMsg2(byte[] message) throws IOException{

        ByteBuffer writeBuffer=ByteBuffer.allocate(message.length);
        writeBuffer.put(message);
        //注意此处的flip方法，是将当前位置设置为EOF，指针指向0，这样为客户端的读模式做好准备
        writeBuffer.flip();
        moshiFour.chanel.write(writeBuffer);

    }

    public static byte[] HexStringToBinary(String s) {
        int len = s.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return b;
    }



}
