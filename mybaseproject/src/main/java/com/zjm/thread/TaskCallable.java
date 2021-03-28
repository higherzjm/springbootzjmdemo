package com.zjm.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author zhujianming
 */
@Slf4j
public class TaskCallable {
    public static void main(String[] args) {
        TaskCallable taskCallable = new TaskCallable();
        taskCallable.test1();

    }

    public void test1() {
        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(8, 30, 60, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100), new ThreadFactoryBuilder().setNameFormat("salary-calculation-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
        CompletionService<String> completionService = new ExecutorCompletionService<>(threadPoolExecutor);
        MyTaskCallable myTaskCallable = new MyTaskCallable("我是中国人");
        completionService.submit(myTaskCallable);

        try {
            String ret = completionService.take().get();
            log.info(ret);
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
