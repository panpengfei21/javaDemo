package com.ppf.demo.zeroCopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class ZcClient {

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);

        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 7777);

        if (!sc.connect(address)) {
            while (!sc.finishConnect()) {

            }
        }

        String fileName = "io-netty-demo/src/main/resources/a.txt";
        FileChannel fc = new FileInputStream(fileName).getChannel();

        // 这里也是零拷贝，
        fc.transferTo(0,fc.size(),sc);

        fc.close();
    }
}
