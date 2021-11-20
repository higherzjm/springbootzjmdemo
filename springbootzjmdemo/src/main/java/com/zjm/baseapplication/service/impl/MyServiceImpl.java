package com.zjm.baseapplication.service.impl;

import com.zjm.baseapplication.VO.RequestVO;
import com.zjm.baseapplication.VO.StudentInfo;
import com.zjm.baseapplication.service.MyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author zhujianming
 */
@Service
public class MyServiceImpl implements MyService {
    @Override
    public StudentInfo getStudentInfo1(RequestVO requestVO) {
        StudentInfo studentInfo=new StudentInfo();
        BeanUtils.copyProperties(requestVO,studentInfo);
        return studentInfo;
    }

    @Override
    public StudentInfo getStudentInfo2(String name, Integer age) {
        return  StudentInfo.builder().name(name).age(age).build();
    }
}
