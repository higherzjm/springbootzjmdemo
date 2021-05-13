package com.zjm.springtransaction.service.impl;

import com.zjm.springtransaction.DTO.SalaryPayrollOperateLogDTO;
import com.zjm.springtransaction.VO.SalaryPayrollOperateLogResultVO;
import com.zjm.springtransaction.entity.SalaryPayrollOperateLog;
import com.zjm.springtransaction.mapper.SalaryPayrollOperateLogMapper;
import com.zjm.springtransaction.service.ISpringTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author zhujianming
 */
@Service
@Slf4j
public class SpringTransactionServiceImpl implements ISpringTransactionService {
    @Resource
    private SalaryPayrollOperateLogMapper salaryPayrollOperateLogMapper;

    @Override
    public List<SalaryPayrollOperateLogResultVO> findSalaryPayrollOperateLogResult(SalaryPayrollOperateLogDTO salaryPayrollOperateLogDTO) {
        List<SalaryPayrollOperateLogResultVO> salaryPayrollOperateLogResultVOList = salaryPayrollOperateLogMapper.querySalaryPayrollOperateLog(salaryPayrollOperateLogDTO);
        log.info("ret:"+salaryPayrollOperateLogResultVOList);
        return salaryPayrollOperateLogResultVOList;
    }


    @Override
    public void saveSalaryPayrollOperateLogResult(SalaryPayrollOperateLog salaryPayrollOperateLog) {
        log.info("salaryPayrollOperateLog:"+salaryPayrollOperateLog);
        salaryPayrollOperateLog.setId(UUID.randomUUID().toString());
        salaryPayrollOperateLogMapper.insert(salaryPayrollOperateLog);
    }
}
