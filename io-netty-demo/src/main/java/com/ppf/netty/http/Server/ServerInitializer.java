package com.ppf.netty.http.Server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 服务器初始化
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("myHttpServerCodec",new HttpServerCodec());
        ch.pipeline().addLast("myHttpHandler",new HttpHandler());
    }
}
