package com.zjm.springtransaction.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.zjm.springtransaction.DTO.SalaryPayrollOperateLogDTO;
import com.zjm.springtransaction.VO.SalaryPayrollOperateLogResultVO;
import com.zjm.springtransaction.entity.SalaryPayrollOperateLog;
import com.zjm.springtransaction.service.ISpringTransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    @PostMapping("/findSalaryPayrollOperateLogResult")
    @ApiOperation(value = "查询日志", notes = "查询日志")
    public List<SalaryPayrollOperateLogResultVO> findSalaryPayrollOperateLogResult(@RequestBody @Validated SalaryPayrollOperateLogDTO salaryPayrollOperateLogDTO) {
        return springTransactionService.findSalaryPayrollOperateLogResult(salaryPayrollOperateLogDTO);
    }

    @PostMapping("/saveSalaryPayrollOperateLogResult")
    @ApiOperation(value = "保存日志", notes = "保存日志")
    public String saveSalaryPayrollOperateLogResult(@RequestBody @Validated SalaryPayrollOperateLogDTO salaryPayrollOperateLogDTO) {
         SalaryPayrollOperateLog salaryPayrollOperateLog=new SalaryPayrollOperateLog();
         BeanUtil.copyProperties(salaryPayrollOperateLogDTO,salaryPayrollOperateLog);
         springTransactionService.saveSalaryPayrollOperateLogResult(salaryPayrollOperateLog);
         return "保存成功";
    }
}
