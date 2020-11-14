package com.ppf.demo.server;

import com.ppf.demo.server.handler.NioAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * Nio 开启并监听一个端口。等待有人连接进来。
 */
public class NioServer implements Runnable{

    // 端口
    private Integer port;
    // 选择器
    private Selector selector;
    // 服务器的socket
    private ServerSocketChannel ssc;

    public NioServer(Integer port) throws IOException {
        this.port = port;

        //1:打开一个选择器
        selector = Selector.open();

        //2:打开一个服务器的socket
        ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT,new NioAcceptor(selector,ssc));
    }


    @Override
    public void run() {
        System.out.println("开始监听端口：" + port);
        while (!Thread.interrupted()){
            try {
                if (selector.select(1000) > 0) {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                dispatchSelectionKey(sk);
                iterator.remove();
            }
        }
    }


    private void dispatchSelectionKey(SelectionKey sk) {
        Runnable r = (Runnable) sk.attachment();
        if (r != null)
            r.run();
    }
}
