package com.dragonsoft.netty.httpserver.http;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.util.CharsetUtil;
import jdk.nashorn.internal.parser.JSONParser;

/**
 * Created by zhangliang on 2019-11-08.
 */
public class Response {

    private ChannelHandlerContext ctx;

    public Response(ChannelHandlerContext ctx){
        this.ctx = ctx;
    }

    public void write(Object object){
        write("application/json", 200, object);
    }

    public void write(Object object, int status){
        write("application/json", status, object);
    }


    public void write(String contextType, int status,  Object object){

        String s = JSON.toJSONString(object);

        FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.valueOf(status),
                Unpooled.copiedBuffer(s, CharsetUtil.UTF_8));

        resp.headers().set(HttpHeaderNames.CONTENT_TYPE, contextType + "; charset=UTF-8");

        // 2.发送
        // 注意必须在使用完之后，close channel
        ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
    }
}