package com.dragonsoft.netty.chat;

import com.dragonsoft.netty.httpserver.ClassMappingLoader;
import com.dragonsoft.netty.httpserver.HttpServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by zhangliang on 2019-09-15.
 * 代码示例
 * https://github.com/Siwash/websocketWithNetty
 */
public class WebsocketServerMain {

    public static void main(String args[]){

        EventLoopGroup bossGroup=new NioEventLoopGroup(); //(1)
        EventLoopGroup workerGroup=new NioEventLoopGroup();

       try{
           final ServerBootstrap serverBootstrap = new ServerBootstrap();
           serverBootstrap.group(bossGroup, workerGroup)
                   .channel(NioServerSocketChannel.class)
                   .childHandler(new ChannelInitializer<SocketChannel>() {
                       protected void initChannel(SocketChannel socketChannel) throws Exception {
                           socketChannel.pipeline().addLast("logging", new LoggingHandler("DEBUG"));
                           socketChannel.pipeline().addLast("http-codec",new HttpServerCodec());//设置解码器
                           socketChannel.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
                           socketChannel.pipeline().addLast("http-chunked",new ChunkedWriteHandler());//用于大数据的分区传输
                           socketChannel.pipeline().addLast("handler",new NoiWebsocketHandler());//自定义的业务handler
                       }
                   })
                   .option(ChannelOption.SO_BACKLOG, 128)
                   .childOption(ChannelOption.SO_KEEPALIVE,true);

           //绑定并开始接收输入的连接
           ChannelFuture f=serverBootstrap.bind(8081).sync(); //(7)
           //在服务器套接字关闭之前保持等待
           //在这个例子中，这不会发生，但你可以优雅的做这件事
           //关闭服务
           ClassMappingLoader.loader();
System.out.println("Server start OK，Port:" + 8081);
           f.channel().closeFuture().sync();
       }catch (Exception e){
           e.printStackTrace();
           workerGroup.shutdownGracefully();
           bossGroup.shutdownGracefully();
       }
    }
}