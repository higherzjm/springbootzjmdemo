package com.zjm.springtransaction.service;

import com.zjm.springtransaction.DTO.SalaryPayrollOperateLogDTO;
import com.zjm.springtransaction.VO.SalaryPayrollOperateLogResultVO;

import java.util.List;

/**
 * @author zhujianming
 */
public interface ISpringTransactionService {
    List<SalaryPayrollOperateLogResultVO> test1(SalaryPayrollOperateLogDTO salaryPayrollOperateLogDTO);
}
