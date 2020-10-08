package com.zhoubing.bishe.sshUtil;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SocketFactory;
import com.jcraft.jsch.UserInfo;
import com.zhoubing.bishe.R;
import com.zhoubing.bishe.RssiHistory;
import com.zhoubing.bishe.sqilite.RssiSQLiteHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class AndroidSSh extends AppCompatActivity {
    Session session;
    Channel channel;
    Button button;
    TextView textView;
    Button button1;
    EditText editText;
    EditText editText_ip;
    InputStream inputStream;
    BufferedReader bufferedReader;
    ScrollView scrollView;
    JshUtil jshUtil;
    Button button_huihua;
    Button qingxi_hign,qingxi_middle,qingxi_low;
    EditText editText_x,editText_y;
    Button iwinfo,database;
    private RssiSQLiteHelper rssiSQLiteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_ssh);
        button = (Button) findViewById(R.id.ssh_start);
        button1 = (Button) findViewById(R.id.ceshi_ssh_ceshi);
        button_huihua = (Button) findViewById(R.id.chushihua_ssh);
        scrollView = (ScrollView) findViewById(R.id.scoll);
        qingxi_hign = (Button) findViewById(R.id.qingxi_high);
        qingxi_middle = (Button) findViewById(R.id.qingxi_middle);
        qingxi_low = (Button) findViewById(R.id.qingxi_low);
        iwinfo = (Button) findViewById(R.id.iwinfo);
        database = (Button) findViewById(R.id.database);
        editText_x = (EditText) findViewById(R.id.x);
        editText_y = (EditText) findViewById(R.id.y);
        button.setEnabled(false);
        button1.setEnabled(false);
        textView = (TextView) findViewById(R.id.kaishi);
        editText = (EditText) findViewById(R.id.cmd_ssh);
        editText_ip = (EditText) findViewById(R.id.ip_ssh);
        rssiSQLiteHelper = new RssiSQLiteHelper(this,"RssiValue.db",null,1);




        button_huihua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jshUtil = new JshUtil("root","admin","192.168.1."+editText_ip.getText().toString());
                try {
                    session = jshUtil.getSession();

                    button.setEnabled(true);

                } catch (JSchException e) {
                    e.printStackTrace();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {


                            UserInfo userInfo = new UserInfo() {
                                @Override
                                public String getPassphrase() {
                                    return null;
                                }

                                @Override
                                public String getPassword() {
                                    return null;
                                }

                                @Override
                                public boolean promptPassword(String message) {
                                    return false;
                                }

                                @Override
                                public boolean promptPassphrase(String message) {
                                    return false;
                                }

                                @Override
                                public boolean promptYesNo(String message) {
                                    return false;
                                }

                                @Override
                                public void showMessage(String message) {

                                }
                            };
                            session.setUserInfo(userInfo);

                            session.connect();


                            channel = session.openChannel("shell");


                            channel.setInputStream(System.in);
                            channel.setOutputStream(System.out);
                            channel.connect(3000);
                            if(!channel.isConnected()){


                            }else{
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        textView.append(session.getHost()+" : 登录认证成功"+"\n");

                                        button1.setEnabled(true);
                                    }
                                });
                            }
                            inputStream= channel.getInputStream();
                            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                            String line ;

                            while ((line= bufferedReader.readLine())!=null){
                                Log.e("测试信息",line);
                                final String ceshi = line;
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                            textView.append(ceshi+"\n");

                                    }
                                });

                            }
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    textView.append("现在输入命令：<命令> <选项> <参数>"+"\n");

                                }
                            });



                        } catch (final JSchException e) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    if(textView.getText().toString().contains(session.getHost()+" : 登录认证成功")){
                                        textView.append(session.getHost()+" : 您已经登入成功，无需在登入！！"+"\n");

                                    }else {
                                        textView.append(session.getHost()+" : 登录认证失败"+"\n");

                                        button.setEnabled(false);
                                        button1.setEnabled(false);

                                    }

                                }
                            });
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);

                        }
                    }
                }).start();

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    execCmd(editText.getText().toString());
                } catch (JSchException e) {
                    e.printStackTrace();
                }
            }
        });
        initButton();


    }

    public void initButton(){

        qingxi_hign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String  cmd ="mjpg_streamer -b -i \"input_uvc.so -r 640*480 -f 15 -d /dev/video0\" -o \"output_http.so -p 8080 -w /web\"";
                    execCmd(cmd);
                } catch (JSchException e) {
                    e.printStackTrace();
                }
            }
        });
        qingxi_middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  cmd ="mjpg_streamer -b -i \"input_uvc.so -r 352*288 -f 15 -d /dev/video0\" -o \"output_http.so -p 8080 -w /web\"";
                try {
                    execCmd(cmd);
                } catch (JSchException e) {
                    e.printStackTrace();
                }
            }
        });
        qingxi_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  cmd ="mjpg_streamer -b -i \"input_uvc.so -r 320*240 -f 15 -d /dev/video0\" -o \"output_http.so -p 8080 -w /web\"";
                try {
                    execCmd(cmd);
                } catch (JSchException e) {
                    e.printStackTrace();
                }
            }
        });
        iwinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    execCmd("iwinfo wlan0 scan");
                } catch (JSchException e) {
                    e.printStackTrace();
                }
            }
        });
        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AndroidSSh.this, RssiHistory.class);
                startActivity(intent);
            }
        });

    }

    public  void execCmd(final String command) throws JSchException {
        new Thread(new Runnable() {
            @Override
            public void run() {

                BufferedReader reader = null;
                Channel channel = null;
                try {
                    if (command != null) {
                        channel = session.openChannel("exec");
                        ((ChannelExec) channel).setCommand(command);
                        if(command.equals("ps")){
                            channel.connect();

                            InputStream in = channel.getInputStream();
                            InputStream err = ((ChannelExec) channel).getErrStream();

                            reader = new BufferedReader(new InputStreamReader(in));
                            BufferedReader reader_err = new BufferedReader(new InputStreamReader(err));
                            String buf = null;
                            while ((buf = reader.readLine()) != null) {
                                final String ceshi = buf;
                                if(ceshi.contains("mjpg_streamer")){
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            Log.e("结果：","结果为："+ceshi);
                                            textView.append("来自于"+session.getHost()+":"+command+"回复："+ceshi+" "+"\n");

                                        }
                                    });
                                }
                            }
                            while ((buf = reader_err.readLine()) != null) {
                                final String ceshi = buf;

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        Log.e("结果：","结果为："+ceshi);
                                        textView.append("来自于"+session.getHost()+":"+command+"回复："+ceshi+" "+"\n");

                                    }
                                });
                            }


                        }else if(command.equals("iwinfo wlan0 scan")){
                            channel.connect();

                            InputStream in = channel.getInputStream();
                            InputStream err = ((ChannelExec) channel).getErrStream();

                            reader = new BufferedReader(new InputStreamReader(in));
                            BufferedReader reader_err = new BufferedReader(new InputStreamReader(err));
                            String buf = null;
                            final StringBuilder sb = new StringBuilder();
                            sb.append("[");
                            int  length =0;
                            while ((buf = reader.readLine()) != null&&(length<11)) {
                                final String ceshi = buf.trim();

                                if(ceshi.contains("Signal")){
                                    length++;
                                    int index1 = ceshi.indexOf("-");
                                    String rssi1 = ceshi.substring(index1+1,index1+3);
                                    sb.append("-"+rssi1+"dBm ");
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            Log.e("结果：","结果为："+ceshi);
                                            int index = ceshi.indexOf("-");
                                            String rssi = ceshi.substring(index+1,index+3);
                                            float distance = (Integer.valueOf(rssi)-59)/(10*1.8f);
                                            textView.append("来自于"+session.getHost()+":"+command+"回复："+rssi+" "+distance+"\n");
                                        }
                                    });
                                }
                            }
                            sb.append("]");
                            SQLiteDatabase db = rssiSQLiteHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("location","坐标值:"+editText_x.getText().toString()+","+editText_y.getText().toString());
                            values.put("rssishuzu",sb.toString());
                            Log.e("sb",sb.toString());
                            db.insert("rssi",null,values);
                            while ((buf = reader_err.readLine()) != null) {
                                final String ceshi = buf;
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        Log.e("结果：","结果为："+ceshi);
                                        textView.append("来自于"+session.getHost()+":"+command+"回复："+ceshi+" "+"\n");

                                    }
                                });
                            }

                        }else{
                            channel.connect();

                            InputStream in = channel.getInputStream();
                            InputStream err = ((ChannelExec) channel).getErrStream();

                            reader = new BufferedReader(new InputStreamReader(in));
                            BufferedReader reader_err = new BufferedReader(new InputStreamReader(err));
                            String buf = null;
                            while ((buf = reader.readLine()) != null) {
                                final String ceshi = buf.trim();

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        Log.e("结果：","结果为："+ceshi);

                                        textView.append("来自于"+session.getHost()+":"+command+"回复："+ceshi+" "+"\n");

                                    }
                                });
                            }
                            while ((buf = reader_err.readLine()) != null) {
                                final String ceshi = buf;

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        Log.e("结果：","结果为："+ceshi);
                                        textView.append("来自于"+session.getHost()+":"+command+"回复："+ceshi+" "+"\n");

                                    }
                                });
                            }

                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (final JSchException e) {
                    e.printStackTrace();
                } finally {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                textView.append("现在输入命令：<命令> <选项> <参数>"+"\n");
                                textView.append("\n");
                                scrollView.fullScroll(ScrollView.FOCUS_DOWN);

                            }
                        });

                    channel.disconnect();
                }
            }
        }).start();
    }
}
