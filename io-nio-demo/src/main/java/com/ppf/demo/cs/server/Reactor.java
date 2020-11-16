package com.ppf.demo.cs.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class Reactor implements Runnable {

    private Selector selector;

    public Reactor(Integer port) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(port));

        selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT,new AcceptorHandler(ssc,selector));
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (selector.select(2000) == 0) {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                dispatch(next);
                iterator.remove();
            }
        }
    }

    private void dispatch(SelectionKey key) {
        Object attachment = key.attachment();
        if (attachment == null) {
            return;
        }
        Runnable runnable = (Runnable) attachment;
        runnable.run();
    }

    public static void main(String[] args) throws IOException {
        Reactor reactor = new Reactor(6666);
        reactor.run();
    }
}
