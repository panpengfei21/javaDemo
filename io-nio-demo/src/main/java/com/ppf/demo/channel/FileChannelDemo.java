package com.ppf.demo.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 用file channel写数据到文件里。
 */
public class FileChannelDemo {
    public static void main(String[] args) throws Exception{
        String filePath = "/Users/panpengfei/Desktop/a.txt";
        //write(filePath);
        read(filePath);
    }

    /**
     * 写入到文件
     * @param path
     * @throws IOException
     */
    static void write(String path) throws IOException {
        //1:打开输出流
        FileOutputStream os = new FileOutputStream(path);

        //2:获取file channel
        FileChannel channel = os.getChannel();

        //3:创建一个缓存区，用于写数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //4:写数据到缓存区
        buffer.put("我要写数据到文件中".getBytes());

        //5:要写数据到chanenl了，写之前，要打buffer flip一下。
        buffer.flip();

        //6:开始写
        channel.write(buffer);

        //7:关闭通道
        os.close();
    }

    /**
     * 从文件读取数据
     * @param path
     * @throws IOException
     */
    static void read(String path) throws IOException {
        //0: 文件
        File file = new File(path);

        //1：打开输入流
        FileInputStream is = new FileInputStream(file);

        //2：获取file channel
        FileChannel channel = is.getChannel();

        //3：初始一个缓存区
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());

        //4：读取数据到缓存区
        channel.read(buffer);

        //5：打印
        System.out.println(new String(buffer.array()));
    }
}
