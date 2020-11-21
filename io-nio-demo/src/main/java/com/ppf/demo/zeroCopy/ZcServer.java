package com.ppf.demo.zeroCopy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class ZcServer {

    public static void main(String[] args) throws IOException {
        ZcServer server = new ZcServer(7777);
        server.listen();
    }

    private Selector selector;
    private ServerSocketChannel ssc;

    public ZcServer(Integer port) throws IOException {
        selector = Selector.open();

        ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT, new Acceptor(ssc,selector));
    }


    public void listen() {
        while (true) {
            try {
                int select = selector.select(2000);
                if (select == 0) {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                dispatchSelectedKey(iterator.next());
                iterator.remove();
            }
        }
    }

    private void dispatchSelectedKey(SelectionKey key) {
        Runnable runnable = (Runnable) key.attachment();

        if (runnable != null) {
            runnable.run();
        }
    }
    // 接收通道连接
    class Acceptor implements Runnable {
        private ServerSocketChannel ssc;
        private Selector selector;

        public Acceptor(ServerSocketChannel ssc, Selector selector) {
            this.ssc = ssc;
            this.selector = selector;
        }


        @Override
        public void run() {
            try {
                SocketChannel channel = ssc.accept();
                System.out.println("somebody is coming");
                channel.configureBlocking(false);
                channel.register(selector,SelectionKey.OP_READ,new Reader(channel,selector));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 读取数据
    class Reader implements Runnable {

        private SocketChannel sc;
        private Selector selector;
        //文件位置
        private String fileName = "io-netty-demo/src/main/resources/b.txt";
        //文件输出输
        private FileOutputStream fos ;
        //文件开始写的位置
        private long position = 0;

        public Reader(SocketChannel sc,Selector selector) throws FileNotFoundException {
            this.sc = sc;
            this.selector = selector;
            this.fos = new FileOutputStream(fileName);

        }

        @Override
        public void run() {
            try {
                FileChannel fc = fos.getChannel();

                // 这里就是零拷贝。
                long l = fc.transferFrom(sc, position, 4);

                position += l;
                if (l == 0) {
                    closeChannel();
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //关闭所有流
        private void closeChannel() throws IOException {
            System.out.println("有人退出了。");
            fos.close();
            sc.keyFor(selector).cancel();
            sc.close();
        }
    }
}
