package com.ppf.netty.task.server.normal;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义一个Handler,需要继承netty里的某个HandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取pipeline里传输过来的数据
     * @param ctx 上下文件，可以在这里面找出需要的各种数据，channel,pipeline等等
     * @param msg 发送过来的数据。
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("客户端 " + ctx.channel().remoteAddress() + " 说：" + buf.toString(CharsetUtil.UTF_8));

        hasTaskQueue(ctx,msg);
        //noTaskQueue(ctx, msg);
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

    /**
     * 没有把耗时的事提交到even loop的task queue.会阻塞当前线程.
     */
    private void noTaskQueue(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
        doSomething(ctx);
    }

    /**
     * 把耗时的事异步处理
     * 适用场景：先告诉客户端，我已经收到信息了。
     */
    private void hasTaskQueue(ChannelHandlerContext ctx, Object msg) {
        //任务1：把任务异步处理了
        ctx.channel().eventLoop().execute(() -> {
            try {
                doSomething(ctx);
            } catch (InterruptedException e) {
                System.out.println("有异常," + e.getMessage());
            }
        });

        //任务2：任务2和任务1是在同一个线程，排队处理。
        ctx.channel().eventLoop().execute(() -> {
            try {
                doSomething(ctx);
            } catch (InterruptedException e) {
                System.out.println("有异常," + e.getMessage());
            }
        });
    }

    /**
     * 做一些耗时的事情
     */
    private void doSomething(ChannelHandlerContext ctx) throws InterruptedException {
        Thread.sleep(1000 * 5);
        ctx.writeAndFlush(Unpooled.copiedBuffer("🐱🐱🐱🐱🐱🐱",CharsetUtil.UTF_8));
    }
}
