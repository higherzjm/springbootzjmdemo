package com.zjm.springframework.springtransaction.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zjm.baseapplication.VO.Student;
import com.zjm.springframework.springtransaction.VO.StudentsInfoVO;
import com.zjm.springframework.springtransaction.entity.StudentsInfo;

import java.util.List;

public interface IStudentService {
    void saveStudentsInfo(StudentsInfo student);

    List<StudentsInfoVO> queryStudentList(String name);

    String updateIdentityTransaction(String id,String value);

    void update2(LambdaUpdateWrapper<StudentsInfo> wrapper,String value);

    String updateIdentityUnTransaction(String id,String value);
}
