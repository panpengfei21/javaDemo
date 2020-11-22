package com.ppf.netty.sample.http3.Server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 一个连接进来时，初始化。
 * 注意：每一个新连接进来就会初始化一次。
 * 新连接进来，就会new出handler来处理它的数据。
 * 所以，如果是一个连接进来，不断掉就会用相同的handler处理它的数据。
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("myHttpServerCodec",new HttpServerCodec());
        ch.pipeline().addLast("myHttpHandler",new HttpHandler());
    }
}
