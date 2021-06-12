package com.zjm.springtransaction.DTO;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 薪资复核-复核操作日志表
 说明：
 针对复核模块的复核动作进行单独的日志记录
 * </p>
 *
 * @author liangchangcheng
 * @since 2020-12-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryPayrollOperateLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "员工编码",example = "OA1343456435840925697")
    private String employeeCode;

    @ApiModelProperty(value = "计薪年", required = true,example ="2021")
    private Integer year;

    @ApiModelProperty(value = "计薪月", required = true,example ="4")
    private Integer month;

    @ApiModelProperty(value = "操作时间-开始",example ="2020-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate operateDateBegin;

    @ApiModelProperty(value = "操作时间-结束",example ="2021-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate operateDateEnd;



    @ApiModelProperty(value = "操作编码(测试事务使用)")
    private String actionNum;





}
