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

//        new ReadThread(socket.getInputStream())

        //new Thread(new ReadThread(socket.getInputStream())).start();
        //new Thread(new WriteThread(socket.getOutputStream())).start();

        while (true){
        }
        //5：断开连接
//        socket.close();
//        System.out.println("断开连接");
    }
}

class ReadThread implements Runnable {
    private InputStream inputStream;
    private char[] buffer = new char[1024];
    private int length;

    public ReadThread(InputStream input) {
        this.inputStream = input;
    }

    @Override
    public void run() {
        System.out.println("等待读");
        try (InputStreamReader reader = new InputStreamReader(new BufferedInputStream(inputStream))){
            while ((length = reader.read(buffer)) != -1 ) {
                System.out.println("开始读");
                System.out.println(new String(buffer,0,length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("结束读");
    }
}

class WriteThread implements Runnable {
    private OutputStream output;
    private char[] buffer = new char[1024];
    private int length =  -1;

    public WriteThread(OutputStream output) {
        this.output = output;
    }

    @Override
    public void run() {
        System.out.println("等待写");
        try(OutputStreamWriter writer = new OutputStreamWriter(new BufferedOutputStream(output))) {
            //3：等待输入
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            while (!"exit".equals(next)) {
                //4：输出
                writer.write(next);
                writer.flush();
                next = scanner.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

