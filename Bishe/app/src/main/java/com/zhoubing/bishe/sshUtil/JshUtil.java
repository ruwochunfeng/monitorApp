package com.zhoubing.bishe.sshUtil;

/**
 * Created by dell on 2018/4/9.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class JshUtil {
    private String charset = "UTF-8"; // 设置编码格式
    private String user; // 用户名
    private String passwd; // 登录密码
    private String host; // 主机IP
    private JSch jsch;
    private Session session;

    /**
     *
     * 用户名，密码，主机
     */
    public JshUtil(String user, String passwd, String host) {
        this.user = user;
        this.passwd = passwd;
        this.host = host;
    }

    /**
     * 连接到指定的IP
     *
     * @throws JSchException
     */
    public Session getSession() throws JSchException {
        jsch = new JSch();
        session = jsch.getSession(user, host, 22);
        session.setPassword(passwd);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        return  session;

    }
//
//    /**
//     * 执行相关的命令
//     */
//    public void execCmd() {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//        String command = "";
//        BufferedReader reader = null;
//        Channel channel = null;
//
//        try {
//            while ((command = br.readLine()) != null) {
//                channel = session.openChannel("exec");
//                ((ChannelExec) channel).setCommand(command);
//                channel.setInputStream(null);
//                ((ChannelExec) channel).setErrStream(System.err);
//
//                channel.connect();
//                InputStream in = channel.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(in,
//                        Charset.forName(charset)));
//                String buf = null;
//                while ((buf = reader.readLine()) != null) {
//                    System.out.println(buf);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSchException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                reader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            channel.disconnect();
//            session.disconnect();
//        }
//    }


}
