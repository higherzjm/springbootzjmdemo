package com.zjm.springframework.springtransaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjm.springframework.springtransaction.entity.StudentsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface StudentsInfoMapper extends BaseMapper<StudentsInfo> {

    void updateNameById(@Param("id") String id,@Param("name") String name);
}
