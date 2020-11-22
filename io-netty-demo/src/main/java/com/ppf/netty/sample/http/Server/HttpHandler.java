package com.ppf.netty.sample.http.Server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 入栈的handler
 */
public class HttpHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (!(msg instanceof HttpRequest)) {
            return;
        }
        HttpRequest request = (HttpRequest)msg;
        if ((request.uri().equals("/favicon.ico"))){
            return;
        }

        System.out.println(request.uri());
        System.out.println("客房端地址：" + ctx.channel().remoteAddress());

        ByteBuf buf = Unpooled.copiedBuffer("hello,您好.", CharsetUtil.UTF_8);

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        response.headers().add(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
        response.headers().add(HttpHeaderNames.CONTENT_LENGTH,buf.readableBytes());

        ctx.writeAndFlush(response);
    }
}
