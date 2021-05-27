package com.zjm.springaop;

import com.zjm.base.VO.SchoolVO;
import com.zjm.springtransaction.entity.SalaryPayrollOperateLog;
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
    @Before("aspectMapperPointCut2() && execution(public * *.save*(..)) && args(salaryPayrollOperateLog,actionNum)")
    public void beforeInsert2(SalaryPayrollOperateLog salaryPayrollOperateLog,String actionNum)
    {
        log.info("ISpringTransactionService切面");
        salaryPayrollOperateLog.setId(UUID.randomUUID().toString());
        salaryPayrollOperateLog.setCreateTime(LocalDateTime.now());
        salaryPayrollOperateLog.setUpdateTime(LocalDateTime.now());
        if ("1".equals(actionNum)) {
            salaryPayrollOperateLog.setCreateUser("actionNum-1创建人");
            salaryPayrollOperateLog.setUpdateUser("actionNum-1更新人");
        }else {
            salaryPayrollOperateLog.setCreateUser("未知创建人");
            salaryPayrollOperateLog.setUpdateUser("未知更新人");
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
