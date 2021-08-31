package com.zjm.mybyte;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 一、十进制转二进制（整数部分）
 * 方法：用2辗转相除直到结果为1，将余数和最后的1从下向上的组合，就是我们想要的结果。
 * 例如：60
 * 60/2 = 30 余 0
 * 30/2 = 15 余 0
 * 15/2 = 7 余 1
 * 7/2 = 3 余 1
 * 3/2 = 1 余 1
 * 所以十进制数60转为二进制数即为 11100
 * 二、十进制小数转换为二进制小数
 * 方法：乘2取整，顺序排列。
 * 具体做法是：用2乘十进制小数，可以得到积，将积的整数部分取出，再用2乘余下的小数部分，又得到一个积，再将积的整数部分取出，如此进行，直到积中的小数部分为零，或者达到所要求的精度为止。然后把取出的整数部分按顺序排列起来，先取的整数作为二进制小数的高位有效位，后取的整数作为低位有效位。
 * 例如：0.25
 * 0.25*2 = 0.5 ------------整数部分：0
 * 0.5*2 = 1.0 ------------整数部分：1
 * 所以十进制数0.25转为二进制数即为 0.01
 * 所以十进制数 60.25 转为二进制数即为 11100.01
 */
@Slf4j
public class MyByteTest {
    @Test
    public void test1(){
        byte a = (byte)0x80;//0x前缀表示16进制
        byte a1 = 0x10;
        byte a1_1 =(byte) 10;
        byte a1_2 =(byte) 0x10;
        byte a2 = 0x20;
        byte a3 = (byte)0x40;
        byte b = (byte)0xFFFFFFFF;
        byte c = (byte)0x0100;
        log.info("a:"+a+";a2:"+a2+";a3:"+a3);
        log.info("a1:"+a1+";a1_1:"+a1_1+";a1_2:"+a1_2);
        log.info("b:"+b);
        log.info("c:"+c);
    }
}
