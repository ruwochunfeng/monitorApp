package com.zhoubing.bishe;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gcssloop.view.utils.MathUtils;
import com.gcssloop.widget.RockerView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.zhoubing.bishe.diaglog.DuojiDiaglog;
import com.zhoubing.bishe.socket.StreamSplit;
import com.zhoubing.bishe.socket.TCPClientReadThread;
import com.zhoubing.bishe.sqilite.MySQLiteHelper;
import com.zhoubing.bishe.util.CustomDialog;
import com.zhoubing.bishe.view.ProgressiveGauge;
import com.zhoubing.bishe.view.SpeedControl;
import com.zhoubing.bishe.view.Yaokongqi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

public class MainActivity extends Activity {

    private static  int id =0;
    private Handler messageHandler =null;
    private Handler messageHandler1 =null;
    ImageView myImage;
    ImageView myImage1;
    Boolean isStop =true;
    Boolean isStop1 =true;
    private String conStr = "http://192.168.1.133:8080/?action=stream";
    private String conStr1 = "http://192.168.1.100:8080/?action=stream";
    Socket socket1,socket2;
    BufferedReader bi1,bi2;
    BufferedWriter bw1,bw2;
    public static TextView tv1,tv2;
    private ProgressiveGauge  gauge,gauge1;
    private ImageView image;
    SocketChannel chanel,channel1;
    Selector selector,selector1;
    Yaokongqi yaokongqi,yaokongqi1;
    Spinner spinner,spinner1;
    String sudu1 ="10";
    private static String hexStr =  "0123456789ABCDEF";
    String sudu2 ="10";
    FloatingActionButton duoji1,duoji2,duoji3;
    long last,last1,chaju;
    String Direc1,Direc2="00";
    SimpleDateFormat df;


    int i =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        myImage = (ImageView) findViewById(R.id.surface1);
        myImage1 = (ImageView) findViewById(R.id.surface2);
        gauge = (ProgressiveGauge) findViewById(R.id.speedshow);
        gauge1 = (ProgressiveGauge) findViewById(R.id.speedshow1);
        duoji1 = (FloatingActionButton) findViewById(R.id.duoji);
        duoji2 = (FloatingActionButton) findViewById(R.id.duoji1);
        duoji3 = (FloatingActionButton) findViewById(R.id.duoji2);
      //  image = (ImageView) findViewById(R.id.zhizhen);

        tv1= (TextView) findViewById(R.id.text);
        tv2= (TextView) findViewById(R.id.text1);
        Log.e("a的长度=",""+"a".getBytes().length);
        FloatingActionButton  luzhi1 = (FloatingActionButton) findViewById(R.id.luzhi1);
        FloatingActionButton luzhi2 = (FloatingActionButton) findViewById(R.id.luzhi2);
        FloatingActionButton shuchu1 = (FloatingActionButton) findViewById(R.id.chuankou);
        FloatingActionButton shuchuan2 = (FloatingActionButton) findViewById(R.id.luzhi3);
        FloatingActionButton power = (FloatingActionButton) findViewById(R.id.power);
        FloatingActionButton power1 = (FloatingActionButton) findViewById(R.id.power1);

        try {
            InetAddress inetAddress = InetAddress.getByName("192.168.1.124");
//            socket1 = new Socket(inetAddress,2001);
//            Log.e("连接状态","结果为1234"+socket1.isConnected());
//            socket2 = new Socket("192.168.1.130",2000);

//            bi1= new BufferedReader(new InputStreamReader(socket1.getInputStream()));
//            bi2= new BufferedReader(new InputStreamReader(socket2.getInputStream()));
//            bw1= new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));
//            bw2= new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        yaokongqi = (Yaokongqi) findViewById(R.id.yaokongqi);
        yaokongqi1 = (Yaokongqi) findViewById(R.id.yaokongqi1);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        duoji1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DuojiDiaglog duojiDiaglog = new DuojiDiaglog(getApplicationContext());
                duojiDiaglog.show();
            }
        });
        duoji2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DuojiDiaglog duojiDiaglog = new DuojiDiaglog(getApplicationContext());
                duojiDiaglog.show();
            }
        });
        duoji3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DuojiDiaglog duojiDiaglog = new DuojiDiaglog(getApplicationContext());
                duojiDiaglog.show();
            }
        });

             /*
                控制命令和串口接收的监听
             */
        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog1 = new CustomDialog(MainActivity.this);
                customDialog1.show();
            }
        });

        power1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,SettingActivitu.class);
                startActivity(intent);

            }
        });
        shuchu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    chanel = SocketChannel.open(new InetSocketAddress("192.168.1.133",2001));Log.e("连接状态","信息"+chanel.isConnected());
                    Log.e("连接状态","信息cesjoihihihhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                    chanel.configureBlocking(false);
                    selector = Selector.open();
                    chanel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE|SelectionKey.OP_CONNECT);
                   // new TCPClientReadThread(selector);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                // 选择一组可以进行I/O操作的事件，放在selector中,客户端的该方法不会阻塞，
                                //这里和服务端的方法不一样，查看api注释可以知道，当至少一个通道被选中时，
                                //selector的wakeup方法被调用，方法返回，而对于客户端来说，通道一直是被选中的
                                try {
                                    selector.select();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Iterator ite = selector.selectedKeys().iterator();
                                while (ite.hasNext()) {
                                    SelectionKey key = (SelectionKey) ite.next();
                                    // 删除已选的key,以防重复处理
                                    ite.remove();
                                    // 连接事件发生
                                    if (key.isConnectable()) {
                                        SocketChannel channel = (SocketChannel) key
                                                .channel();
                                        // 如果正在连接，则完成连接
                                        try {
                                            if (channel.isConnectionPending()) {

                                                channel.finishConnect();


                                            }
                                            // 设置成非阻塞
                                            channel.configureBlocking(false);
                                            Log.e("进来了","连接成功");
                                            //在这里可以给服务端发送信息哦
                                            // channel.write(ByteBuffer.wrap(new String("向服务端发送了一条信息").getBytes()));
                                            //在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
                                            channel.register(selector, SelectionKey.OP_READ);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        // 获得了可读的事件
                                    } else if (key.isReadable()) {
                                        try {
                                            Log.e("进来了","读数据成功");
                                            read(key);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }
                        }
                    }).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

             /*
                按钮测试
             */
        shuchuan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    chanel = SocketChannel.open(new InetSocketAddress("192.168.1.100",2001));Log.e("连接状态","信息"+chanel.isConnected());
                    Log.e("连接状态","信息cesjoihihihhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                    chanel.configureBlocking(false);
                    selector = Selector.open();
                    chanel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE|SelectionKey.OP_CONNECT);
                    // new TCPClientReadThread(selector);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                // 选择一组可以进行I/O操作的事件，放在selector中,客户端的该方法不会阻塞，
                                //这里和服务端的方法不一样，查看api注释可以知道，当至少一个通道被选中时，
                                //selector的wakeup方法被调用，方法返回，而对于客户端来说，通道一直是被选中的
                                try {
                                    selector.select();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Iterator ite = selector.selectedKeys().iterator();
                                while (ite.hasNext()) {
                                    SelectionKey key = (SelectionKey) ite.next();
                                    // 删除已选的key,以防重复处理
                                    ite.remove();
                                    // 连接事件发生
                                    if (key.isConnectable()) {
                                        SocketChannel channel = (SocketChannel) key
                                                .channel();
                                        // 如果正在连接，则完成连接
                                        try {
                                            if (channel.isConnectionPending()) {

                                                channel.finishConnect();


                                            }
                                            // 设置成非阻塞
                                            channel.configureBlocking(false);
                                            Log.e("进来了","连接成功");
                                            //在这里可以给服务端发送信息哦
                                            // channel.write(ByteBuffer.wrap(new String("向服务端发送了一条信息").getBytes()));
                                            //在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
                                            channel.register(selector, SelectionKey.OP_READ);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        // 获得了可读的事件
                                    } else if (key.isReadable()) {
                                        try {
                                            Log.e("进来了","读数据成功");
                                            read1(key);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }
                        }
                    }).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
         /*
                1.面向操作者左侧的下拉菜单按钮监听
             */
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] languages = getResources().getStringArray(R.array.sudu);
                sudu1 = languages[position];
                Toast.makeText(MainActivity.this,"A速度为"+sudu1+" B速度为"+sudu2,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*
                1.面向操作者右侧的下拉菜单按钮监听
             */
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] languages = getResources().getStringArray(R.array.sudu);
                sudu2 = languages[position];
                Toast.makeText(MainActivity.this,"A速度为"+sudu1+" B速度为"+sudu2,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
                  /*
                1.面向操作者左侧的录制按钮的监听
             */
        luzhi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStop){
                    isStop = false;

                    new Thread() {

                        public void run() {
                            try {
                                Date curDate = new Date(System.currentTimeMillis());
                                URL url =new URL(conStr);

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

                                        Message message = Message.obtain();
                                        message.arg1 = 0;
                                        message.obj = BitmapFactory.decodeByteArray(localObject3, 0, localObject3.length);
                                        if(message.obj!=null){
                                            int i =(((Bitmap)message.obj).getHeight())*(((Bitmap)message.obj).getRowBytes())/1024;
                                            Log.e("字节大小",""+i);
                                        }
                                        messageHandler.sendMessage(message);
                                        Date endDate = new Date(System.currentTimeMillis());
                                        long diff = endDate.getTime() - curDate.getTime();
                                        Log.e("延时测试",""+(diff-last)/2);
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
//                                Toast.makeText(mContext, "无法连接上服务器!", Toast.LENGTH_SHORT).show();
                                Message message = Message.obtain();
                                message.arg1 = 1;
                                messageHandler.sendMessage(message);
                            }
                        }
                    }.start();
                }else{
                    isStop = true;

                }
            }
        });

              /*
                1.面向操作者右侧的录制按钮的监听
             */
        luzhi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStop1){
                    isStop1 = false;

                    new Thread() {
                        @SuppressWarnings("unchecked")
                        public void run() {
                            try {

                                URL url =new URL(conStr1);
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
                                message1.arg1 = 0;
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
                                        Message message = Message.obtain();
                                        message.arg1 = 1;
                                        message.obj = BitmapFactory.decodeByteArray(localObject3, 0, localObject3.length);
                                        messageHandler1.sendMessage(message);
                                        Date endDate = new Date(System.currentTimeMillis());
                                        long diff1 = endDate.getTime() - curDate.getTime();
                                        chaju=(diff1-last1)/2;
                                        Log.e("延时1测试",""+chaju);

//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                yanshi.setText("延时："+chaju);
//                                            }
//                                        });
                                        last1 =diff1;


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
                                Toast.makeText(MainActivity.this, "无法连接上服务器!", Toast.LENGTH_SHORT).show();
                                Message message = Message.obtain();
                                message.arg1 = 0;
                                messageHandler1.sendMessage(message);
                            }
                        }
                    }.start();
                }else{
                    isStop1 = true;

                }
            }
        });
        Looper looper = Looper.myLooper();
        messageHandler = new MessageHandler(looper);
        messageHandler1 = new MessageHandler1(looper);
             /*
                1.面向操作者左侧的遥控器模式按钮
             */
        yaokongqi.setListener(new Yaokongqi.MenuListener() {
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
                            Direc1 = "00";
                            String zhuanjiao = "00";
                            String jiaoyan1 =null;
                            int j =0;
                            if(Direc1.charAt(1)!=Direc1.charAt(0)){
                                j =i+1;
                            }else{
                                j=i;
                            }
                            String jiaoyan = Integer.toHexString(j);
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
                            String zhuanjiao = "00";
                            String jiaoyan1 =null;
                            String jiaoyan = Integer.toHexString(Integer.valueOf((i+0+0)));
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
                            String zhuanjiao = "00";
                            String jiaoyan1 =null;
                            int j =0;
                            if(Direc1.charAt(1)!=Direc1.charAt(0)){
                                j =i+1;
                            }else{
                                j=i;
                            }
                            String jiaoyan = Integer.toHexString(j);
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
                            String zhuanjiao = "00";
                            Direc1 ="01";
                            String jiaoyan1 =null;
                            String jiaoyan = Integer.toHexString(Integer.valueOf((i+1+0)));
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
                            String zhuanjiao = "00";
                            String jiaoyan1 =null;
                            int j =0;
                            if(Direc1.charAt(1)!=Direc1.charAt(0)){
                                j =i+1;
                            }else{
                                j=i;
                            }
                            String jiaoyan = Integer.toHexString(j);
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
             /*
                1.面向操作者右侧的遥控器模式按钮
             */
        yaokongqi1.setListener(new Yaokongqi.MenuListener() {
            @Override
            public void onCenterCliched() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int i =Integer.valueOf(sudu2);
                            String sudu =Integer.toHexString(i);
                            if(sudu.length()==1){
                                sudu = "0"+sudu;
                            }
                            String zhuanjiao = "00";

                            String jiaoyan1 =null;
                            int j =0;
                            if(Direc2.charAt(1)!=Direc2.charAt(0)){
                                j =i+1;
                            }else{
                                j=i;
                            }
                            String jiaoyan = Integer.toHexString(j);
                            if(jiaoyan.length()==1){
                                jiaoyan1 = "0"+jiaoyan;
                            }else{
                                jiaoyan1 = jiaoyan;
                            }
                            Log.e("数据格式为","a55a"+sudu+zhuanjiao+"00"+jiaoyan1);
                            sendMsg2(HexStringToBinary("a55a"+sudu+zhuanjiao+Direc2+jiaoyan1));

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
                            int i =Integer.valueOf(sudu2);
                            String sudu =Integer.toHexString(i);
                            if(sudu.length()==1){
                                sudu = "0"+sudu;
                            }
                            Direc2 = "00";
                            String zhuanjiao = "00";
                            String jiaoyan1 =null;
                            String jiaoyan = Integer.toHexString(Integer.valueOf((i+0+0)));
                            if(jiaoyan.length()==1){
                                jiaoyan1 = "0"+jiaoyan;
                            }else{
                                jiaoyan1 = jiaoyan;
                            }
                            Log.e("数据格式为","a55a"+sudu+zhuanjiao+"00"+jiaoyan1);
                            sendMsg2(HexStringToBinary("a55a"+sudu+zhuanjiao+Direc2+jiaoyan1));
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
                            int i =Integer.valueOf(sudu2);
                            String sudu =Integer.toHexString(i);
                            if(sudu.length()==1){
                                sudu = "0"+sudu;
                            }
                            String zhuanjiao = "00";
                            String jiaoyan1 =null;
                            int j =0;
                            if(Direc2.charAt(1)!=Direc2.charAt(0)){
                                j =i+1;
                            }else{
                                j=i;
                            }
                            String jiaoyan = Integer.toHexString(j);
                            if(jiaoyan.length()==1){
                                jiaoyan1 = "0"+jiaoyan;
                            }else{
                                jiaoyan1 = jiaoyan;
                            }
                            Log.e("数据格式为","a55a"+sudu+zhuanjiao+"00"+jiaoyan1);
                            sendMsg2(HexStringToBinary("a55a"+sudu+zhuanjiao+Direc2+jiaoyan1));
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
                            int i =Integer.valueOf(sudu2);
                            String sudu =Integer.toHexString(i);
                            if(sudu.length()==1){
                                sudu = "0"+sudu;
                            }
                            String zhuanjiao = "00";
                            Direc2 ="01";
                            String jiaoyan1 =null;
                            String jiaoyan = Integer.toHexString(Integer.valueOf((i+1+0)));
                            if(jiaoyan.length()==1){
                                jiaoyan1 = "0"+jiaoyan;
                            }else{
                                jiaoyan1 = jiaoyan;
                            }
                            Log.e("数据格式为","a55a"+sudu+zhuanjiao+"00"+jiaoyan1);
                            sendMsg2(HexStringToBinary("a55a"+sudu+zhuanjiao+Direc2+jiaoyan1));
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
                            int i =Integer.valueOf(sudu2);
                            String sudu =Integer.toHexString(i);
                            if(sudu.length()==1){
                                sudu = "0"+sudu;
                            }
                            String zhuanjiao = "00";
                            String jiaoyan1 =null;
                            int j =0;

                            if(Direc2.charAt(1)!=Direc2.charAt(0)){

                                j =i+1;
                            }else{
                                j=i;
                            }
                            String jiaoyan = Integer.toHexString(Integer.valueOf(j));
                            if(jiaoyan.length()==1){
                                jiaoyan1 = "0"+jiaoyan;
                            }else{
                                jiaoyan1 = jiaoyan;
                            }
                            Log.e("数据格式为","a55a"+sudu+zhuanjiao+"00"+jiaoyan1);
                            sendMsg2(HexStringToBinary("a55a"+sudu+zhuanjiao+Direc2+jiaoyan1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
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
                    myImage.setImageBitmap((Bitmap)msg.obj);

                    break;
                default:
                    break;
            }

        }
    }

              /*
                图像传输左侧的Handler
             */
    class MessageHandler1 extends Handler {
        public MessageHandler1(Looper looper) {
            super(looper);
        }
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 1:
                    myImage1.setImageBitmap((Bitmap)msg.obj);
                    break;
                default:
                    break;
            }

        }
    }

    public void sendMsg2(byte[] message) throws IOException{

        ByteBuffer writeBuffer=ByteBuffer.allocate(message.length);
        writeBuffer.put(message);
        //注意此处的flip方法，是将当前位置设置为EOF，指针指向0，这样为客户端的读模式做好准备
        writeBuffer.flip();
        chanel.write(writeBuffer);

    }


    //a55a500F0160
    public void sendMsg1() throws IOException{
        byte[] bytes =new byte[6];
        bytes[0]=(byte) 0xa5;
        bytes[1]=(byte) 0x5a;
        bytes[2]=(byte) 0x50;
        bytes[3]=(byte) 0x0F;
        bytes[4]=(byte) 0x01;
        bytes[5]=(byte) 0x60;

        ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        //注意此处的flip方法，是将当前位置设置为EOF，指针指向0，这样为客户端的读模式做好准备
        writeBuffer.flip();
        chanel.write(writeBuffer);

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


    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
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
                tv1.setText(msg);
            }
        });
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
        channel.write(outBuffer);// 将消息回送给客户端
    }

    public void read1(SelectionKey key) throws IOException {
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
                tv2.setText(msg);
            }
        });
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
        channel.write(outBuffer);// 将消息回送给客户端
    }

    @Override
    protected void onStop() {
        super.onStop();



    }
}
