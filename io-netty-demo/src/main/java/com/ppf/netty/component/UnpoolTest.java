package com.ppf.netty.component;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UnpoolTest {
    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);
    }
}
