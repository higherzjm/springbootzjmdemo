package com.zjm.redis.requestLimit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 漏斗限流
 */
@RequestMapping("/funnelRateLimit")
@RestController
@Slf4j
@Api(tags = "Redis")
public class FunnelRateLimitController {

    public FunnelRateLimitController() {
        //每2秒流出1个
        BigDecimal leakingRate = new BigDecimal("1").divide(new BigDecimal("2"));
        //漏斗容量5
        FunnelUtil.capacity=5;
        //留出率
        FunnelUtil.leakingRate= leakingRate;
        //默认剩余容量
        FunnelUtil.leftQuota=5;
    }
    @GetMapping("/request")
    @ApiOperation(value = "漏斗算法实现限流")
    public String limitTest() {
      boolean waterStatus= FunnelUtil.watering(1);
      if (waterStatus){
          return "请求成功,漏斗剩余容量:"+FunnelUtil.leftQuota;
      }else {
          return "请求失败,漏斗剩余容量:"+FunnelUtil.leftQuota;
      }
    }
}
