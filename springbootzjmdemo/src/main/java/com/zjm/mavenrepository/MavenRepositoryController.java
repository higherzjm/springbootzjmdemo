package com.zjm.mavenrepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhujianming
 */
@RequestMapping("/mavenrepository")
@RestController
@Slf4j
@Api(tags = "maven私服仓库")
public class MavenRepositoryController {

    @PostMapping("/test1/{name}")
    @ApiOperation(value = "name", notes = "name")
    public String test1(@ApiParam(name = "name", value = "姓名") @PathVariable("name") String name) {
        return new TestClass().testMthod(name);
    }

}
