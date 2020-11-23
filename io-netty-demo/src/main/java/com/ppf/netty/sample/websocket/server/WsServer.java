package com.ppf.netty.sample.websocket.server;

import com.ppf.netty.sample.websocket.server.handler.WsServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WsServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        try{
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .handler(new LoggingHandler())

                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //1:因为是基于http协议的，所以要加http编码和解码器
                            pipeline.addLast(new HttpServerCodec());
                            //2:以块方式写的
                            pipeline.addLast(new ChunkedWriteHandler());
                            //3:当浏览器一次性发送大量数据数据时，浏览器就会把它分成多段请求。
                            //  这就是有时浏览器发送大量数据时，多个http请求的原因。
                            //  服务器，要把多段请求聚合起来
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            //4:把普通的http协议升级成web socket协议,并保持长连接
                            //  它会把数据转成 Text and Binary data frames的形式，传给下一个handler
                            //  浏览器请求的path  ws://localhost:8000/hello  path要对应上才能连接
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            //5:加上自己的数据处理
                            pipeline.addLast(new WsServerHandler());
                        }
                    });
            ChannelFuture cf = sb.bind(8000).sync();
            cf.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
