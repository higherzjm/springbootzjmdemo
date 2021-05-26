package com.zjm.springtransaction.service.impl;

import com.zjm.springtransaction.DTO.SalaryPayrollOperateLogDTO;
import com.zjm.springtransaction.VO.SalaryPayrollOperateLogResultVO;
import com.zjm.springtransaction.entity.SalaryPayrollOperateLog;
import com.zjm.springtransaction.mapper.SalaryPayrollOperateLogMapper;
import com.zjm.springtransaction.service.ISpringTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    //@Transactional(propagation = Propagation.NOT_SUPPORTED) //加了事务可消息避免不可重复读，做到请求过程中读到的数据都same
    @Transactional
    public List<SalaryPayrollOperateLogResultVO> findSalaryPayrollOperateLogResult(SalaryPayrollOperateLogDTO salaryPayrollOperateLogDTO) {
        List<SalaryPayrollOperateLogResultVO> salaryPayrollOperateLogResultVOList = salaryPayrollOperateLogMapper.querySalaryPayrollOperateLog(salaryPayrollOperateLogDTO);
        log.info("query1:" + salaryPayrollOperateLogResultVOList.size());
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//
//        }
        Thread thread = new Thread(new ThreadImplRunnable(salaryPayrollOperateLogMapper));
        thread.start();

        salaryPayrollOperateLogResultVOList = salaryPayrollOperateLogMapper.querySalaryPayrollOperateLog(salaryPayrollOperateLogDTO);
        log.info("query1:" + salaryPayrollOperateLogResultVOList.size());
        return salaryPayrollOperateLogResultVOList;
    }


    @Override
    public void saveSalaryPayrollOperateLogResult(SalaryPayrollOperateLog salaryPayrollOperateLog) {
        log.info("salaryPayrollOperateLog:" + salaryPayrollOperateLog);
        salaryPayrollOperateLog.setId(UUID.randomUUID().toString());
        salaryPayrollOperateLogMapper.insert(salaryPayrollOperateLog);
    }

    class ThreadImplRunnable implements Runnable {

        private SalaryPayrollOperateLogMapper salaryPayrollOperateLogMapper;

        public ThreadImplRunnable(SalaryPayrollOperateLogMapper salaryPayrollOperateLogMapper) {
            this.salaryPayrollOperateLogMapper = salaryPayrollOperateLogMapper;
        }

        /**
         * 重写run方法
         */
        @Override
        public void run() {
            SalaryPayrollOperateLog salaryPayrollOperateLog = SalaryPayrollOperateLog.builder().organizationCode("ORG1343456435840925697")
                    .month(4).year(2021).updateTime(LocalDateTime.now()).build();
            salaryPayrollOperateLog.setId(UUID.randomUUID().toString());
            salaryPayrollOperateLogMapper.insert(salaryPayrollOperateLog);
        }
    }
}
