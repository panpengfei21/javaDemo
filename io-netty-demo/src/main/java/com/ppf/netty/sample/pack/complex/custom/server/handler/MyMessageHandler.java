package com.ppf.netty.sample.pack.complex.custom.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import com.ppf.netty.sample.pack.complex.message.MyMessage;


public class MyMessageHandler extends SimpleChannelInboundHandler<MyMessage>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage msg) throws Exception {
        System.out.println("客房端来信：" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常:" + cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server 通道激活。");
    }
}

