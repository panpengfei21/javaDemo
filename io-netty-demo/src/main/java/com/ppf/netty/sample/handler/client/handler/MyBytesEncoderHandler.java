package com.ppf.netty.sample.handler.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * 这是out bound，chain 的最后一步把类型转成字节写出去。
 * 如果上一步传过来的数据不是<Long>，就会直接跳过这个Handler,写出去
 */
public class MyBytesEncoderHandler extends MessageToByteEncoder<Long> {
    /**
     * 把数据转成字节
     * @param ctx 上下文
     * @param msg 要写出动的数据
     * @param out 缓存区。用于保存写出去的字节数据
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("long 转成 message");
        out.writeLong(msg);
    }
}
