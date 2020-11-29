package com.ppf.netty.sample.pack.complex.string.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyMessageEncoderHandler extends MessageToByteEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        System.out.println("数据，编码");
        //1:拿到要传送的字节
        byte[] bytes = msg.getBytes();

        //2:获取长度
        int length = bytes.length;

        //3:把数据写到缓存里
        out.writeInt(length);
        out.writeBytes(bytes);
    }
}
