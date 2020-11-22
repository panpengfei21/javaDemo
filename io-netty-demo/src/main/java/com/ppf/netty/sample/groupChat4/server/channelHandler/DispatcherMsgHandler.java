package com.ppf.netty.sample.groupChat4.server.channelHandler;

import com.ppf.netty.sample.groupChat4.server.group.ChatGroup;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;

/**
 * 用于转放的
 */
public class DispatcherMsgHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(buf.toString(CharsetUtil.UTF_8));
        ChatGroup.singleton.sendMessage((SocketChannel) ctx.channel(),(ByteBuf)msg);
    }
}
