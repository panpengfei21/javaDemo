package com.ppf.netty.sample.pack.complex.custom.server.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class MyChannelInitializerHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //1:先解析byte[]
        pipeline.addLast(new MyMessageDecoder());
        //2:再处理解析结果
        pipeline.addLast(new MyMessageHandler());
    }
}
