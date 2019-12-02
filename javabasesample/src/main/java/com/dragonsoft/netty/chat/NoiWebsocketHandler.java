package com.dragonsoft.netty.chat;

import com.dragonsoft.netty.httpserver.DispatherServlet;
import com.dragonsoft.netty.httpserver.http.Request;
import com.dragonsoft.netty.httpserver.http.Response;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;
import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;

public class NoiWebsocketHandler extends ChannelInboundHandlerAdapter {

    private WebSocketServerHandshaker handshaker;


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端加入连接："+ctx.channel());
        ChannelSupervise.removeChannel(ctx.channel());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
  public void channelActive(final ChannelHandlerContext ctx){ //(1)
        System.out.println("客户端加入连接："+ctx.channel());
        ChannelSupervise.addChannel(ctx.channel());
  }
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
    cause.printStackTrace();
    ctx.close();
  }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("收到消息：" + msg);

        if(msg instanceof FullHttpRequest){
            handlerFullHttpRequest(ctx, msg);
        }else if(msg instanceof WebSocketFrame){
            handlerWebSocketFrame(ctx,msg);
        }

    }

    private void handlerWebSocketFrame(ChannelHandlerContext ctx, Object msg) {
        // 判断是否关闭链路的指令
        WebSocketFrame frame = (WebSocketFrame)msg;
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(
                    new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            System.out.println("本例程仅支持文本消息，不支持二进制消息");
            throw new UnsupportedOperationException(String.format(
                    "%s frame types not supported", frame.getClass().getName()));
        }
        // 返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        System.out.println("服务端收到：" + request);
        TextWebSocketFrame tws = new TextWebSocketFrame(new Date().toString()
                + ctx.channel().id() + "：" + request);
        // 群发
        ChannelSupervise.send2All(tws);
        // 返回【谁发的发给谁】
        // ctx.channel().writeAndFlush(tws);
    }

    private void handlerFullHttpRequest(ChannelHandlerContext ctx, Object msg) {
        //要求Upgrade为websocket，过滤掉get/Post
        FullHttpRequest req  = (FullHttpRequest)msg;
        if (!req.decoderResult().isSuccess()
                || (!"websocket".equals(req.headers().get("Upgrade")))) {
            //若不是websocket方式，则创建BAD_REQUEST的req，返回给客户端
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "ws://localhost:8081/websocket", null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory
                    .sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    /**
     * 拒绝不合法的请求，并返回错误信息
     * */
    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(),
                    CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        // 如果是非Keep-Alive，关闭连接
        if (!isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }
}