package com.dragonsoft.netty.httpserver;

import com.dragonsoft.netty.httpserver.http.Request;
import com.dragonsoft.netty.httpserver.http.Response;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import static java.lang.System.in;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {
//  @Override
//  public void channelActive(final ChannelHandlerContext ctx){ //(1)
////    ChannelHandlerContext.alloc() 获取当前的 ByteBufAllocator并分配一个新的缓冲区
////      ChannelFuture channelFuture = ctx.writeAndFlush(new UnixTime());
//      //todo
////      channelFuture.addListener(ChannelFutureListener.CLOSE);
//  }
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
    cause.printStackTrace();
    ctx.close();
  }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //默默的丢弃接收到的数据
        Request request = new Request((FullHttpRequest) msg);

        System.out.println(request.getURI());

        Response response = new Response(ctx);

        DispatherServlet dispatherServlet = new DispatherServlet();
        dispatherServlet.doDispatcher(request,response);

//        String[] strings = {request.getMethod(), request.getURI(), request.getParameters().toString()};
//        response.write(strings);

//        ctx.writeAndFlush(msg);
    }
}