package com.zjm.springtransaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjm.springtransaction.DTO.LogInfoDTO;
import com.zjm.springtransaction.VO.LogInfoResultVO;
import com.zjm.springtransaction.entity.LogInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface LogInfoMapper extends BaseMapper<LogInfo> {

    List<LogInfoResultVO> querySalaryPayrollOperateLog(@Param("param") LogInfoDTO logInfoDTO);
}
