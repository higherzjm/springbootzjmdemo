package com.zjm.springtransaction.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zjm.base.VO.Student;
import com.zjm.springtransaction.entity.StudentsInfo;

import java.util.List;

public interface IStudentService {
    void save(Student student);

    List<StudentsInfo> queryStudentList(String name);

    String updateIdentityTransaction(String id,String value);

    void update2(LambdaUpdateWrapper<StudentsInfo> wrapper,String value);

    String updateIdentityUnTransaction(String id,String value);
}
