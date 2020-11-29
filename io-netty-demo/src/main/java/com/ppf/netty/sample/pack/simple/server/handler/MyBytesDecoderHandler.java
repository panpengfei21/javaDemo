package com.ppf.netty.sample.pack.simple.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 把字节转成Long
 */
public class MyBytesDecoderHandler extends ByteToMessageDecoder
{
    /**
     * 把字节转成Long
     * @param ctx 上下文
     * @param in socket传来的字节保存在这里
     * @param out out 大于1，就会传给下一个handler处理
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("接收bytes");
        //因为一个Long 4个字节。小于8个字节就放过，不读.  64-bits
        if (in.readableBytes() < 8) {
            return;
        }
        //读取一个Long
        long l = in.readLong();
        out.add(l);
    }
}
