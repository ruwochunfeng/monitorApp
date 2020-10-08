package com.zhoubing.bishe.socket;

/**
 * Created by dell on 2017/12/23.
 */
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.zhoubing.bishe.MainActivity;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class TCPClientReadThread implements Runnable {
    private Selector selector;

    public TCPClientReadThread(Selector selector){
        this.selector=selector;
        new Thread(this).start();

    }

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
            Iterator ite = this.selector.selectedKeys().iterator();
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
                        channel.register(this.selector, SelectionKey.OP_READ);
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

    public void read(SelectionKey key) throws IOException {
        // 服务器可读取消息:得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        Log.e("进来了","是真的进来了了啊");
        System.out.println("服务端收到信息："+msg);
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
        channel.write(outBuffer);// 将消息回送给客户端
    }
}
