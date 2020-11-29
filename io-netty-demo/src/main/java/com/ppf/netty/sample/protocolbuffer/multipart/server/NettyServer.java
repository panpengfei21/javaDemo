package com.ppf.netty.sample.protocolbuffer.multipart.server;

import com.ppf.MyDataInfo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;


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
            //   带child都是配置worker的
            //   不带child的都是配置boss的
            sb.group(boss,worker)//设置主从线程组
                    .channel(NioServerSocketChannel.class)//设置通道的类型
                    .option(ChannelOption.SO_BACKLOG,128) //设置线程队列连接的个数。

                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置连接通道保持连接的状态。
                    //设置handler。如果连接进来了，做什么？
                    //pack 入站双向链表的头一个handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //给pipline加处理器。
                            //数据流要经过一层层的处理器来处理。
                            //就像request,要经过过滤器一样。
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ProtobufDecoder(MyDataInfo.Data.getDefaultInstance()));
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });

            System.out.println("服务器，准备好了....");
            //4：监听端口
            ChannelFuture cf = sb.bind(8000).sync();

            //5：对关闭通道进行监听
            cf.channel()
                    .closeFuture()
                    .sync();
        }finally {
            //6：如果发生异常就关闭
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
