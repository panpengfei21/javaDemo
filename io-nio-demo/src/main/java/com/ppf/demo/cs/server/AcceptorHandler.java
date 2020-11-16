package com.ppf.demo.cs.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 用于接收通道连接。
 */
public class AcceptorHandler implements Runnable {

    ServerSocketChannel ssc;
    Selector selector;

    public AcceptorHandler(ServerSocketChannel ssc, Selector selector) {
        this.ssc = ssc;
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            SocketChannel channel = ssc.accept();
            System.out.println("有人进来了。");
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ,new ReadHandler(channel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
