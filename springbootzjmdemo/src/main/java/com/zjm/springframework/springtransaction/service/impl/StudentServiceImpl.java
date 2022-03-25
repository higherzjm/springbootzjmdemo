package com.zjm.springframework.springtransaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjm.baseapplication.VO.Student;
import com.zjm.springframework.methodInterceptor.interceptor2.HfiTrace;
import com.zjm.springframework.springtransaction.VO.StudentsInfoVO;
import com.zjm.springframework.springtransaction.entity.StudentsInfo;
import com.zjm.springframework.springtransaction.service.IStudentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private BaseMapper<StudentsInfo> baseMapper;

    @Override
    public void saveStudentsInfo(StudentsInfo student) {
        baseMapper.insert(student);
    }

    @Override
    public List<StudentsInfoVO> queryStudentList(String name) {
        LambdaQueryWrapper<StudentsInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(StudentsInfo::getId, StudentsInfo::getName,
                StudentsInfo::getAge,StudentsInfo::getUpdateUser,
                StudentsInfo::getUpdateTime).orderByDesc(StudentsInfo::getAge);
        if (!StringUtils.isEmpty(name)) {
            //queryWrapper.like(StudentsInfo::getName, name);
        }
        List<StudentsInfo> infoList = baseMapper.selectList(queryWrapper);
        List<StudentsInfoVO> infoVOList = new ArrayList<>();
        for (StudentsInfo studentsInfo : infoList) {
            StudentsInfoVO infoVO = new StudentsInfoVO();
            BeanUtils.copyProperties(studentsInfo, infoVO);
            infoVOList.add(infoVO);
        }
        return infoVOList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    @HfiTrace  //自定义注解拦拦截
    @Override
    public String updateIdentityTransaction(String id, String value) {
        LambdaUpdateWrapper<StudentsInfo> wrapper = new LambdaUpdateWrapper<StudentsInfo>().set(StudentsInfo::getName, value).eq(StudentsInfo::getId, id);
        baseMapper.update(null, wrapper);
        //update2(wrapper,value);//复用本次事务代理，新方法不管事务怎么设置，都复用本方法的事务隔离级别和传播机制
        //((IStudentService) AopContext.currentProxy()).update2(wrapper,value);//启用新的事务代理，新方法如未设置新事务就沿用本方法的事务隔离级别和传播机制，新事务就启用新的隔离级别
     /*   MyThread myThread=new MyThread(wrapper,value);
        StudentsInfo studentsInfo=baseMapper.selectById(id);
        if (studentsInfo!=null){
            studentsInfo.setIdentity(value);
            baseMapper.updateById(studentsInfo);
        }
        new Thread(myThread).start();//启用新的事务代理*/
        return "更新成功";
    }

    @Override
    @Transactional
    public void update2(LambdaUpdateWrapper<StudentsInfo> wrapper, String value) {
        log.info("do  anything");
        wrapper.set(StudentsInfo::getName, value + "->再更新2");
        baseMapper.update(null, wrapper);
    }

    @Override
    @HfiTrace
    public String updateIdentityUnTransaction(String id, String value) {
        LambdaUpdateWrapper<StudentsInfo> wrapper = new LambdaUpdateWrapper<StudentsInfo>().set(StudentsInfo::getName, value).eq(StudentsInfo::getId, id);
        baseMapper.update(null, wrapper);
        return "更新成功";
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class MyThread implements Runnable {
        LambdaUpdateWrapper<StudentsInfo> baseMapper;
        String value;

        @Override
        public void run() {
            update2(baseMapper, value);
        }
    }
}
