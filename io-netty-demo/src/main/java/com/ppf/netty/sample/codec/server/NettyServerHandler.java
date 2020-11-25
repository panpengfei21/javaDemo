package com.ppf.netty.sample.codec.server;

import com.ppf.netty.sample.codec.proto.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 自定义一个Handler,需要继承netty里的某个HandlerAdapter
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {

    /**
     * 读取pipeline里传输过来的数据
     * @param ctx 上下文件，可以在这里面找出需要的各种数据，channel,pipeline等等
     * @param msg 发送过来的数据。
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
        System.out.println("客户端 " + ctx.channel().remoteAddress() + " 说：id>" + msg.getId() + "  name>" + msg.getName());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("您好，客户端", CharsetUtil.UTF_8));
    }

    /**
     * 发生异常关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常，关闭.");
        ctx.close();
    }
}
