package com.ppf.netty.sample.groupChat4.version0.client;

import com.ppf.netty.sample.groupChat4.version0.client.handler.InitializerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

public class GCClient implements Runnable{
    static public final GCClient singleton = new GCClient();
    private SocketChannel sc;

    private GCClient() { }

    public SocketChannel getSc() {
        return sc;
    }

    public void setSc(SocketChannel sc) {
        this.sc = sc;
    }

    public void run() {
        if (sc == null) {
            return;
        }
        System.out.println("等待输入");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        while (!"exit".equals(s)) {
            ByteBuf buf = Unpooled.copiedBuffer(s, CharsetUtil.UTF_8);
            sc.writeAndFlush(buf);
            s = scanner.nextLine();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(worker)
                    .channel(NioSocketChannel.class) //配置channel的类型
                    .handler(new InitializerHandler());

            ChannelFuture cf = bootstrap.connect("127.0.0.1", 8000).sync();

            cf.channel().closeFuture().sync();
        }finally {
            worker.shutdownGracefully();
        }
    }

}
