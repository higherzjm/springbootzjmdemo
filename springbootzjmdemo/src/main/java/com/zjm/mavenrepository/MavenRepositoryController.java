package com.zjm.mavenrepository;

import com.zjm.commonutil.SpringBeanUtils;
import com.zjm.springtransaction.service.ISpringTransactionService;
import com.zjm.springtransaction.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhujianming
 */
@RequestMapping("/mavenrepository")
@RestController
@Slf4j
@Api(tags = "maven私服仓库")
@RequiredArgsConstructor
public class MavenRepositoryController {
   private SpringBeanUtils springBeanUtils;
   @PostMapping("/queryBeanInfo")
   @ApiOperation(value = "查询bean信息", notes = "。。。")
   public String queryBeanInfo(){
     return springBeanUtils.getBean(IStudentService.class).toString();
   }



}
