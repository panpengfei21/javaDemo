package com.ppf.netty.sample.protocolbuffer.multipart.client;

import com.ppf.MyDataInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道就绪时，可以写了。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int i = new Random().nextInt(2);

        MyDataInfo.Data data;
        System.out.println(i + " <<");
        if (i == 0) {
            data = MyDataInfo.Data.newBuilder()
                    .setType(MyDataInfo.Data.DataType.StudentType)//确定数据类型
                    .setStudent(MyDataInfo.Student.newBuilder().setId(3).setName("学生").build())//放入数据体
                    .build();
        }else {
            data = MyDataInfo.Data.newBuilder()
                    .setType(MyDataInfo.Data.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setName("工厂人").setAge(2))
                    .build();
        }
        ctx.writeAndFlush(data);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器说：" + buf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("有异常");
        cause.printStackTrace();
        ctx.close();
    }
}
