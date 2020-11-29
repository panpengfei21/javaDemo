package com.ppf.netty.sample.pack.complex.custom.client.handler;

import com.ppf.netty.sample.pack.complex.message.MyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

public class MyMessageEncoderHandler extends MessageToByteEncoder<MyMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MyMessage msg, ByteBuf out) throws Exception {
        System.out.println("数据，编码");
        //把数据写到缓存里
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent().getBytes(Charset.forName("utf-8")));
    }
}
