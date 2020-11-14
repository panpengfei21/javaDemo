package com.ppf.demo.buffer;

import java.nio.IntBuffer;

public class BufferTest {
    public static void main(String[] args) {
        //1:创建一个可以容纳10个int的buffer
        IntBuffer buffer = IntBuffer.allocate(10);

        //2:写数据到buffer
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i * 2);
        }

        //3:读写转换(就是游标重置到启始位置)
        buffer.flip();

        //3:读取数据
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
