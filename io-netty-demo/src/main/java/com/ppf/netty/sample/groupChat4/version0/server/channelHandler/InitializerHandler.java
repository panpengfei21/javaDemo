package com.ppf.netty.sample.groupChat4.version0.server.channelHandler;

import com.ppf.netty.sample.groupChat4.version0.server.group.ChatGroup;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;

public class InitializerHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println(ch.remoteAddress().getPort() + "来了");
        ChatGroup.singleton.addClient(ch);
        ChatGroup.singleton.sendMessage(ch, Unpooled.copiedBuffer("我来了", CharsetUtil.UTF_8));
        ch.pipeline().addLast(new DispatcherMsgHandler());
    }
}
