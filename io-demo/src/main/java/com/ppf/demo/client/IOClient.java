package com.ppf.demo.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class IOClient {
    public static void main(String[] args) throws IOException {
        //1:开启一个连接到某个IP，某个端口的socket
        Socket socket = new Socket("127.0.0.1", 7878);

        //2：获取输出流
        try(OutputStreamWriter writer
                    = new OutputStreamWriter(
                            new BufferedOutputStream(
                                    socket.getOutputStream()
                            )
                    )
        ) {
            //3：等待输入
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            while (!"exit".equals(next)) {
                //4：输出
                writer.write(next);
                writer.flush();
                next = scanner.next();
            }
        }
        //5：断开连接
        socket.close();
        System.out.println("断开连接");
    }
}
