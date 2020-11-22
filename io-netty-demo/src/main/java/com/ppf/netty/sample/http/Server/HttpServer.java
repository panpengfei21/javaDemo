package com.ppf.netty.sample.http.Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * 有些浏览器，对于http请求，8000以下端口的。发不出去。
 * 1：换成8000或以上的端口。
 * 2：直接用post man测试
 */
public class HttpServer {
    public static void main(String[] args) throws InterruptedException {

        //1:创建主从线程组
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            //2:创建启动引导器,并配置
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)

                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ServerInitializer());

            //3:监听端口
            ChannelFuture cf = sb.bind(8000).sync();

            //4:关闭监听
            cf.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
