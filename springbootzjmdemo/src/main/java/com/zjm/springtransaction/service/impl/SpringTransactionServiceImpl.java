package com.zjm.springtransaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjm.springtransaction.DTO.SalaryPayrollOperateLogDTO;
import com.zjm.springtransaction.VO.SalaryPayrollOperateLogResultVO;
import com.zjm.springtransaction.entity.SalaryPayrollOperateLog;
import com.zjm.springtransaction.mapper.SalaryPayrollOperateLogMapper;
import com.zjm.springtransaction.service.ISpringTransactionService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
        return salaryPayrollOperateLogResultVOList;
    }

    public boolean checkExistSalaryPayrollOperateLogInMonth(String organizationCode, Integer month) {
        LambdaQueryWrapper<SalaryPayrollOperateLog> lambdaQueryWrapper = new LambdaQueryWrapper<SalaryPayrollOperateLog>().eq(SalaryPayrollOperateLog::getMonth, month)
                .eq(SalaryPayrollOperateLog::getOrganizationCode, organizationCode).select(SalaryPayrollOperateLog::getId,SalaryPayrollOperateLog::getYear);
        SalaryPayrollOperateLog salaryPayrollOperateLog = salaryPayrollOperateLogMapper.selectOne(lambdaQueryWrapper);
        return salaryPayrollOperateLog != null;
    }

    /**
     * mysql默认隔离级别为可重复读，同一接口同时请求多次读到的数据会一致，
     * 如下代码加事务请求两次会插入两条，不加事务就不会
     * propagation==Propagation.NOT_SUPPORTED   传播级别不支持事务 或 isolation= Isolation.READ_UNCOMMITTED 隔离级别为读未提交，这样只会拆入一条
     **/
    //@Transactional(propagation=Propagation.NOT_SUPPORTED,isolation= Isolation.READ_UNCOMMITTED)
    @Override
    public void saveSalaryPayrollOperateLogResult(SalaryPayrollOperateLog salaryPayrollOperateLog, String actionNum) {
        log.info("salaryPayrollOperateLog:" + salaryPayrollOperateLog);
        salaryPayrollOperateLog.setId(UUID.randomUUID().toString());
         //判断是否存在
        if (!checkExistSalaryPayrollOperateLogInMonth(salaryPayrollOperateLog.getOrganizationCode(), salaryPayrollOperateLog.getMonth())) {
            log.info("可插入");
            salaryPayrollOperateLogMapper.insert(salaryPayrollOperateLog);
        }else {
            log.info("已存在");
        }
        //测试高并发的时候使用
        if ("1".equals(actionNum)) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
