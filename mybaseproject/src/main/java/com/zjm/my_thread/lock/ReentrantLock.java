package com.zjm.my_thread.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Description: ��������
 * �����룬��ζ���߳̿��Խ������Ѿ�ӵ������ͬ������顣
 * @Author: zhujianming
 * @Date: 2021/4/21
 **/
@Slf4j
public class ReentrantLock {
    boolean isLocked = false;
    Thread lockedBy = null;
    int lockedCount = 0;
    @Test
    public void test1() throws  Exception{
        lock();//��������һ��
        lock();//�������ڶ���
    }
    public synchronized void lock() throws InterruptedException {
        Thread thread = Thread.currentThread();
        while (isLocked && lockedBy!= thread) {
            wait();
        }
        log.info("threadName��"+thread.getName());
        isLocked = true;
        lockedCount++;
        lockedBy = thread;
    }

    public synchronized void unlocked() {
        if (Thread.currentThread() == this.lockedBy) {
            lockedCount--;
            if (lockedCount == 0) {
                isLocked = false;
                notify();
            }
        }
    }
}
