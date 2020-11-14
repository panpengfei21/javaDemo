package com.ppf.demo.channel;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 用file channel写数据到文件里。
 */
public class FileChannelDemo {
    public static void main(String[] args) throws Exception{
        //1:打开输出流
        FileOutputStream os = new FileOutputStream("/Users/panpengfei/Desktop/a.txt");

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
}
