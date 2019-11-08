package com.dragonsoft.netty.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandlerPOJO extends ChannelInboundHandlerAdapter {
  @Override
  public void channelActive(final ChannelHandlerContext ctx){ //(1)
//    ChannelHandlerContext.alloc() 获取当前的 ByteBufAllocator并分配一个新的缓冲区
      ChannelFuture channelFuture = ctx.writeAndFlush(new UnixTime());
      //todo
//      channelFuture.addListener(ChannelFutureListener.CLOSE);
  }
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
    cause.printStackTrace();
    ctx.close();
  }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //默默的丢弃接收到的数据
        ByteBuf in = (ByteBuf) msg;
        System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));
        ctx.writeAndFlush(msg);
    }
}