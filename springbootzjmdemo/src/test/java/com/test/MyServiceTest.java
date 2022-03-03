package com.test;

import com.zjm.baseapplication.VO.StudentInfo;
import com.zjm.baseapplication.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNull;

/**
 * @author zhujianming
 * @description
 * @date 2022/2/28 10:57
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class MyServiceTest {
    @Autowired
    private MyService myService;

    @Test
    public void test(){
       log.info("ret:{}",myService.getStudentInfo2("张三",30));
        assertNull(myService.getStudentInfo2("张三",30));
    }


}
