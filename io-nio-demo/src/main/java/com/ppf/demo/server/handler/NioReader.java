package com.ppf.demo.server.handler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class NioReader implements Runnable {

    private SocketChannel sc;
    private Selector selector;

    public NioReader(SocketChannel sc, Selector selector) {
        this.sc = sc;
        this.selector = selector;
    }

    @Override
    public void run() {

        try {
            read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void read() throws IOException {
        byte[] arr = new byte[10];
        ByteBuffer buffer = ByteBuffer.wrap(arr);

        int num = sc.read(buffer);

        if (num == -1) {
            closeChannel();
            return;
        }

        String string = new String(arr, 0, num);

        System.out.println(string);

    }

    private void closeChannel() {
        try {
            sc.finishConnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void test0() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = -1;
        try {
            while ((read = sc.read(buffer)) != -1) {
                String s = new String(buffer.array(),0,read);
                System.out.println(s);

                //ByteBuffer wrap = ByteBuffer.wrap(("接收：" + s).getBytes());
                //sc.write(wrap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
