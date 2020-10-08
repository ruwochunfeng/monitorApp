package com.zhoubing.bishe;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.zhoubing.bishe.diaglog.ShipinDialog1;
import com.zhoubing.bishe.fragment.jiemian.ControlFragment;
import com.zhoubing.bishe.fragment.jiemian.DataFragment;
import com.zhoubing.bishe.fragment.jiemian.MapFragment;
import com.zhoubing.bishe.fragment.jiemian.PhotoFragment;
import com.zhoubing.bishe.fragment.jiemian.SettingFragment;
import com.zhoubing.bishe.socket.StreamSplit;
import com.zhoubing.bishe.sshUtil.AndroidSSh;
import com.zhoubing.bishe.util.AndroidDiuBaoUtil;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class MoshiFour extends AppCompatActivity  implements View.OnClickListener{

    private PhotoFragment photoFragment;
    private SettingFragment settingFragment;
    public DataFragment dataFragment;
    private MapFragment mapFragment;
    private ControlFragment controlFragment;
    private Handler messageHandler =null;
    private Handler messageHandler1=null;
    private Handler messageHandler2=null;
    private Handler messageHandler3=null;
    private ImageButton photo;
    public SocketChannel chanel;
    public SocketChannel chanel1;
    public Selector selector1;
    public Selector selector;
    private ImageButton set;
    private ImageButton data;
    private ImageButton map;
    private ImageButton control;
    private ImageView image_tongdao1;
    private ImageView image_tongdao2;
    private ImageView image_tongdao3;
    private ImageView image_tongdao4;
    private FragmentManager fragmentManager;
    Toast toast ;
    Toast toast1 ;
    Toast toast2 ;
    Toast toast3 ;
     long last;
    private static long yanshi=0 ;
    int cishu=0;

    private Spinner spinner;
    Boolean isStop =true;
    Boolean isStop1 =true;
    Boolean isStop3 =true;
    Boolean isStop4 =true;
    Boolean tongdao1 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moshi_four);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getFragmentManager();
        initViews();
        setSelectTab(4);
    }

    private void initViews() {
        photo = (ImageButton) findViewById(R.id.photo);
        set = (ImageButton) findViewById(R.id.setting);
        data = (ImageButton) findViewById(R.id.chuanggqni);
        map = (ImageButton) findViewById(R.id.map);
        control = (ImageButton) findViewById(R.id.control);
        image_tongdao1 = (ImageView) findViewById(R.id.tongdao_1);
        image_tongdao2 = (ImageView) findViewById(R.id.tongdao_2);
        image_tongdao3 = (ImageView) findViewById(R.id.tongdao_3);
        image_tongdao4 = (ImageView) findViewById(R.id.tongdao_4);
        Looper looper = Looper.myLooper();

        messageHandler = new MessageHandler(looper);
        messageHandler1 = new MessageHandler1(looper);
        messageHandler2 = new MessageHandler2(looper);
        messageHandler3 = new MessageHandler3(looper);

        photo.setOnClickListener(this);
        set.setOnClickListener(this);
        data.setOnClickListener(this);
        map.setOnClickListener(this);
        control.setOnClickListener(this);
        try {
            chanel = SocketChannel.open(new InetSocketAddress("192.168.1.100",2001));
            Log.e("连接状态","信息"+chanel.isConnected());
            Log.e("连接状态","信息cesjoihihihhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
            chanel.configureBlocking(false);
            selector = Selector.open();
            chanel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE|SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initChannel();

        image_tongdao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStop){
                    isStop = false;
                    new Thread() {

                        public void run() {
                            try {
                                URL url =new URL(controlFragment.url_1);
                                Date curDate = new Date(System.currentTimeMillis());
                                Socket server = new Socket(url.getHost(), url.getPort());
                                Log.e("测试一","连接状态"+server.isConnected());
                                OutputStream os = server.getOutputStream();
                                InputStream is = server.getInputStream();

                                StringBuffer request = new StringBuffer();
                                request.append("GET " + url.getFile() + " HTTP/1.0\r\n");
                                request.append("Host: " + url.getHost() + "\r\n");
                                request.append("\r\n");
                                Log.e("ceshi",""+request.toString());
                                os.write(request.toString().getBytes(), 0, request.length());
                                //is.available()
                                DataInputStream dateInputStream =new DataInputStream(new BufferedInputStream(is));
                                StreamSplit localStreamSplit = new StreamSplit(dateInputStream);
                                Hashtable localHashtable = localStreamSplit.readHeaders();
                                String str3 = (String)localHashtable.get("content-type");
                                int n = str3.indexOf("boundary=");
                                Object localObject2 = "--";
                                if (n != -1){
                                    localObject2 = str3.substring(n + 9);
                                    str3 = str3.substring(0, n);
                                    Log.e("cesi",str3);
                                    if (!((String)localObject2).startsWith("--"))
                                        localObject2 = "--" + (String) localObject2;
                                }
                                if (str3.startsWith("multipart/x-mixed-replace")){
                                    localStreamSplit.skipToBoundary((String)localObject2);
                                }
                                Message message1 = Message.obtain();
                                message1.arg1 = 1;
                                messageHandler.sendMessage(message1);
                                do{
                                    if (localObject2 != null){
                                        localHashtable = localStreamSplit.readHeaders();
                                        if (localStreamSplit.isAtStreamEnd())
                                            break;
                                        str3 = (String)localHashtable.get("content-type");
                                        if (str3 == null)
                                            throw new Exception("No part content type");
                                    }
                                    if (str3.startsWith("multipart/x-mixed-replace")){
                                        n = str3.indexOf("boundary=");
                                        localObject2 = str3.substring(n + 9);
                                        localStreamSplit.skipToBoundary((String)localObject2);
                                    }else{
                                        byte[] localObject3 = localStreamSplit.readToBoundary((String)localObject2);
                                        if (localObject3.length == 0)
                                            break;
                                        tongdao1 =false;
                                        Message message = Message.obtain();
                                        message.arg1 = 0;
                                        message.obj = BitmapFactory.decodeByteArray(localObject3, 0, localObject3.length);
                                        messageHandler.sendMessage(message);
                                        Date endDate = new Date(System.currentTimeMillis());
                                        long diff = endDate.getTime() - curDate.getTime();
                                        yanshi= (diff-last)/2;
                                        Log.e("延时测试",""+yanshi);
                                        cishu++;
                                        if(cishu==30){
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    controlFragment.tv1.setText("延时:"+yanshi+"ms");
                                                    cishu =0;
                                                }
                                            });
                                        }
                                        last =diff;
                                    }
                                    try{
                                        Thread.sleep(10L);
                                    }catch (InterruptedException localInterruptedException){

                                    }
                                }while (!isStop);
                                server.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("错误");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tongdao1 = true;
                                        if(toast == null){
                                            toast = Toast.makeText(MoshiFour.this,"移动目标1有问题",Toast.LENGTH_SHORT);

                                        }else{
                                            toast.setText("移动目标1有问题");
                                            toast.setDuration(Toast.LENGTH_SHORT);
                                        }
                                        toast.show();
                                    }
                                });
                                Message message = Message.obtain();
                                message.arg1 = 1;
                                messageHandler.sendMessage(message);
                            }
                        }
                    }.start();
                }else{

                    isStop = true;
                    if(tongdao1){
                        if(toast == null){
                            toast = Toast.makeText(MoshiFour.this,"移动目标1有问题",Toast.LENGTH_SHORT);

                        }else{
                            toast.setText("移动目标1有问题");
                            toast.setDuration(Toast.LENGTH_SHORT);
                        }
                        toast.show();
                    }

                }
            }
        });

        image_tongdao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStop1){
                    isStop1 = false;

                    new Thread() {

                        public void run() {
                            try {
                                URL url =new URL(controlFragment.url_2);

                                Socket server = new Socket(url.getHost(), url.getPort());
                                OutputStream os = server.getOutputStream();
                                InputStream is = server.getInputStream();


                                StringBuffer request = new StringBuffer();
                                request.append("GET " + url.getFile() + " HTTP/1.0\r\n");
                                request.append("Host: " + url.getHost() + "\r\n");
                                request.append("\r\n");
                                Log.e("ceshi",""+request.toString());
                                os.write(request.toString().getBytes(), 0, request.length());

                                StreamSplit localStreamSplit = new StreamSplit(new DataInputStream(new BufferedInputStream(is)));
                                Hashtable localHashtable = localStreamSplit.readHeaders();

                                String str3 = (String)localHashtable.get("content-type");
                                int n = str3.indexOf("boundary=");
                                Object localObject2 = "--";
                                if (n != -1){
                                    localObject2 = str3.substring(n + 9);
                                    str3 = str3.substring(0, n);
                                    Log.e("cesi",str3);
                                    if (!((String)localObject2).startsWith("--"))
                                        localObject2 = "--" + (String) localObject2;
                                }
                                if (str3.startsWith("multipart/x-mixed-replace")){
                                    localStreamSplit.skipToBoundary((String)localObject2);
                                }
                                Message message1 = Message.obtain();
                                message1.arg1 = 1;
                                messageHandler1.sendMessage(message1);
                                do{
                                    if (localObject2 != null){
                                        localHashtable = localStreamSplit.readHeaders();
                                        if (localStreamSplit.isAtStreamEnd())
                                            break;
                                        str3 = (String)localHashtable.get("content-type");
                                        if (str3 == null)
                                            throw new Exception("No part content type");
                                    }
                                    if (str3.startsWith("multipart/x-mixed-replace")){
                                        n = str3.indexOf("boundary=");
                                        localObject2 = str3.substring(n + 9);
                                        localStreamSplit.skipToBoundary((String)localObject2);
                                    }else{
                                        byte[] localObject3 = localStreamSplit.readToBoundary((String)localObject2);
                                        if (localObject3.length == 0)
                                            break;

                                        Message message = Message.obtain();
                                        message.arg1 = 0;
                                        message.obj = BitmapFactory.decodeByteArray(localObject3, 0, localObject3.length);
                                        messageHandler1.sendMessage(message);

                                    }
                                    try{
                                        Thread.sleep(10L);
                                    }catch (InterruptedException localInterruptedException){

                                    }
                                }while (!isStop1);
                                server.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("错误");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(toast1 == null){
                                            toast1 = Toast.makeText(MoshiFour.this,"移动目标2有问题",Toast.LENGTH_SHORT);

                                        }else{
                                            toast1.setText("移动目标2有问题");
                                            toast1.setDuration(Toast.LENGTH_SHORT);
                                        }
                                        toast1.show();
                                    }
                                });
//                                Toast.makeText(mContext, "无法连接上服务器!", Toast.LENGTH_SHORT).show();
                                Message message = Message.obtain();
                                message.arg1 = 1;
                                messageHandler1.sendMessage(message);
                            }
                        }
                    }.start();
                }else{
                    isStop1 = true;

                }
            }
        });
        image_tongdao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStop3){
                    isStop3 = false;

                    new Thread() {

                        public void run() {
                            try {
                                URL url =new URL(controlFragment.url_1);
                                Date curDate = new Date(System.currentTimeMillis());
                                Socket server = new Socket(url.getHost(), url.getPort());
                                OutputStream os = server.getOutputStream();
                                InputStream is = server.getInputStream();


                                StringBuffer request = new StringBuffer();
                                request.append("GET " + url.getFile() + " HTTP/1.0\r\n");
                                request.append("Host: " + url.getHost() + "\r\n");
                                request.append("\r\n");
                                Log.e("ceshi",""+request.toString());
                                os.write(request.toString().getBytes(), 0, request.length());

                                StreamSplit localStreamSplit = new StreamSplit(new DataInputStream(new BufferedInputStream(is)));
                                Hashtable localHashtable = localStreamSplit.readHeaders();

                                String str3 = (String)localHashtable.get("content-type");
                                int n = str3.indexOf("boundary=");
                                Object localObject2 = "--";
                                if (n != -1){
                                    localObject2 = str3.substring(n + 9);
                                    str3 = str3.substring(0, n);
                                    Log.e("cesi",str3);
                                    if (!((String)localObject2).startsWith("--"))
                                        localObject2 = "--" + (String) localObject2;
                                }
                                if (str3.startsWith("multipart/x-mixed-replace")){
                                    localStreamSplit.skipToBoundary((String)localObject2);
                                }
                                Message message1 = Message.obtain();
                                message1.arg1 = 1;
                                messageHandler2.sendMessage(message1);
                                do{
                                    if (localObject2 != null){
                                        localHashtable = localStreamSplit.readHeaders();
                                        if (localStreamSplit.isAtStreamEnd())
                                            break;
                                        str3 = (String)localHashtable.get("content-type");
                                        if (str3 == null)
                                            throw new Exception("No part content type");
                                    }
                                    if (str3.startsWith("multipart/x-mixed-replace")){
                                        n = str3.indexOf("boundary=");
                                        localObject2 = str3.substring(n + 9);
                                        localStreamSplit.skipToBoundary((String)localObject2);
                                    }else{
                                        byte[] localObject3 = localStreamSplit.readToBoundary((String)localObject2);
                                        if (localObject3.length == 0)
                                            break;

                                        Message message = Message.obtain();
                                        message.arg1 = 0;
                                        message.obj = BitmapFactory.decodeByteArray(localObject3, 0, localObject3.length);
                                        messageHandler2.sendMessage(message);

                                    }
                                    try{
                                        Thread.sleep(10L);
                                    }catch (InterruptedException localInterruptedException){

                                    }
                                }while (!isStop3);
                                server.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("错误");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(toast2 == null){
                                            toast2 = Toast.makeText(MoshiFour.this,"移动目标3有问题",Toast.LENGTH_SHORT);

                                        }else{
                                            toast2.setText("移动目标3有问题");
                                            toast2.setDuration(Toast.LENGTH_SHORT);
                                        }
                                        toast2.show();
                                    }
                                });
//                                Toast.makeText(mContext, "无法连接上服务器!", Toast.LENGTH_SHORT).show();
                                Message message = Message.obtain();
                                message.arg1 = 1;
                                messageHandler2.sendMessage(message);
                            }
                        }
                    }.start();
                }else{
                    isStop3 = true;

                }
            }
        });
        image_tongdao4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStop4){
                    isStop4 = false;

                    new Thread() {

                        public void run() {
                            try {
                                URL url =new URL(controlFragment.url_1);
                                Date curDate = new Date(System.currentTimeMillis());
                                Socket server = new Socket(url.getHost(), url.getPort());
                                OutputStream os = server.getOutputStream();
                                InputStream is = server.getInputStream();


                                StringBuffer request = new StringBuffer();
                                request.append("GET " + url.getFile() + " HTTP/1.0\r\n");
                                request.append("Host: " + url.getHost() + "\r\n");
                                request.append("\r\n");
                                Log.e("ceshi",""+request.toString());
                                os.write(request.toString().getBytes(), 0, request.length());

                                StreamSplit localStreamSplit = new StreamSplit(new DataInputStream(new BufferedInputStream(is)));
                                Hashtable localHashtable = localStreamSplit.readHeaders();

                                String str3 = (String)localHashtable.get("content-type");
                                int n = str3.indexOf("boundary=");
                                Object localObject2 = "--";
                                if (n != -1){
                                    localObject2 = str3.substring(n + 9);
                                    str3 = str3.substring(0, n);
                                    Log.e("cesi",str3);
                                    if (!((String)localObject2).startsWith("--"))
                                        localObject2 = "--" + (String) localObject2;
                                }
                                if (str3.startsWith("multipart/x-mixed-replace")){
                                    localStreamSplit.skipToBoundary((String)localObject2);
                                }
                                Message message1 = Message.obtain();
                                message1.arg1 = 1;
                                messageHandler3.sendMessage(message1);
                                do{
                                    if (localObject2 != null){
                                        localHashtable = localStreamSplit.readHeaders();
                                        if (localStreamSplit.isAtStreamEnd())
                                            break;
                                        str3 = (String)localHashtable.get("content-type");
                                        if (str3 == null)
                                            throw new Exception("No part content type");
                                    }
                                    if (str3.startsWith("multipart/x-mixed-replace")){
                                        n = str3.indexOf("boundary=");
                                        localObject2 = str3.substring(n + 9);
                                        localStreamSplit.skipToBoundary((String)localObject2);
                                    }else{
                                        byte[] localObject3 = localStreamSplit.readToBoundary((String)localObject2);
                                        if (localObject3.length == 0)
                                            break;

                                        Message message = Message.obtain();
                                        message.arg1 = 0;
                                        message.obj = BitmapFactory.decodeByteArray(localObject3, 0, localObject3.length);
                                        messageHandler3.sendMessage(message);

                                    }
                                    try{
                                        Thread.sleep(10L);
                                    }catch (InterruptedException localInterruptedException){

                                    }
                                }while (!isStop4);
                                server.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("错误");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(toast3 == null){
                                            toast3 = Toast.makeText(MoshiFour.this,"移动目标4有问题",Toast.LENGTH_SHORT);

                                        }else{
                                            toast3.setText("移动目标4有问题");
                                            toast3.setDuration(Toast.LENGTH_SHORT);
                                        }
                                        toast3.show();
                                    }
                                });
//                                Toast.makeText(mContext, "无法连接上服务器!", Toast.LENGTH_SHORT).show();
                                Message message = Message.obtain();
                                message.arg1 = 1;
                                messageHandler3.sendMessage(message);
                            }
                        }
                    }.start();
                }else{
                    isStop4 = true;

                }
            }
        });


    }
    class MessageHandler extends Handler {
        public MessageHandler(Looper looper) {
            super(looper);
        }
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 0:
                    image_tongdao1.setImageBitmap((Bitmap)msg.obj);
                    break;
                default:
                    break;
            }

        }
    }

    public void initChannel(){
        try {
            chanel1 = SocketChannel.open(new InetSocketAddress("192.168.1.134",2001));
            Log.e("连接状态","信息22"+chanel1.isConnected());
            chanel1.configureBlocking(false);
            selector1 = Selector.open();
            chanel1.register(selector1, SelectionKey.OP_READ|SelectionKey.OP_WRITE|SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    class MessageHandler1 extends Handler {
        public MessageHandler1(Looper looper) {
            super(looper);
        }
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 0:
                    image_tongdao2.setImageBitmap((Bitmap)msg.obj);
                    break;
                default:
                    break;
            }

        }
    }
    class MessageHandler2 extends Handler {
        public MessageHandler2(Looper looper) {
            super(looper);
        }
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 0:
                    image_tongdao3.setImageBitmap((Bitmap)msg.obj);
                    break;
                default:
                    break;
            }

        }
    }

    class MessageHandler3 extends Handler {
        public MessageHandler3(Looper looper) {
            super(looper);
        }
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 0:
                    image_tongdao4.setImageBitmap((Bitmap)msg.obj);
                    break;
                default:
                    break;
            }

        }
    }

    private void setSelectTab(int index) {
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (photoFragment == null) {
                    photoFragment = new PhotoFragment();
                    transaction.add(R.id.content, photoFragment);
                } else {
                    transaction.show(photoFragment);
                }
                break;
            case 1:
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                    transaction.add(R.id.content, settingFragment);
                } else {
                    transaction.show(settingFragment);
                }
                break;
            case 2:
                if (dataFragment == null) {
                    dataFragment = new DataFragment();
                    transaction.add(R.id.content, dataFragment);
                } else {
                    transaction.show(dataFragment);
                }
                break;
            case 3:
                if (mapFragment == null) {
                    mapFragment = new MapFragment();
                    transaction.add(R.id.content, mapFragment);
                } else {
                    transaction.show(mapFragment);
                }
                break;
            case 4:
                if (controlFragment == null) {
                    controlFragment = new ControlFragment();
                    transaction.add(R.id.content, controlFragment);
                } else {
                    transaction.show(controlFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo:
                setSelectTab(0);
                break;
            case R.id.setting:
                setSelectTab(1);
                break;
            case R.id.chuanggqni:
                setSelectTab(2);
                break;
            case R.id.map:
                setSelectTab(3);
                break;
            case R.id.control:
                setSelectTab(4);
                break;
            default:
                break;
        }
    }

    private void clearSelection() {
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (photoFragment != null) {
            transaction.hide(photoFragment);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
        if (dataFragment != null) {
            transaction.hide(dataFragment);
        }
        if (mapFragment != null) {
            transaction.hide(mapFragment);
        }
        if (controlFragment != null) {
            transaction.hide(controlFragment);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobal,menu);
        return true;
    }

    public void read(SelectionKey key) throws IOException {
        // 服务器可读取消息:得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer);
        byte[] data = buffer.array();
        final String msg = new String(data).trim();
        Log.e("进来了","是真的进来了了啊");
        System.out.println("服务端收到信息："+msg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataFragment.tv1.setText(msg);
            }
        });
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
        channel.write(outBuffer);// 将消息回送给客户端
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.backup:

                break;
            case R.id.backup1:
                Intent intent = new Intent(MoshiFour.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.backup2:
                Intent intent1 = new Intent(MoshiFour.this,LoginHistory.class);
                startActivity(intent1);
                break;
            case R.id.backup3:
                Intent intent2 = new Intent(MoshiFour.this,PingActivity.class);
                startActivity(intent2);
                break;
            case R.id.backup4:
                ShipinDialog1 shipinDialog1 = new ShipinDialog1(this);
                shipinDialog1.show();



                break;
            case R.id.backup5:
                Intent intent3 = new Intent(MoshiFour.this,UDPActivity.class);
                startActivity(intent3);

                break;
            case R.id.backup8:
                /**
                 *   关于功能
                 */
                Intent intent5 = new Intent(MoshiFour.this,FengMapActivity.class);
                startActivity(intent5);

                break;
            case R.id.backup7:
                /**
                 *   开启ssh功能
                 */
                Intent intent4 = new Intent(MoshiFour.this,AndroidSSh.class);
                startActivity(intent4);

                break;
            case R.id.backup6:
                /**
                 *   开启ssh功能
                 */
                Intent intent6 = new Intent(MoshiFour.this,SvgActivity.class);
                startActivity(intent6);

                break;
            default:
                break;
        }
        return true;
    }

    public long getYanshi(){
        return yanshi;
    }
}
