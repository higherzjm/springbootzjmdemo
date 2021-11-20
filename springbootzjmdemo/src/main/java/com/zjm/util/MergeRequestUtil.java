package com.zjm.util;

import com.alibaba.fastjson.JSONObject;
import com.zjm.baseapplication.VO.SalaryRecheckMergeRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author zhujianming
 * 薪资复核请求合并工具
 */

@Configuration
@Slf4j
public class MergeRequestUtil {
    //积攒请求的阻塞队列，如果向一个已经满了的队列中添加元素或者从空队列中移除元素，都将会导致线程阻塞，
    // 线程一直等待到有旧元素被移除或新元素被添加的时候，才能继续执行。符合这种情况的队列，称为阻塞队列。
    public static LinkedBlockingDeque<SalaryRecheckMergeRequestVO> salaryRecheckRequestQueue = new LinkedBlockingDeque<>();
    //积攒请求的非阻塞队列
    //public static ConcurrentLinkedQueue<SalaryRecheckMergeRequestVO> salaryRecheckRequestQueue = new ConcurrentLinkedQueue<>();

    //批量请求
    @PostConstruct //被@PostConstruct注解的方法将在该被创建且该类中所有注入操作完成之后执行
    public void salaryRecheckBatchRequest() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(100);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // 把出queue的请求存储一次
            List<SalaryRecheckMergeRequestVO> salaryRecheckMergeRequestVOList = new ArrayList<>();
            for (int i = 0; i < salaryRecheckRequestQueue.size(); i++) {
                SalaryRecheckMergeRequestVO salaryRecheckMergeRequestVO = salaryRecheckRequestQueue.poll();
                if (Objects.nonNull(salaryRecheckMergeRequestVO)) {
                    salaryRecheckMergeRequestVOList.add(salaryRecheckMergeRequestVO);
                }
            }

            if (!salaryRecheckMergeRequestVOList.isEmpty()) {
                SalaryRecheckMergeRequestVO salaryRecheckMergeRequestVOException = null;
                try {
                    // 通知请求的线程
                    for (SalaryRecheckMergeRequestVO salaryRecheckMergeRequestVO : salaryRecheckMergeRequestVOList) {
                        log.info("请求地址:名称 " + salaryRecheckMergeRequestVO.getRequestPath() + ":" + salaryRecheckMergeRequestVO.getRequestName());
                        if (!StringUtils.isEmpty(salaryRecheckMergeRequestVO.getParamJsonStr())) {
                            JSONObject jsonObject = JSONObject.parseObject(salaryRecheckMergeRequestVO.getParamJsonStr());
                            log.info("参数列表:" + jsonObject);
                        }
                        salaryRecheckMergeRequestVO.getCompletedFuture().complete("请求成功");
                        salaryRecheckMergeRequestVOException = salaryRecheckMergeRequestVO;
                    }
                } catch (Throwable throwable) {
                    // 通知请求的线程-异常
                    salaryRecheckMergeRequestVOException.getCompletedFuture().obtrudeException(throwable);

                }

            }

        }, 0, 30, TimeUnit.MILLISECONDS);
    }
}
