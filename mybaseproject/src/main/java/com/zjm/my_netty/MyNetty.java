package com.zjm.my_netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.ImmediateEventExecutor;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class MyNetty {

    @Test
    public void test1() throws Exception {
        // 创建BossGroup和WorkerGroup
        // boss处理连接请求
        // worker和客户端进行业务处理
        // 二者都是无限循环
        // 如果不指定数量，默认是cpu的核数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务器端启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 使用链式编程进行设置
            bootstrap.group(bossGroup, workerGroup)  //设置两个线程组
                    .channel(NioServerSocketChannel.class)    //使用NioSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)  //设置线程队列的连接数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)  //
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 为pipline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 这里先暂时设置为null,不涉及本次要演示的功能
                            socketChannel.pipeline().addLast(null);
                        }
                    });
            log.info("Server---------服务端已就绪");
            // 绑定一个端口并同步
            int port = 6888;
            ChannelFuture channelFuture = bootstrap.bind(port).sync();

            ChannelFutureListener futureListener = new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        log.info("Server---------绑定端口isSuccess()=true");
                    } else {
                        log.info("Server---------绑定端口isSuccess()=false");
                    }
                    if (channelFuture.isDone()) {
                        log.info("Server---------绑定端口isDone()=true");
                    } else {
                        log.info("Server---------绑定端口isDone()=false");
                    }
                    log.info("Server---------绑定端口cause()=" + String.valueOf(channelFuture.cause()));
                }
            };

            // 注册监听器
            channelFuture.addListener(futureListener);

            // 监听关闭通道的动作
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Test
    public void test2(){
        FutureListener<String> mainPromiseListener = future -> {
            System.out.println("-----------");
            if (future.isCancelled() ) {

            }
        };
        Promise<String> promise = ImmediateEventExecutor.INSTANCE.newPromise();

        promise.addListener(mainPromiseListener);
    }
    @Test
    public void test3(){

    }
}
