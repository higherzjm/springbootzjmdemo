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
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

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
    public JSONObject saveSportsLotteryHistoryPrize(@RequestBody @Validated List<SportsLotteryHistoryPrizeVO> sportsLotteryHistoryPrizeVOList) {
        String sportsLotteryType = "";
        List<SportsLotteryHistoryPrizeVO> sportsLotteryHistoryPrizeSotreDataVOList=null;
        if ("1".equals(sportsLotteryHistoryPrizeVOList.get(0).getSportsLotteryType())) {
            sportsLotteryType = "lotteryOf31Choose7List";
            String redisValue = redisTemplate.opsForValue().get("lotteryOf31Choose7List");
            if (redisValue != null) {
                sportsLotteryHistoryPrizeSotreDataVOList = JSON.parseArray(JSON.parseObject(redisValue).getString(sportsLotteryType), SportsLotteryHistoryPrizeVO.class);
            }
        }else if ("2".equals(sportsLotteryHistoryPrizeVOList.get(0).getSportsLotteryType())){
            sportsLotteryType = "lotteryOf35Choose7List";
            String redisValue = redisTemplate.opsForValue().get("lotteryOf31Choose7List");
            if (redisValue != null) {
                sportsLotteryHistoryPrizeSotreDataVOList = JSON.parseArray(JSON.parseObject(redisValue).getString(sportsLotteryType), SportsLotteryHistoryPrizeVO.class);
            }
        }
        if (sportsLotteryHistoryPrizeSotreDataVOList==null){
            sportsLotteryHistoryPrizeSotreDataVOList=sportsLotteryHistoryPrizeVOList;
        }else {
            sportsLotteryHistoryPrizeSotreDataVOList.addAll(sportsLotteryHistoryPrizeVOList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(sportsLotteryType, sportsLotteryHistoryPrizeSotreDataVOList);
        redisTemplate.opsForValue().set(sportsLotteryType, jsonObject.toString());
        return jsonObject;
    }

    @GetMapping("/querySportsLotteryHistoryPrize/{sportsLotteryType}")
    @ApiOperation(value = "查询历史开奖号码", notes = "查询历史开奖号码")
    public Map<String, String> querySportsLotteryHistoryPrize(
            @ApiParam(name = "sportsLotteryType", value = "类型 1：双色球31选7, 2:大乐透31选5+12选2", required = true) @PathVariable("sportsLotteryType") String sportsLotteryType) {
        List<SportsLotteryHistoryPrizeVO> sportsLotteryHistoryPrizeVOList = new ArrayList<>();
        if ("1".equals(sportsLotteryType)) {
            String redisValue = redisTemplate.opsForValue().get("lotteryOf31Choose7List");
            if (redisValue != null) {
                sportsLotteryHistoryPrizeVOList = JSON.parseArray(JSON.parseObject(redisValue).getString("lotteryOf31Choose7List"), SportsLotteryHistoryPrizeVO.class);
            }
        }else if ("2".equals(sportsLotteryType)){
            String redisValue = redisTemplate.opsForValue().get("lotteryOf35Choose7List");
            if (redisValue != null) {
                sportsLotteryHistoryPrizeVOList = JSON.parseArray(JSON.parseObject(redisValue).getString("lotteryOf35Choose7List"), SportsLotteryHistoryPrizeVO.class);
            }
        }
        Map<String, String> sportsLotteryHistoryPrizeMapVO = new HashMap<>();
        sportsLotteryHistoryPrizeVOList.stream().map(s -> {
            sportsLotteryHistoryPrizeMapVO.put(s.getPrizeDate(), formatDatas(s.getPrizeNum()));
            return null;
        }).collect(Collectors.toList());

        //排序
        Map<String, String> sportsLotteryHistoryPrizeMapSortVO = sportsLotteryHistoryPrizeMapVO.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(comparingByKey()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return sportsLotteryHistoryPrizeMapSortVO;
    }
    private String formatDatas(String prizeNum){
       String[] strs=prizeNum.split(" ");
       String newPrizeNum="";
       for (String str:strs){
           if (!str.equals("#")&&str.length()==1){
               str="0"+str+" ";
           }else {
               str=str+" ";
           }
           newPrizeNum=newPrizeNum+str;
       }
        return newPrizeNum.substring(0,newPrizeNum.lastIndexOf(" "));
    }

    /**
     * @Description: 福建体彩31选7 每周一、三、六,每日开奖
     * http://localhost:8081/sportsLottery/lotteryOf31Choose7
     */
    @GetMapping("/lotteryOf31Choose7")
    @ApiOperation(value = "体彩--31选7", notes = "notes:体彩--31选7")
    public String lotteryOf31Choose7() {
        Random random = new Random(System.currentTimeMillis());
        List<Integer> dataList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Integer data = random.nextInt(32);
            addValue(data, dataList, 0);
        }
        ComparatorList cl = new ComparatorList();
        dataList.sort(cl);
        return dataList.toString();
    }

    @GetMapping("/deleteLotteryOf31Choose7Cache/{sportsLotteryType}")
    @ApiOperation(value = "体彩--删除缓存", notes = "notes:体彩--删除缓存")
    public boolean deleteLotteryOf31Choose7Cache(@ApiParam(name = "sportsLotteryType", value = "类型 1：双色球31选7, 2:大乐透31选5+12选2", required = true) @PathVariable("sportsLotteryType") String sportsLotteryType) {
        if ("1".equals(sportsLotteryType)) {
            return redisTemplate.delete("lotteryOf31Choose7List");
        }else if ("2".equals(sportsLotteryType)){
            return redisTemplate.delete("lotteryOf35Choose7List");
        }
        return false;
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
