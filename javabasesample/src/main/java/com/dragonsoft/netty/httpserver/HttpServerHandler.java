package com.dragonsoft.netty.httpserver;

import com.dragonsoft.netty.pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import static java.lang.System.in;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {
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
//        DefaultFullHttpRequest httpRequest = (DefaultFullHttpRequest) msg;
//
//        String uri = httpRequest.uri();
//
//        HttpHeaders entries = httpRequest.trailingHeaders();

        System.out.println(msg);

        FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer("{name:'123'}", CharsetUtil.UTF_8));

        resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");

        // 2.发送
        // 注意必须在使用完之后，close channel
        ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);

//        ctx.writeAndFlush(msg);
    }
}