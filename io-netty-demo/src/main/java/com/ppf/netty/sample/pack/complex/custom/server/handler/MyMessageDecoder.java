package com.ppf.netty.sample.pack.complex.custom.server.handler;

import com.ppf.netty.sample.pack.complex.message.MyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.util.List;

public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("接收到数据.....要解码");
        //1:读取长度
        int length = in.readInt();

        //2：根据长度，读取内容
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        String content = new String(bytes, Charset.forName("utf-8"));

        //3:转成约定的类型
        MyMessage msg = new MyMessage();
        msg.setLength(length);
        msg.setContent(content);

        //4:传给下一个handler处理
        out.add(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("decoder active");
        super.channelActive(ctx);
    }
}
