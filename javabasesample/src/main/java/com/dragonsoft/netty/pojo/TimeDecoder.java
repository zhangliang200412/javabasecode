package com.dragonsoft.netty.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TimeDecoder extends ByteToMessageDecoder { // (1)
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
//        我们可以修改 TimeDecoder 来产生一个 UnixTime 而非 ByteBuf。
        if (in.readableBytes() < 4) {
            return; // (3)
        }
        out.add(new UnixTime(in.readUnsignedInt())); // (4)
    }
}