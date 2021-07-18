package com.zjm.util;

import cn.hutool.core.util.ReflectUtil;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @description: 金额处理类
 */
public class BigDecimalUtil {

    /**
     * 金额舍入
     *
     * @param num
     * @param scale
     * @param roundingMode
     * @return
     */
    public static BigDecimal round(BigDecimal num, Integer scale, RoundingMode roundingMode) {
        return num == null ? null : num.setScale(scale, roundingMode);
    }

    /**
     * 对象金额舍入
     *
     * @param obj
     * @param scale
     * @param roundingMode
     * @return
     */
    public static void objAmtRound(Object obj, Integer scale, RoundingMode roundingMode) {
        if (scale == null || roundingMode == null) {
            return;
        }
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == BigDecimal.class) {
                BigDecimal value = (BigDecimal) ReflectUtil.getFieldValue(obj, field);
                if (value != null) {
                    ReflectUtil.setFieldValue(obj, field, value.setScale(scale, roundingMode));
                }
            }
        }
    }

    /**
     * 金额舍入并转化成字符串
     *
     * @param num
     * @param scale
     * @param roundingMode
     * @return
     */
    public static String roundAndToString(BigDecimal num, Integer scale, RoundingMode roundingMode) {
        BigDecimal round = BigDecimalUtil.round(num, scale, roundingMode);
        return BigDecimalUtil.toString(round);
    }

    /**
     * 除
     *
     * @param num1
     * @param num2
     * @return
     */
    public static BigDecimal div(BigDecimal num1, BigDecimal num2) {
        Assert.notNull(num1, "除数不能为空");
        Assert.notNull(num2, "除数不能为空");
        Assert.isTrue(!BigDecimal.ZERO.equals(num2), "除数不能为0");
        return null;
    }

    /**
     * N个BigDecimal相加，忽略为null的项
     *
     * @param bigDecimals
     * @return
     */
    public static BigDecimal safePlus(BigDecimal... bigDecimals) {
        BigDecimal retDecimal = BigDecimal.ZERO;
        if (bigDecimals == null) {
            return retDecimal;
        }
        for (BigDecimal i : bigDecimals) {
            if (i == null) {
                continue;
            }
            retDecimal = retDecimal.add(i);
        }
        return retDecimal;
    }

    /**
     * 第一个数减去多个数，为空的当0处理
     *
     * @param bigDecimals
     * @return
     */
    public static BigDecimal safeSub(BigDecimal... bigDecimals) {
        if (bigDecimals == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal retDecimal = bigDecimals[0];
        for (int i = 1; i < bigDecimals.length; i++) {
            retDecimal = retDecimal.subtract(bigDecimals[i] == null ? BigDecimal.ZERO : bigDecimals[i]);
        }
        return retDecimal;
    }

    /**
     * 减，null作0处理
     *
     * @param num1
     * @param num2
     * @return
     */
    public static BigDecimal subtract(BigDecimal num1, BigDecimal num2) {
        num1 = num1 == null ? BigDecimal.ZERO : num1;
        num2 = num2 == null ? BigDecimal.ZERO : num2;
        return num1.subtract(num2);
    }

    /**
     * 金额转字符串
     *
     * @param obj
     * @return
     */
    public static String toString(BigDecimal obj) {
        return obj == null ? null : obj.toPlainString();
    }

    /**
     * 字符串转金额，字符串空返回0
     *
     * @param obj
     * @return
     */
    public static BigDecimal toBigDecimal(String obj) {
        return StringUtils.isEmpty(obj) ? BigDecimal.ZERO : new BigDecimal(obj);
    }

    /**
     * 字符串转金额，字符串空返回空
     *
     * @param obj
     * @return
     */
    public static BigDecimal toNullableBigDecimal(String obj) {
        return StringUtils.isEmpty(obj) ? null : new BigDecimal(obj);
    }

    /**
     * 计算多个数的和,为空取0
     *
     * @param bigDecimals
     * @return
     */
    public static BigDecimal add(BigDecimal... bigDecimals) {
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal bigDecimal : bigDecimals) {
            if (bigDecimal == null) {
                continue;
            }
            total = total.add(bigDecimal);
        }
        return total;
    }

    /**
     * 比较值是否相等
     *
     * @param decimal
     * @param decimal2
     */
    public static Boolean equals(BigDecimal decimal, BigDecimal decimal2) {
        if (decimal != null && decimal2 != null) {
            return decimal.compareTo(decimal2) == 0;
        }
        if (decimal == null && decimal2 == null) {
            return true;
        }
        return false;
    }



}
