package com.ppf.demo.channel.fileChannel;

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
        //read(filePath);

        copyFile("/Users/panpengfei/Desktop/a.png","/Users/panpengfei/Desktop/b.png");
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

        //6：关闭
        is.close();
    }

    /**
     * 拷贝文件
     * @param src 源文件
     * @param dst 目标地址
     * @throws IOException
     */
    static void copyFile(String src,String dst) throws IOException {

        //1:打开输入输出流
        FileInputStream is = new FileInputStream(src);
        FileOutputStream os = new FileOutputStream(dst);

        //2：分别打开channel
        FileChannel isChannel = is.getChannel();
        FileChannel osChannel = os.getChannel();

        //3：把输入channel数据输到输出channel(就是数据直接从输入通道转到输出通道)
        isChannel.transferTo(0,isChannel.size(),osChannel);

        //4：关闭流
        is.close();
        os.close();
    }
}
