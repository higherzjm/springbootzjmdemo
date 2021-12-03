package com.zjm.my_netty;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 继承TimerTask实现定时任务
 */
@Slf4j
public class NettyTask {


    public static void main(String[] args) {
        NettyTask task = new NettyTask();
        task.test1();
    }

    static Timer timer = new HashedWheelTimer(50L, TimeUnit.MILLISECONDS, 512);

    @Test
    public void test1() {
        TimerTask task = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                log.info("---业务处理-----");
            }
        };
        //10秒后执行
        Timeout timeout = timer.newTimeout(task, 5, TimeUnit.SECONDS);
        log.info("timeOut:"+timeout);
    }

}