package com.ppf.netty.sample.heartbeat.server;

import com.ppf.netty.sample.heartbeat.server.handler.HeartbeatHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class HeartbeatServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    //这个是打印日志
                    .handler(new LoggingHandler(LogLevel.INFO))

                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //加入一个空闲检测机制
                            // * Triggers an {@link IdleStateEvent} when a {@link Channel} has not performed
                            // * read, write, or both operation for a while.
                            //当一段时间没有发生读·写·读写，就会触发这一空闲处理器。
                            //
                            //以下是参数
                            //long readerIdleTime：多久没有读
                            //long writerIdleTime：多久没有写
                            //long allIdleTime：多久没有读写
                            //TimeUnit unit：时间单位
                            //
                            //所以，我们会加入心跳机制。不要触发上面的handler。
                            pipeline.addLast(new IdleStateHandler(8,8,8, TimeUnit.SECONDS));
                            //放在idle后面，用来处理idle产生的空闲事件.
                            pipeline.addLast(new HeartbeatHandler());
                        }
                    });

            ChannelFuture cf = sb.bind(8000).sync();
            cf.channel().closeFuture().sync();

            System.out.println("gogog");
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
