package com.ppf.netty.sample.groupChat4.version0.server;

import com.ppf.netty.sample.groupChat4.version0.server.channelHandler.InitializerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 群聊的服务器
 */
public class GCServer {
    public static void main(String[] args) throws InterruptedException {

        //1:创建线程组
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            //2:启动引导器
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)

                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new InitializerHandler());

            //3:启动。监听端口
            ChannelFuture cf = sb.bind(8000).sync();

            //4:关闭监听
            cf.channel().closeFuture().sync();
        }finally {
            //5:关闭
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
