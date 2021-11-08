package com.zjm.springtransaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjm.base.VO.Student;
import com.zjm.base.aop_methodInterceptor.interceptor2.HfiTrace;
import com.zjm.springtransaction.entity.StudentsInfo;
import com.zjm.springtransaction.service.IStudentService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.config.TransactionManagementConfigUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private BaseMapper<StudentsInfo> baseMapper;

    @Override
    public void save(Student student) {

    }

    @Override
    @HfiTrace
    public List<StudentsInfo> queryStudentList(String name) {
        LambdaQueryWrapper<StudentsInfo> queryWrapper = new LambdaQueryWrapper<>();
        //todo update_time 查出来因为版本问题会报错
        queryWrapper.select(StudentsInfo::getId, StudentsInfo::getName,
                StudentsInfo::getAge, StudentsInfo::getIdentity, StudentsInfo::getUpdateUser).orderByDesc(StudentsInfo::getAge);
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like(StudentsInfo::getName, name);
        }
        return baseMapper.selectList(queryWrapper);
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW,isolation= Isolation.READ_UNCOMMITTED)
    @HfiTrace  //自定义注解拦�?
    @Override
    public String updateIdentityTransaction(String id, String value) {
        LambdaUpdateWrapper<StudentsInfo> wrapper = new LambdaUpdateWrapper<StudentsInfo>().set(StudentsInfo::getIdentity, value).eq(StudentsInfo::getId, id);
        baseMapper.update(null, wrapper);
        //update2(wrapper,value);//update2 事务不生效，因为事务拦截器使用代理类进行调用，且与方法和类名称同时匹�?
        //((IStudentService) AopContext.currentProxy()).update2(wrapper,value);//使用注入自己或手动产生代理类事务会生�?

        //new Thread(()-> update2(wrapper,value)).start();//�?启新事物
        MyThread myThread=new MyThread(wrapper,value);
        new Thread(myThread).start();//�?启新事物
        return "更新成功";
    }

    @Override
    @Transactional
    public void update2( LambdaUpdateWrapper<StudentsInfo> wrapper,String value) {
       log.info("do  anything");
        wrapper.set(StudentsInfo::getIdentity, value+"->再更�?2");
        baseMapper.update(null, wrapper);
    }

    @Override
    @HfiTrace
    public String updateIdentityUnTransaction(String id, String value) {
        LambdaUpdateWrapper<StudentsInfo> wrapper = new LambdaUpdateWrapper<StudentsInfo>().set(StudentsInfo::getIdentity, value).eq(StudentsInfo::getId, id);
        baseMapper.update(null, wrapper);
        return "更新成功";
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class MyThread implements Runnable{
        LambdaUpdateWrapper<StudentsInfo> baseMapper;
         String value;

        @Override
        public void run() {
            update2(baseMapper,value);
        }
    }
}
