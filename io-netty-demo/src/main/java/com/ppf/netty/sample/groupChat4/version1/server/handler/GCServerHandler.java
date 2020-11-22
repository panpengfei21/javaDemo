package com.ppf.netty.sample.groupChat4.version1.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;


public class GCServerHandler extends SimpleChannelInboundHandler<String> {


    private static ChannelGroup cf =  new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 数据传输进来
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        int port = getPort(ctx);
        cf.stream().forEach(c -> {
            if (c.equals(ctx.channel())) {
                return;
            }

            c.writeAndFlush(port + ":" + msg);
        });
    }

    /**
     * 连接可以用了。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        int port = channel.remoteAddress().getPort();
        ByteBuf buf = Unpooled.copiedBuffer(port + "进来了", CharsetUtil.UTF_8);
        cf.writeAndFlush(buf);
    }

    /**
     * 连接被关闭了
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        int port = getPort(ctx);
        ByteBuf buf = Unpooled.copiedBuffer(port + "离开了", CharsetUtil.UTF_8);
        cf.writeAndFlush(buf);
    }

    /**
     * handler被加入去，新连接进来。
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        cf.add(ctx.channel());
    }

    /**
     * handler被移除，一般
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    }

    private Integer getPort(ChannelHandlerContext ctx) {
        SocketChannel channel = (SocketChannel) ctx.channel();
        return channel.remoteAddress().getPort();
    }

}
