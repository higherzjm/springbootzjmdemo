package com.zjm.my_netty.basecommunication;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 数据通信
 */
public class Client {

    public static void main(String[] args) throws Exception {
        
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
        .channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel sc) throws Exception { 
                sc.pipeline().addLast(new ClientHandler());
            }
        });
        
        ChannelFuture cf1 = b.connect("127.0.0.1", 8765).sync();
        //发送消息, Buffer类型. write需要flush才发送, 可用writeFlush代替
        cf1.channel().writeAndFlush(Unpooled.copiedBuffer("客户端发送的消息1".getBytes()));
        cf1.channel().writeAndFlush(Unpooled.copiedBuffer("客户端发送的消息2".getBytes()));
        Thread.sleep(2000);
        cf1.channel().writeAndFlush(Unpooled.copiedBuffer("休息2秒钟，客户端继续发送消息".getBytes()));

        cf1.channel().closeFuture().sync();
        group.shutdownGracefully();
    }
}