package com.ppf.demo.channel.socketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class MutipleBufferDemo {
    public static void main(String[] args) throws IOException {
        int port = 7000;
        //1:打开服务器的socket
        ServerSocketChannel ssc = ServerSocketChannel.open();

        //2:绑定7000端口
        ssc.socket().bind(new InetSocketAddress(port));
        System.out.println("监听" + port + "端口");

        //3:等待连接
        SocketChannel sc = ssc.accept();
        System.out.println("有人进来了.");

        //4：buffer数组
        ByteBuffer[] buffers = new ByteBuffer[3];

        for (int i = 0; i < buffers.length; i++) {
            buffers[i] = ByteBuffer.allocate(10);
        }


        //5：读取数据
        while (true) {
            long read;
            while ((read = sc.read(buffers)) > 0) {
                Arrays.stream(buffers)
                        .peek(c-> System.out.println("limit:" + c.limit() + " position:" + c.position()))
                        .forEach(c->c.flip());
                sc.write(buffers);
            }

            Arrays.stream(buffers).forEach(c->c.clear());
        }
    }
}
