package com.ppf.demo.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class IOServer {
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        int port = 7878;
        //1：开始监听port
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("开始监听" + port);

        while (true) {
            //2:等待客户端的连接
            Socket socket = serverSocket.accept();
            System.out.println(socket.getInetAddress().getHostAddress() + "->进来了");

            //3：获取输入流
            try(InputStreamReader reader
                        = new InputStreamReader(
                                new BufferedInputStream(
                                        socket.getInputStream()
                                )
                          )
            ){
                char[] buffer = new char[1024];
                int length;
                //4：读取客户端的输入数据
                while ((length = reader.read(buffer)) != -1) {
                    String s = new String(buffer, 0, length);
                    System.out.println(s);
                }
            }
            System.out.println("关闭连接");
            socket.close();
        }
    }
}
