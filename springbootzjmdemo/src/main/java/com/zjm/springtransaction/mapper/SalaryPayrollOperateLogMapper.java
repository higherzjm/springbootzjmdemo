package com.zjm.springtransaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjm.springtransaction.DTO.SalaryPayrollOperateLogDTO;
import com.zjm.springtransaction.VO.SalaryPayrollOperateLogResultVO;
import com.zjm.springtransaction.entity.SalaryPayrollOperateLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SalaryPayrollOperateLogMapper extends BaseMapper<SalaryPayrollOperateLog> {

    List<SalaryPayrollOperateLogResultVO> querySalaryPayrollOperateLog(@Param("param") SalaryPayrollOperateLogDTO salaryPayrollOperateLogDTO);
}
