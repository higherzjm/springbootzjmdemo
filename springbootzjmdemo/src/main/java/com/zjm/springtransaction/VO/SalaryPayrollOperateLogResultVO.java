package com.zjm.springtransaction.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author zhujianming
 */
@Data
public class SalaryPayrollOperateLogResultVO {

    @ApiModelProperty(value = "操作日志id")
    private String operateLogId;
    @ApiModelProperty(value = "操作时间：YYYY-MM-dd HH24:mm:ss")
    private String createTime;

    @ApiModelProperty(value = "操作人")
    private String createUser;
    @ApiModelProperty(value = "薪资年")
    private Integer year;
    @ApiModelProperty(value = "薪资月")
    private Integer month;


}
