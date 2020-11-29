package com.ppf.netty.sample.pack.simple.server.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;

public class InitializeChannelHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new MyBytesDecoderHandler());
        pipeline.addLast(new MyLongHandler());

        pipeline.addLast(new NioEventLoopGroup(),new MyLongHandler());
    }
}
