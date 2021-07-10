package com.zjm.customannotation;

import cn.hutool.extra.spring.SpringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.StringJoiner;

/**
 * @author zhujianming
 */
@RequestMapping("/myAnnotationControl")
@RestController
@Slf4j
@Api(tags = "自定义注解")
public class MyAnnotationControl {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @PostMapping("/test")
    @ApiOperation(value = "测试1")
    public String saveSportsLotteryHistoryPrize(@RequestBody MyAnnotationVO myAnnotationVO) throws NoSuchFieldException {
        StringJoiner result = new StringJoiner(";\r\n");
        Set<ConstraintViolation<MyAnnotationVO>> set = validator.validate(myAnnotationVO, Default.class);
        if (set != null && !set.isEmpty()) {
            for (ConstraintViolation<MyAnnotationVO> cv : set) {
                Field declaredField = myAnnotationVO.getClass().getDeclaredField(cv.getPropertyPath().toString());
                ApiModelProperty annotation = declaredField.getAnnotation(ApiModelProperty.class);
                //拼接错误信息，包含当前出错数据的标题名字+错误信息
                result.add(annotation.value() + cv.getMessage());
            }
        }
        if (StringUtils.isEmpty(result.toString())) {
            return myAnnotationVO.toString();
        } else {
            return result.toString();
        }

    }
}
