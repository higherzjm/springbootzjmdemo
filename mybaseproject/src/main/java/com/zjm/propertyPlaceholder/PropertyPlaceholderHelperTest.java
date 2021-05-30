package com.zjm.propertyPlaceholder;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.Properties;

/**
 * @Description: ����ռλ������
 * @Author: zhujianming
 * @Date: 2021/5/27
 * @param:
 **/
@Slf4j
public class PropertyPlaceholderHelperTest {


    @Test
    public void test1() {
        PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}");

        String text = "name=${name},age=${age}";
        Properties props = new Properties();
        props.setProperty("name", "admin");
        props.setProperty("age", "18");

        String result = helper.replacePlaceholders(text, props);
        log.info("�򵥲���:" + result);

        /**
         * �ݹ�
         */
        text = "message=${message}";
        props.setProperty("message", "hi,${name}");
        result = helper.replacePlaceholders(text, props);
        log.info("�ݹ���Ƕ����:" + result);

    }


    /**
     * ��������
     */
    @Test
    public void test2() {

        PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("$#", "#");

        String text = "name=$#name#,age=$#age#";
        Properties props = new Properties();
        props.setProperty("name", "admin");
        props.setProperty("age", "18");

        String result = helper.replacePlaceholders(text, props);

        log.info("��������:"+result);


    }

}