package com.zjm.baseapplication.service;

import com.zjm.baseapplication.VO.RequestVO;
import com.zjm.baseapplication.VO.StudentInfo;

/**
 * @author zhujianming
 */
public interface MyService {
     StudentInfo getStudentInfo1(RequestVO requestVO);
     StudentInfo getStudentInfo2(String name,Integer age);
}
