package com.zjm.my_netty.unpackingpackage;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 拆包粘包问题
 */
@Slf4j
public class Client {

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
                        sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
                        sc.pipeline().addLast(new StringDecoder());
                        sc.pipeline().addLast(new ClientHandler());
                    }
                });

        ChannelFuture cf = b.connect("127.0.0.1", 8766).sync();
        FutureListener futureListener = new FutureListener() {
            @Override
            public void operationComplete(Future future) throws Exception {
                if (future.isDone()) {
                    log.info("client:isDone is true:"+System.currentTimeMillis());
                }
                if (future.isSuccess()) {
                    log.info("client:isSuccess is true:"+System.currentTimeMillis());
                }
                Object getMsg=future.get();
                log.info("client:getMsg:"+getMsg);
            }

        };
        cf.addListener(futureListener);
        cf.channel().writeAndFlush(Unpooled.wrappedBuffer("客户端发送消息1$_".getBytes()));//执行这一条后监听器就会开启
        cf.channel().writeAndFlush(Unpooled.wrappedBuffer("客户端发送消息2$_".getBytes()));

        cf.channel().closeFuture().sync();
        group.shutdownGracefully();


    }
}