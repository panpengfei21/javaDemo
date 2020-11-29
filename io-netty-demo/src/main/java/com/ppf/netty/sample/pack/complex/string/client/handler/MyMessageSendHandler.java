package com.ppf.netty.sample.pack.complex.string.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class MyMessageSendHandler extends SimpleChannelInboundHandler<String>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道激活，发送");
        for (int i = 0; i < 10; i++) {
            ctx.writeAndFlush("冬天到了，我在。！" + new Random().nextInt(100));

        }
    }
}
