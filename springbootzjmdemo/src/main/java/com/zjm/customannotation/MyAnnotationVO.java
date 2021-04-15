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
    private String name;
    @MyAnnotation(MaxValue =50)
    @ApiModelProperty(value = "年龄")
    private Integer age;
}
