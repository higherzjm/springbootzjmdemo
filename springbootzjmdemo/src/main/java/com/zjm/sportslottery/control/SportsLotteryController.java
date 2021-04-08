package com.zjm.sportslottery.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjm.sportslottery.util.ComparatorList;
import com.zjm.sportslottery.vo.SportsLotteryHistoryPrizeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author zhujianming
 * 体育彩票专题
 */
@RequestMapping("/sportsLottery")
@RestController
@Slf4j
@Api(tags = "体彩")
public class SportsLotteryController {
    @Autowired
    public StringRedisTemplate redisTemplate;


    @PostMapping("/saveSportsLotteryHistoryPrize")
    @ApiOperation(value = "保存历史开奖号码", notes = "保存历史开奖号码")
    public List<SportsLotteryHistoryPrizeVO> saveSportsLotteryHistoryPrize(@RequestBody @Validated SportsLotteryHistoryPrizeVO sportsLotteryHistoryPrizeVO) {
        String sportsLotteryType = "";
        if ("1".equals(sportsLotteryHistoryPrizeVO.getSportsLotteryType())) {
            sportsLotteryType = "lotteryOf31Choose7List";
        }
        String redisValue = redisTemplate.opsForValue().get(sportsLotteryType);
        List<SportsLotteryHistoryPrizeVO> sportsLotteryHistoryPrizeVOList = JSON.parseArray(redisValue, SportsLotteryHistoryPrizeVO.class);
        if (sportsLotteryHistoryPrizeVOList == null) {
            sportsLotteryHistoryPrizeVOList = new ArrayList<>();
        }
        sportsLotteryHistoryPrizeVOList.add(sportsLotteryHistoryPrizeVO);
        redisTemplate.opsForValue().set(sportsLotteryType, sportsLotteryHistoryPrizeVOList.toString());
        return sportsLotteryHistoryPrizeVOList;
    }

    @GetMapping("/querySportsLotteryHistoryPrize/{sportsLotteryType}")
    @ApiOperation(value = "查询历史开奖号码", notes = "查询历史开奖号码")
    public List<SportsLotteryHistoryPrizeVO> querySportsLotteryHistoryPrize(
            @ApiParam(name = "sportsLotteryType", value = "类型 1：双色球31选7, 2:大乐透31选5+12选2", required = true) @PathVariable("sportsLotteryType") String sportsLotteryType) {
        List<SportsLotteryHistoryPrizeVO> sportsLotteryHistoryPrizeVOList = null;
        if ("1".equals(sportsLotteryType)) {
            String redisValue = redisTemplate.opsForValue().get("lotteryOf31Choose7List");
            sportsLotteryHistoryPrizeVOList = JSON.parseArray(redisValue, SportsLotteryHistoryPrizeVO.class);
        }
        return sportsLotteryHistoryPrizeVOList;
    }

    /**
     * @Description: 福建体彩31选7 每周一、三、六,每日开奖
     * http://localhost:8081/sportsLottery/lotteryOf31Choose7
     */
    @GetMapping("/lotteryOf31Choose7")
    @ApiOperation(value = "体彩--31选7", notes = "notes:体彩--31选7")
    public String lotteryOf31Choose7() {
        String redisValue = redisTemplate.opsForValue().get("lotteryOf31Choose7List");
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray;
        if (redisValue == null) {
            jsonArray = new JSONArray();
        } else {
            jsonArray = JSON.parseArray(redisValue);
        }

        Random random = new Random(System.currentTimeMillis());
        List<Integer> dataList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Integer data = random.nextInt(32);
            addValue(data, dataList, 0);
        }
        ComparatorList cl = new ComparatorList();
        dataList.sort(cl);
        jsonObject.put("lotteryOf31Choose7", dataList.toString());
        jsonArray.add(dataList.toString());
        redisTemplate.opsForValue().set("lotteryOf31Choose7List", jsonArray.toString());
        return jsonArray.toString();
    }

    @GetMapping("/deleteLotteryOf31Choose7Cache")
    @ApiOperation(value = "体彩--删除缓存", notes = "notes:体彩--删除缓存")
    public boolean deleteLotteryOf31Choose7Cache() {
        return redisTemplate.delete("lotteryOf31Choose7List");
    }


    public void addValue(Integer value, List<Integer> valueList, int m) {
        if (value != 0 && !valueList.contains(value)) {
            valueList.add(value);
        } else {
            Random random = new Random(System.currentTimeMillis() + m);
            Integer data = random.nextInt(32);
            log.info("data:" + data);
            m++;
            addValue(data, valueList, m);
        }
    }


    /**
     * @Description: 大乐透（前区“35选5”＋后区“12选2”） 每周一、三、六
     * http://localhost:8081/sportsLottery/lotteryOf35Choose7
     */
    @GetMapping("/lotteryOf35Choose7")
    @ApiOperation(value = "体彩--35选7", notes = "notes:体彩--35选7")
    public String lotteryOf35Choose7() {
        List<Integer> dataList = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        List<Integer> dataList5 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Integer data = random.nextInt(36);
            addValue(data, dataList5, 0);
        }
        ComparatorList cl = new ComparatorList();
        dataList5.sort(cl);

        List<Integer> dataList2 = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Integer data = random.nextInt(13);
            addValue(data, dataList2, 0);
        }
        dataList2.sort(cl);

        dataList.addAll(dataList5);
        dataList.addAll(dataList2);
        return dataList.toString();
    }

}
