package com.zjm.springframework.springMainTest;

import com.MySpringBootApplication;
import com.zjm.springframework.springtransaction.VO.StudentsInfoVO;
import com.zjm.springframework.springtransaction.service.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zhujianming
 * @description
 * @date 2022/4/3 17:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySpringBootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class SpringTest {
    @Autowired
    private IStudentService studentService;
    //todo  不知道为什么报错，起不来，后面再分析
    @Test
    public void queryStudents(){
        List<StudentsInfoVO>  voList=studentService.queryStudentList("张三");
        log.info("voList:{}",voList);
    }
}
