package com.ppf.netty.sample.protocolbuffer.multipart.server;

import com.ppf.MyDataInfo;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 自定义一个Handler,需要继承netty里的某个HandlerAdapter
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.Data> {

    /**
     * 读取pipeline里传输过来的数据
     * @param ctx 上下文件，可以在这里面找出需要的各种数据，channel,pipeline等等
     * @param msg 发送过来的数据。
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Data msg) throws Exception {
        switch (msg.getType()) {
            case StudentType:{
                MyDataInfo.Student student = msg.getStudent();
                System.out.println("student:id > " + student.getId() + " name>" + student.getName());
            }
                break;
            case WorkerType:{
                MyDataInfo.Worker worker = msg.getWorker();
                System.out.println("worker:name >" + worker.getName() + " aget>" + worker.getAge());
            }
                break;
            case UNRECOGNIZED:{
                System.out.println("未知类型");
            }
                break;
        }
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
