package com.ppf.netty.sample.groupChat4.version0.server.group;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 聊天室
 */
public class ChatGroup {
    public static final ChatGroup singleton = new ChatGroup();

    private Set<SocketChannel> set;

    private ChatGroup() {
        this.set = new HashSet<>();
    }

    /**
     * 有人加入聊天室
     * @param sc 有人
     */
    public void addClient(SocketChannel sc) {
        set.add(sc);
    }

    /**
     * 有人退出聊天室
     * @param sc 有人
     */
    public void removeClient(SocketChannel sc) {
        set.remove(sc);
    }

    /**
     * 聊天室里人数的数量
     */
    public Integer numbers() {
        return set.size();
    }

    /**
     * 发送消息
     * @param sc 谁发的？
     * @param buf 消息内容
     */
    public void sendMessage(SocketChannel sc, ByteBuf buf) {
        set.stream().forEach(c -> {
            if (!c.equals(sc)) {
                int port = sc.remoteAddress().getPort();
                String msg = port + "说：" + buf.toString(CharsetUtil.UTF_8);
                c.writeAndFlush(Unpooled.copiedBuffer(msg,CharsetUtil.UTF_8));
            }
        });
    }
}
