package com.ppf.netty.task.server.normal;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class NettyServer {
    public static void main(String[] args) throws InterruptedException {

        //1:创建boss,worker两个线程组.
        //boss：只处理连接请求
        //worker：和客户端的业务处理。
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            //2：创建服务器启动引导器
            ServerBootstrap sb = new ServerBootstrap();
            //3：配置启动的各项参数
            sb.group(boss,worker)//设置主从线程组
                    .channel(NioServerSocketChannel.class)//设置通道的类型
                    .option(ChannelOption.SO_BACKLOG,128) //设置线程队列连接的个数。

                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置连接通道保持连接的状态。
                    .childHandler(new ChannelInitializer<SocketChannel>() { //设置handler。如果连接进来了，做什么？
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //给pipline加处理器。
                            //数据流要经过一层层的处理器来处理。
                            //就像request,要经过过滤器一样。
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });

            System.out.println("服务器，准备好了....");
            //4：监听端口
            ChannelFuture cf = sb.bind(6666).sync();

            //5：对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            //6：如果发生异常就关闭
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
