package com.zjm.springframework.springtransaction.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zjm.springframework.springtransaction.DTO.LogInfoDTO;
import com.zjm.springframework.springtransaction.VO.LogInfoResultVO;
import com.zjm.springframework.springtransaction.VO.StudentsInfoVO;
import com.zjm.springframework.springtransaction.entity.LogInfo;
import com.zjm.springframework.springtransaction.service.ISpringTransactionService;
import com.zjm.springframework.springtransaction.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zhujianming
 */
@RequestMapping("/springTransaction")
@RestController
@Slf4j
@Api(tags = "数据库事务")
public class SpringTransactionController {
    @Autowired
    private ISpringTransactionService springTransactionService;
    @Autowired
    private IStudentService studentService;
    @PostMapping("/queryStudents/{name}")
    @ApiOperation(value = "spring事务-查询学生列表", notes = "query student list")
    public List<StudentsInfoVO> queryStudentList(@ApiParam(name = "name", value = "姓名",defaultValue = "张") @PathVariable("name") String name) {
        return studentService.queryStudentList(name);
    }
    @PostMapping("/updateIdentityUnTransaction/{id}/{value}")
    @ApiOperation(value = "spring事务-更新学生身份_不带事务")
    public String updateIdentityUnTransaction(@ApiParam(name = "id", value = "主键",defaultValue = "1") @PathVariable("id") String id, @ApiParam(name = "value", value = "更新值")@PathVariable("value") String value) {
        return studentService.updateIdentityUnTransaction(id,value);
    }
    @PostMapping("/updateIdentityTransaction/{id}/{value}")
    @ApiOperation(value = "spring事务-更新学生身份_带事务")
    public String updateIdentityTransaction(@ApiParam(name = "id", value = "主键",defaultValue = "1") @PathVariable("id") String id, @ApiParam(name = "value", value = "更新值")@PathVariable("value") String value) {
        return studentService.updateIdentityTransaction(id,value);
    }
    @PostMapping("/findLog")
    @ApiOperation(value = "spring事务-查询日志", notes = "查询日志")
    public List<LogInfoResultVO> findLog(@RequestBody @Validated LogInfoDTO logInfoDTO) {
        return springTransactionService.findLog(logInfoDTO);
    }

    @PostMapping("/saveLog")
    @ApiOperation(value = "spring事务-保存日志", notes = "保存日志")
    public String saveLog(@RequestBody @Validated LogInfoDTO logInfoDTO) throws Exception {
        LogInfo logInfo = new LogInfo();
        BeanUtil.copyProperties(logInfoDTO, logInfo);
        IntStream.rangeClosed(1,3).forEach(i->{
            log.info("i:{}",i);
            springTransactionService.saveLog(logInfo, logInfoDTO.getActionNum());
        });
        return "保存成功";
    }

    @GetMapping("/queryDynamicTableInfo/{id}")
    @ApiOperation(value = "spring事务-mybatis动态查询", notes = "mybatis动态查询")
    public LogInfo queryDynamicTableInfo(@ApiParam(name = "id", value = "主键id") @PathVariable("id") String id) {
        return springTransactionService.queryDynamicTableInfo(id, LogInfo.class);
    }
}
