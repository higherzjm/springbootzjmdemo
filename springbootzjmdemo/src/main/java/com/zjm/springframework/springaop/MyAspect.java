package com.zjm.springframework.springaop;

import com.zjm.springframework.springtransaction.entity.LogInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class MyAspect {
    public MyAspect() {
        log.info("初始化切面");
    }

    @Pointcut("this(com.baomidou.mybatisplus.core.mapper.BaseMapper)")
    public void aspectMapperPointCut()
    {
    }
    @Before("aspectMapperPointCut() && execution(public * *.insert(..)) && args(baseModel)")
    public void beforeInsert(BaseEntity baseModel)
    {
        log.info("切面1");
    }

    @Pointcut("this(com.zjm.springframework.springtransaction.service.ISpringTransactionService)")
    public void aspectMapperPointCut2()
    {
    }
    @Before("aspectMapperPointCut2() && execution(public * *.save*(..)) && args(loginfo,actionNum)")
    public void beforeInsert2(LogInfo loginfo,String actionNum)
    {
        log.info("ISpringTransactionService切面");
        loginfo.setId(UUID.randomUUID().toString().replace("-",""));
        loginfo.setCreateTime(LocalDateTime.now());
        loginfo.setUpdateTime(LocalDateTime.now());
        loginfo.setEmployeeName("切面插入的随机员工");
        if ("1".equals(actionNum)) {
            loginfo.setCreateUser("actionNum-1创建人");
            loginfo.setUpdateUser("actionNum-1更新人");
        }else {
            loginfo.setCreateUser("未知创建人");
            loginfo.setUpdateUser("未知更新人");
        }
    }






 /*   @Before("aspectMapperPointCut() && execution(public * *.updateById(..)) && args(baseModel)")
    public void beforeUpdateById(BaseEntity baseModel)
    {
        updataEntity(baseModel);
    }

    @Before(value="aspectMapperPointCut() && execution(public * *.update(..)) && args(baseModel, wrapper)", argNames="baseModel,wrapper")
    public void beforeUpdate(BaseEntity baseModel, Wrapper wrapper) {
        updataEntity(baseModel);
    }

    private void updataEntity(BaseEntity baseModel) {
        baseModel.setUpdateTime(LocalDateTime.now());
    }*/
}
