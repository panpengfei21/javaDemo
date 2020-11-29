package com.ppf.netty.sample.pack.complex.custom.client.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ChannelInitializerHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //2:加入编码handler
        pipeline.addLast(new MyMessageEncoderHandler());
        //1:加入发送的handler
        pipeline.addLast(new MyMessageSendHandler());

    }
}
