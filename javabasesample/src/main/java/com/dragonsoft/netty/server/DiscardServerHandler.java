package com.dragonsoft.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by zhangliang on 2019-09-15.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        //默默的丢弃接收到的数据
        ByteBuf in = (ByteBuf) msg;
        System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));
        ctx.writeAndFlush(msg);
//        ByteBuf in = (ByteBuf) msg;
////        System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));
//        try{
//            while(in.isReadable()){ //(1)
//                    System.out.print((char) in.readByte());
//                System.out.flush();
//            }
//        } finally{
//            ReferenceCountUtil.release(msg); //(2)
//        }
//        ((ByteBuf) msg).release(); //(3)
    }
}