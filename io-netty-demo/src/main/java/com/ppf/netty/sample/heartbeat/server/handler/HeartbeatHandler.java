package com.ppf.netty.sample.heartbeat.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    /**
     * 用于处理被触发的用户事件
     * 一般，在这里处理是否要断开连接的操作。
     * @param ctx 上下文
     * @param evt 事件
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case ALL_IDLE:
                    System.out.println("读写都没有");
                    break;
                case READER_IDLE:
                    System.out.println("读没有");
                    break;
                case WRITER_IDLE:
                    System.out.println("写没有");
                    break;
            }
        }
    }
}
