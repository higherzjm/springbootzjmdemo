package com.zjm.springaop;

import com.zjm.base.VO.SchoolVO;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.time.LocalDateTime;

@Aspect
public class MyAspect {
    @Pointcut("this(com.baomidou.mybatisplus.core.mapper.BaseMapper)")
    public void aspectMapperPointCut()
    {
    }
    @Before("aspectMapperPointCut() && execution(public * *.insert(..)) && args(baseModel)")
    public void beforeInsert(BaseEntity baseModel)
    {
        baseModel.setCreateTime(LocalDateTime.now());
        baseModel.setUpdateTime(LocalDateTime.now());
        baseModel.setCreateUser("");
        baseModel.setUpdateUser("");
    }

    @Pointcut("this(com.zjm.base.service.impl.MyServiceImpl)")
    public void aspectMapperPointCut2()
    {
    }

    @Before("aspectMapperPointCut() && execution(public * *.insert*(..)) && args(schoolVO)")
    public void beforeInsert(SchoolVO schoolVO)
    {
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
