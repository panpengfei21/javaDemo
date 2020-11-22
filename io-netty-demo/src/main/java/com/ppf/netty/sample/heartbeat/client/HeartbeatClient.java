package com.ppf.netty.sample.heartbeat.client;

import com.ppf.netty.sample.heartbeat.client.handler.HeartbeatClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class HeartbeatClient {
    private String host;
    private Integer port;

    public HeartbeatClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new HeartbeatClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect(host, port).sync();


            System.out.println("======================");
            Scanner scanner = new Scanner(System.in);

            String s = scanner.nextLine();
            while (!"exit".equals(s)){
                future.channel().writeAndFlush(s);
                s = scanner.nextLine();
            }
            future.channel().closeFuture().sync();
        }finally {
            worker.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new HeartbeatClient("localhost",8000).run();
    }
}
