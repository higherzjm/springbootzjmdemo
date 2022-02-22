package com.zjm.my_thread.taskcallable;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池升级版
 *
 * @author zhujianming
 */
@Slf4j
public class TaskCallable {
    public static void main(String[] args) {
        TaskCallable taskCallable = new TaskCallable();
        //taskCallable.test1();
        taskCallable.test2();

    }

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(100), new ThreadFactoryBuilder().setNameFormat("test").build(),
            new ThreadPoolExecutor.AbortPolicy());
    private CompletionService<String> completionService = new ExecutorCompletionService<>(threadPoolExecutor);


    /**
     * @description 执行线程并获取返回值
     * @date 2022/2/21 17:30
     */
    public void test1() {
        try {
            MyTaskCallable myTaskCallable = new MyTaskCallable("我是中国人");
            threadPoolExecutor.allowCoreThreadTimeOut(true);//允许核心线程超时
            //submit返回的future对象直接get，会阻塞，直到线程执行结束
            Future<String> f = completionService.submit(myTaskCallable);
            log.info("异步跳过任务执行{},f:{}", System.currentTimeMillis(), f);
            Future<String> future = completionService.take();
            log.info("获取阻塞对象{},f:{}", System.currentTimeMillis(), f);
            String takeRet = future.get();
            log.info("take执行结束{},返回结果{}", System.currentTimeMillis(), takeRet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * @description 执行线程并获取返回值
     * @date 2022/2/21 17:30
     */
    public void test2() {
        try {
            threadPoolExecutor.allowCoreThreadTimeOut(true);//允许核心线程超时
            List<Future<String>> futureList = Lists.newArrayList();
            for (int i = 0; i < 20; i++) {
                MyTaskCallable myTaskCallable = new MyTaskCallable("我是中国人" + i);
                Future<String> future = completionService.submit(myTaskCallable);
                futureList.add(future);
            }
            for (Future<String> future : futureList) {
                log.info("返回值{}", future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

@Slf4j
class MyTaskCallable implements Callable<String> {
    String param;

    public MyTaskCallable(String param) {
        this.param = param;
    }

    @Override
    public String call() throws Exception {
        log.info("开始执行线程:{},{}", System.currentTimeMillis(),param);
        Thread.sleep(5000);
        return System.currentTimeMillis()+","+ param;
    }
}
