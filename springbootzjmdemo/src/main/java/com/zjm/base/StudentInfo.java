package com.zjm.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhujianming
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "学生信息请求VO")
public class StudentInfo{
    @ApiModelProperty(value = "学生姓名")
    private String name;
    @ApiModelProperty(value = "学生年龄")
    private Integer age;
}