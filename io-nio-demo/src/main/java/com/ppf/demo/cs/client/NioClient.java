package com.ppf.demo.cs.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NioClient {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6666);

        if (!channel.connect(address)) {
            while (!channel.finishConnect()) {
                System.out.println("=========");
            }
        }

        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        while (!s.equals("exit")){
            System.out.println("output:" + s);
            ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
            channel.write(buffer);
            s = scanner.nextLine();
        }

        scanner.close();
        channel.close();
    }
}
