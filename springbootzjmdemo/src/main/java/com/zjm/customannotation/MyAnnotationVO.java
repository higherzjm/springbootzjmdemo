package com.zjm.customannotation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhujianming
 */
@Builder
@Data
@ApiModel("自定义注解")
@NoArgsConstructor
@AllArgsConstructor
public class MyAnnotationVO implements Serializable {
    @ApiModelProperty(value = "姓名")
    @CheckIsNotEmpty(message = "不可为空")
    private String name;
    @CheckValueLegal(MaxValue =50)
    @ApiModelProperty(value = "年龄")
    @CheckIsNotEmpty(message = "不可为空")
    private Integer age;
}
