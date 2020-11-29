package com.ppf.netty.sample.pack.simple.client;

import com.ppf.netty.sample.pack.simple.client.handler.ChannelInitializerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class PackClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializerHandler());

            ChannelFuture cf = bootstrap.connect("127.0.0.1", 8000).sync();
            cf.channel().closeFuture().sync();
        }finally {
            worker.shutdownGracefully();
        }
    }
}
