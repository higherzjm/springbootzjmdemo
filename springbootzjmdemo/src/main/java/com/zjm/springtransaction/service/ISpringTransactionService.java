package com.zjm.springtransaction.service;

import com.zjm.springtransaction.DTO.SalaryPayrollOperateLogDTO;
import com.zjm.springtransaction.VO.SalaryPayrollOperateLogResultVO;
import com.zjm.springtransaction.entity.SalaryPayrollOperateLog;

import java.util.List;

/**
 * @author zhujianming
 */
public interface ISpringTransactionService {
    List<SalaryPayrollOperateLogResultVO> findSalaryPayrollOperateLogResult(SalaryPayrollOperateLogDTO salaryPayrollOperateLogDTO);
    void  saveSalaryPayrollOperateLogResult(SalaryPayrollOperateLog salaryPayrollOperateLog,String actionNum);
}
