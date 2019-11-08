package com.dragonsoft.netty.pojo;

import com.dragonsoft.netty.server.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by zhangliang on 2019-09-15.
 */
public class TimeServerMainPOJO {

    public static void main(String args[]){
        EventLoopGroup bossGroup=new NioEventLoopGroup(); //(1)
        EventLoopGroup workerGroup=new NioEventLoopGroup();

       try{
           final ServerBootstrap serverBootstrap = new ServerBootstrap();
           serverBootstrap.group(bossGroup, workerGroup)
                   .channel(NioServerSocketChannel.class)
                   .childHandler(new ChannelInitializer<SocketChannel>() {
                       protected void initChannel(SocketChannel socketChannel) throws Exception {
                           socketChannel.pipeline().addLast(new TimeEncoder(),new TimeServerHandlerPOJO());
                       }
                   })
                   .option(ChannelOption.SO_BACKLOG, 128)
                   .childOption(ChannelOption.SO_KEEPALIVE,true);

           //绑定并开始接收输入的连接
           ChannelFuture f=serverBootstrap.bind(8883).sync(); //(7)
           //在服务器套接字关闭之前保持等待
           //在这个例子中，这不会发生，但你可以优雅的做这件事
           //关闭服务
System.out.println("Server start OK");
           f.channel().closeFuture().sync();
       }catch (Exception e){
           e.printStackTrace();
           workerGroup.shutdownGracefully();
           bossGroup.shutdownGracefully();
       }
    }
}