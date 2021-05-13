package com.zjm.springaop;

import com.zjm.base.VO.SchoolVO;
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

    @Pointcut("this(com.zjm.springtransaction.service.ISpringTransactionService)")
    public void aspectMapperPointCut2()
    {
    }
    @Before("aspectMapperPointCut2() && execution(public * *.save*(..)) && args(baseModel)")
    public void beforeInsert2(BaseEntity baseModel)
    {
        log.info("切面2");
        baseModel.setId(UUID.randomUUID().toString());
        baseModel.setCreateTime(LocalDateTime.now());
        baseModel.setUpdateTime(LocalDateTime.now());
        baseModel.setCreateUser("创建人");
        baseModel.setUpdateUser("更新人");
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
