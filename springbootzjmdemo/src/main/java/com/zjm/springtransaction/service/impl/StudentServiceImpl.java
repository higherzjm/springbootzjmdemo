package com.zjm.springtransaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjm.base.VO.Student;
import com.zjm.base.aop_methodInterceptor.interceptor2.HfiTrace;
import com.zjm.springtransaction.entity.StudentsInfo;
import com.zjm.springtransaction.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {
    @Override
    public String toString() {
        return "学生服务类StudentServiceImpl{" +
                "baseMapper=" + baseMapper +
                '}';
    }

    @Autowired
    private BaseMapper<StudentsInfo> baseMapper;
    @Override
    public void save(Student student) {

    }

    @Override
    @HfiTrace
    public List<StudentsInfo> queryStudentList(String name) {
        LambdaQueryWrapper<StudentsInfo> queryWrapper=new LambdaQueryWrapper<>();
        //todo update_time 查出来因为版本问题会报错
        queryWrapper.select(StudentsInfo::getId,StudentsInfo::getName,
                StudentsInfo::getAge, StudentsInfo::getIdentity,StudentsInfo::getUpdateUser).orderByDesc(StudentsInfo::getAge);
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like(StudentsInfo::getName, name);
        }
        return baseMapper.selectList(queryWrapper);
    }
    @Transactional
    @HfiTrace  //自定义注解拦截
    @Override
    public String updateIdentityTransaction(String id,String value) {
        LambdaUpdateWrapper<StudentsInfo> wrapper=new LambdaUpdateWrapper<StudentsInfo>().set(StudentsInfo::getIdentity,value).eq(StudentsInfo::getId,id);
        baseMapper.update(null,wrapper);
        return "更新成功";
    }
    @Override
    @HfiTrace
    public String updateIdentityUnTransaction(String id,String value) {
        LambdaUpdateWrapper<StudentsInfo> wrapper=new LambdaUpdateWrapper<StudentsInfo>().set(StudentsInfo::getIdentity,value).eq(StudentsInfo::getId,id);
        baseMapper.update(null,wrapper);
        return "更新成功";
    }
}
