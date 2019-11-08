package com.dragonsoft.netty.httpserver;

import com.dragonsoft.netty.pojo.TimeClientHandlerPOJO;
import com.dragonsoft.netty.pojo.TimeDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by zhangliang on 2019-09-15.
 */
public class HttpClientMain {

    public static void main(String arg[]){
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeDecoder(),new TimeClientHandlerPOJO());
                        }
                    });
            //启动客户端
            ChannelFuture f=bootstrap.connect("localhost",8883).sync(); //(5)
            System.out.println("Client start OK");

            //在连接关闭之前保持等待
            f.channel().closeFuture().sync();
        }catch (Exception ex){
            workerGroup.shutdownGracefully();
        }
    }
}