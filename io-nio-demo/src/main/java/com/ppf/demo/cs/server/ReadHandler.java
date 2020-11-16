package com.ppf.demo.cs.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ReadHandler implements Runnable{
    SocketChannel sc;

    public ReadHandler(SocketChannel sc) {
        this.sc = sc;
    }

    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            int read = sc.read(buffer);
            if (read == -1) {
                closeChannel();
                return;
            }
            System.out.println("from client:" + new String(buffer.array(),0,read));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeChannel() throws IOException {
        System.out.println("关闭通道");
        sc.close();
    }
}
