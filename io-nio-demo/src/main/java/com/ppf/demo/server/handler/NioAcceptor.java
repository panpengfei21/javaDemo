package com.ppf.demo.server.handler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioAcceptor implements Runnable{

    private Selector selector;
    private ServerSocketChannel ssc;

    public NioAcceptor(Selector selector, ServerSocketChannel ssc) {
        this.selector = selector;
        this.ssc = ssc;
    }

    @Override
    public void run() {
        try {
            SocketChannel socket = ssc.accept();
            System.out.println(socket.getRemoteAddress() + " 进来了。");
            socket.configureBlocking(false);
            socket.register(selector, SelectionKey.OP_READ,new NioReader(socket,selector));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
