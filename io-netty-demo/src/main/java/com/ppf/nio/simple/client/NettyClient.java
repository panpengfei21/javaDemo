package com.ppf.nio.simple.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //1：创建工作线程组
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            //2：创建启动引导器
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker) //配置工作线程组
                    .channel(NioSocketChannel.class) //配置channel的类型
                    .handler(new ChannelInitializer<SocketChannel>() { // 配置连接进来时怎么做。
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端准备好了...");

            //3：连接服务器
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 6666).sync();
            //4：对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            //5：关闭通道
            worker.shutdownGracefully();
        }
    }
}
