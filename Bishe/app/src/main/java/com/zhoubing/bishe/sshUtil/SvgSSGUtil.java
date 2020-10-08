package com.zhoubing.bishe.sshUtil;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by dell on 2018/4/27.
 */

public class SvgSSGUtil {

    Session session;
    Channel channel;
    InputStream inputStream;
    BufferedReader bufferedReader;
    Context context ;
    private JSch jsch;

    public SvgSSGUtil(Activity context) throws JSchException {
        this.context = context;
        jsch = new JSch();
        session = jsch.getSession("root", "192.168.1.100", 22);
        session.setPassword("admin");
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
    }

    public Session getSession(){

        return session;
    }


    public void loginIn(){

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
                    inputStream= channel.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line= bufferedReader.readLine())!=null){
                        Log.e("测试信息",line);
                    }
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,"开始指纹定位",Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (final JSchException e) {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,"定位获取失败",Toast.LENGTH_SHORT).show();
                        }
                    });

                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {


                }
            }
        }).start();
    }


    public void exexCommmad(final Session session1 , final String command, final TextView textView){
        new Thread(new Runnable() {
            @Override
            public void run() {

                BufferedReader reader = null;
                Channel channel = null;
                try {
                    if (command != null) {
                        channel = session1.openChannel("exec");
                        ((ChannelExec) channel).setCommand(command);
                        if(command.equals("iwinfo wlan0 scan")){
                            channel.connect();

                            InputStream in = channel.getInputStream();
                            InputStream err = ((ChannelExec) channel).getErrStream();

                            reader = new BufferedReader(new InputStreamReader(in));
                            BufferedReader reader_err = new BufferedReader(new InputStreamReader(err));
                            String buf = null;
                            int  length =0;
                            final StringBuilder sb = new StringBuilder();
                            while ((buf = reader.readLine()) != null&&(length<11)) {
                                final String ceshi = buf.trim();

                                if(ceshi.contains("Signal")){
                                    length++;
                                    int index1 = ceshi.indexOf("-");
                                    String rssi1 = ceshi.substring(index1+1,index1+3);
                                    sb.append("-"+rssi1+"dBm ");

                                }
                            }
                            while ((buf = reader_err.readLine()) != null) {
                                final String ceshi = buf;

                            }
                            ((Activity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText("11组信号强度值："+sb.toString());
                                }
                            });

                        }else{
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (final JSchException e) {
                    e.printStackTrace();
                } finally {
                    channel.disconnect();
                }
            }
        }).start();


    }
}
