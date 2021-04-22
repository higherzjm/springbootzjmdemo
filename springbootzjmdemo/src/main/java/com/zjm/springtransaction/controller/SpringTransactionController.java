package com.zjm.springtransaction.controller;

import com.zjm.springtransaction.DTO.SalaryPayrollOperateLogDTO;
import com.zjm.springtransaction.VO.SalaryPayrollOperateLogResultVO;
import com.zjm.springtransaction.service.ISpringTransactionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhujianming
 */
@RequestMapping("/springTransaction")
@RestController
@Slf4j
@Api(tags = "spring事务")
public class SpringTransactionController {
    @Autowired
    private ISpringTransactionService springTransactionService;
    @PostMapping("/test1")
    public List<SalaryPayrollOperateLogResultVO> test1(@RequestBody @Validated SalaryPayrollOperateLogDTO salaryPayrollOperateLogDTO) {
        return springTransactionService.test1(salaryPayrollOperateLogDTO);
    }
}
