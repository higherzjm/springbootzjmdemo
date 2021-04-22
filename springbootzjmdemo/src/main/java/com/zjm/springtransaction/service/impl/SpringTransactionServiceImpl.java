package com.zjm.springtransaction.service.impl;

import com.zjm.springtransaction.DTO.SalaryPayrollOperateLogDTO;
import com.zjm.springtransaction.VO.SalaryPayrollOperateLogResultVO;
import com.zjm.springtransaction.mapper.SalaryPayrollOperateLogMapper;
import com.zjm.springtransaction.service.ISpringTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhujianming
 */
@Service
@Slf4j
public class SpringTransactionServiceImpl implements ISpringTransactionService {
    @Resource
    private SalaryPayrollOperateLogMapper salaryPayrollOperateLogMapper;

    @Override
    public List<SalaryPayrollOperateLogResultVO> test1(SalaryPayrollOperateLogDTO salaryPayrollOperateLogDTO) {
        List<SalaryPayrollOperateLogResultVO> salaryPayrollOperateLogResultVOList = salaryPayrollOperateLogMapper.querySalaryPayrollOperateLog(salaryPayrollOperateLogDTO);
        log.info("ret:"+salaryPayrollOperateLogResultVOList);
        return salaryPayrollOperateLogResultVOList;
    }


}
