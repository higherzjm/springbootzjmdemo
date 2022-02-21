package com.zjm.my_thread.taskcallable;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 线程池升级版
 * @author zhujianming
 */
@Slf4j
public class TaskCallable {
    public static void main(String[] args) {
        TaskCallable taskCallable = new TaskCallable();
        taskCallable.test1();

    }
    /**
     * @description 执行线程并获取返回值
     * @date 2022/2/21 17:30
     */
    public void test1() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 30, 60, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100), new ThreadFactoryBuilder().setNameFormat("salary-calculation-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.allowCoreThreadTimeOut(true);//允许核心线程超时
        CompletionService<String> completionService = new ExecutorCompletionService<>(threadPoolExecutor);
        MyTaskCallable myTaskCallable = new MyTaskCallable("我是中国人");
        try {
            String subRet=completionService.submit(myTaskCallable).get();
            log.info("subRet:"+subRet);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            String takeRet = completionService.take().get();
            log.info("takeRet:"+takeRet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
class MyTaskCallable implements Callable<String> {
    String param;

    public MyTaskCallable(String param) {
        this.param = param;
    }

    @Override
    public String call() throws Exception {
        return "初始化参数:" + param;
    }
}
