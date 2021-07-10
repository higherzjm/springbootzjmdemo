package com.zjm.springaop;

import com.zjm.base.VO.SchoolVO;
import com.zjm.springtransaction.entity.LogInfo;
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
        log.info("��ʼ������");
    }

    @Pointcut("this(com.baomidou.mybatisplus.core.mapper.BaseMapper)")
    public void aspectMapperPointCut()
    {
    }
    @Before("aspectMapperPointCut() && execution(public * *.insert(..)) && args(baseModel)")
    public void beforeInsert(BaseEntity baseModel)
    {
        log.info("����1");
    }

    @Pointcut("this(com.zjm.springtransaction.service.ISpringTransactionService)")
    public void aspectMapperPointCut2()
    {
    }
    @Before("aspectMapperPointCut2() && execution(public * *.save*(..)) && args(loginfo,actionNum)")
    public void beforeInsert2(LogInfo loginfo,String actionNum)
    {
        log.info("ISpringTransactionService����");
        loginfo.setId(UUID.randomUUID().toString().replace("-",""));
        loginfo.setCreateTime(LocalDateTime.now());
        loginfo.setUpdateTime(LocalDateTime.now());
        loginfo.setEmployeeName("�����������Ա��");
        if ("1".equals(actionNum)) {
            loginfo.setCreateUser("actionNum-1������");
            loginfo.setUpdateUser("actionNum-1������");
        }else {
            loginfo.setCreateUser("δ֪������");
            loginfo.setUpdateUser("δ֪������");
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
