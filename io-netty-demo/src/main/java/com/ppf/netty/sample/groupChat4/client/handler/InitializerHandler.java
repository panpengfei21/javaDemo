package com.ppf.netty.sample.groupChat4.client.handler;

import com.ppf.netty.sample.groupChat4.client.GCClient;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class InitializerHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ReaderHandler());
        GCClient.singleton.setSc(ch);
        new Thread(GCClient.singleton).start();
    }
}
