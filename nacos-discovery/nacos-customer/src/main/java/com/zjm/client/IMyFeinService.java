package com.zjm.client;

import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "nacos-provide", url = "${higher.providerFeign.url}")
public interface IMyFeinService {
    @GetMapping("/myFeign/querySportsLotteryHistoryPrize/{sportsLotteryType}")
    String querySportsLotteryHistoryPrize(@ApiParam(name = "sportsLotteryType", value = "类型 1：双色球31选7, 2:大乐透31选5+12选2", required = true) @PathVariable("sportsLotteryType") String sportsLotteryType);

    @GetMapping("/studentName/{name}")
    String studentName(@PathVariable("name") String name);
}
