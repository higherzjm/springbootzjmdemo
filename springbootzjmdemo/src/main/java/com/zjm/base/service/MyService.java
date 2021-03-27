package com.zjm.base.service;

import com.zjm.base.RequestVO;
import com.zjm.base.StudentInfo;

/**
 * @author zhujianming
 */
public interface MyService {
     StudentInfo getStudentInfo1(RequestVO requestVO);
     StudentInfo getStudentInfo2(String name,Integer age);
}
