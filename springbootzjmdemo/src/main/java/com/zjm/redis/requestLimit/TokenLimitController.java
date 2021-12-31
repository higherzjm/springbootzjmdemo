package com.zjm.redis.requestLimit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * 令牌算法限流
 */
@RequestMapping("/tokenLimit")
@RestController
@Slf4j
@Api(tags = "Redis")
public class TokenLimitController {

    public TokenLimitController() {
        TokenUtil.limit=10;
        TokenUtil.period=5;
        TokenUtil.timeunit= TimeUnit.SECONDS;
        TokenUtil.init();
    }
    @GetMapping("/tokenRequest")
    @ApiOperation(value = "令牌算法限流")
    public String limitTest() {
      boolean status= TokenUtil.tryGetToken();
      if (status){
          return "请求成功,令牌剩余数量:"+TokenUtil.blockingQueue.size();
      }else {
          return "请求失败,令牌剩余容量:"+TokenUtil.blockingQueue.size();
      }
    }
}
