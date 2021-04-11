package com.zjm.sportslottery.control;

import com.alibaba.fastjson.JSON;
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

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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


    /**
     * @Description: 35 https://www.lottery.gov.cn/kj/kjlb.html?dlt
     * 31  http://chart.fjtc.com.cn/fjchart_js/chart_tcfj/chart.shtml?LotID=3107&ChartID=0&_StatType=1&MinIssue=&MaxIssue=&IssueTop=100&ChartType=0&param=0&tab=0
     */
    @PostMapping("/saveSportsLotteryHistoryPrize")
    @ApiOperation(value = "保存历史开奖号码", notes = "保存历史开奖号码")
    public JSONObject saveSportsLotteryHistoryPrize(@RequestBody @Validated List<SportsLotteryHistoryPrizeVO> sportsLotteryHistoryPrizeVOList) {
        String sportsLotteryType = "";
        List<SportsLotteryHistoryPrizeVO> sportsLotteryHistoryPrizeSotreDataVOList = null;
        if ("1".equals(sportsLotteryHistoryPrizeVOList.get(0).getSportsLotteryType())) {
            sportsLotteryType = "lotteryOf31Choose7List";
            String redisValue = redisTemplate.opsForValue().get(sportsLotteryType);
            if (redisValue != null) {
                sportsLotteryHistoryPrizeSotreDataVOList = JSON.parseArray(JSON.parseObject(redisValue).getString(sportsLotteryType), SportsLotteryHistoryPrizeVO.class);
            }
        } else if ("2".equals(sportsLotteryHistoryPrizeVOList.get(0).getSportsLotteryType())) {
            sportsLotteryType = "lotteryOf35Choose7List";
            String redisValue = redisTemplate.opsForValue().get(sportsLotteryType);
            if (redisValue != null) {
                sportsLotteryHistoryPrizeSotreDataVOList = JSON.parseArray(JSON.parseObject(redisValue).getString(sportsLotteryType), SportsLotteryHistoryPrizeVO.class);
            }
        }
        if (sportsLotteryHistoryPrizeSotreDataVOList == null) {
            sportsLotteryHistoryPrizeSotreDataVOList = sportsLotteryHistoryPrizeVOList;
        } else {
            sportsLotteryHistoryPrizeSotreDataVOList.addAll(sportsLotteryHistoryPrizeVOList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(sportsLotteryType, sportsLotteryHistoryPrizeSotreDataVOList);
        redisTemplate.opsForValue().set(sportsLotteryType, jsonObject.toString());
        return jsonObject;
    }

    @GetMapping("/querySportsLotteryHistoryPrize/{sportsLotteryType}/{lastIssues}")
    @ApiOperation(value = "查询历史开奖号码", notes = "查询历史开奖号码")
    public Map<String, String> querySportsLotteryHistoryPrize(
            @ApiParam(name = "sportsLotteryType", value = "类型 1：双色球31选7, 2:大乐透31选5+12选2", required = true) @PathVariable("sportsLotteryType") String sportsLotteryType,
            @ApiParam(name = "lastIssues", value = "分析最近期数", required = true) @PathVariable("lastIssues") Integer lastIssues) {
        List<SportsLotteryHistoryPrizeVO> sportsLotteryHistoryPrizeVOList = new ArrayList<>();
        if ("1".equals(sportsLotteryType)) {
            String redisValue = redisTemplate.opsForValue().get("lotteryOf31Choose7List");
            if (redisValue != null) {
                sportsLotteryHistoryPrizeVOList = JSON.parseArray(JSON.parseObject(redisValue).getString("lotteryOf31Choose7List"), SportsLotteryHistoryPrizeVO.class);
            }
        } else if ("2".equals(sportsLotteryType)) {
            String redisValue = redisTemplate.opsForValue().get("lotteryOf35Choose7List");
            if (redisValue != null) {
                sportsLotteryHistoryPrizeVOList = JSON.parseArray(JSON.parseObject(redisValue).getString("lotteryOf35Choose7List"), SportsLotteryHistoryPrizeVO.class);
            }
        }
        Map<String, String> sportsLotteryHistoryPrizeMapVO = new HashMap<>();
        sportsLotteryHistoryPrizeVOList.stream().map(s -> {
            String formatPrizeNum = formatDatas(s.getPrizeNum(), " ");
            sportsLotteryHistoryPrizeMapVO.put(formatDatas(s.getPrizeDate(), "-"), formatPrizeNum);
            return null;
        }).collect(Collectors.toList());

        String analysisPrizeRet = "";
        //排序
        if ("2".equals(sportsLotteryType)) {
            sportsLotteryHistoryPrizeMapVO.put("2050彩票规则", "大乐透（前区“35选5”＋后区“12选2”） 每周一、三、六21:10开奖 ");
            sportsLotteryHistoryPrizeMapVO.put("2040中奖规则", "一等奖:5红球+2蓝球 浮动;\n" +
                    "二等奖:5红球+1蓝球 浮动;\n" +
                    "三等奖:5红球 或 4红球+2蓝球 浮动；\n" +
                    "四等奖:4红球+1蓝球 或 3红球+2蓝球 200元;\n" +
                    "五等奖:4红球 或 3 红球+1蓝球 或 2红球+2蓝球10元;\n" +
                    "六等奖:3红球 或 2红球+1蓝球 或 1红球+2蓝球 或 2蓝球 5元");
            sportsLotteryHistoryPrizeMapVO.put("2030概率分析", analysisPrizeRet);
        }

        Map<String, String> sportsLotteryHistoryPrizeMapSortVO = sportsLotteryHistoryPrizeMapVO.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(comparingByKey()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        List<String> allPrizeNum = new ArrayList<>();
        sportsLotteryHistoryPrizeMapSortVO.forEach((k, v) -> {
            if ((allPrizeNum.size() < lastIssues) &&!k.contains("2050")&&!k.contains("2040")&&!k.contains("2030")) {
                allPrizeNum.add(v);
            }
        });
        analysisPrizeRet = analysisPrizeNumData(allPrizeNum);
        sportsLotteryHistoryPrizeMapSortVO.replace("2030概率分析",analysisPrizeRet);

        return sportsLotteryHistoryPrizeMapSortVO;
    }

    private String analysisPrizeNumData(List<String> allPrizeNum) {
        if (allPrizeNum.size() == 0) {
            return "无数据";
        }
        int beforeNumDatasTotalSize = allPrizeNum.size() * 5;
        int afterNumDatasTotalSize = allPrizeNum.size() * 2;
        List<String> between_1_10 = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10");
        List<String> between_11_20 = Arrays.asList("11", "12", "13", "14", "15", "16", "17", "18", "19", "20");
        List<String> between_21_30 = Arrays.asList("21", "22", "23", "24", "25", "26", "27", "28", "29", "30");
        List<String> between_31_35 = Arrays.asList("31", "32", "33", "34", "35");

        List<String> after_1_3 = Arrays.asList("01", "02", "03");
        List<String> after_4_6 = Arrays.asList("04", "05", "06");
        List<String> after_7_9 = Arrays.asList("07", "08", "09");
        List<String> after_10_12 = Arrays.asList("10", "11", "12");

        AtomicInteger between_1_10_size = new AtomicInteger();
        AtomicInteger between_11_20_size = new AtomicInteger();
        AtomicInteger between_21_30_size = new AtomicInteger();
        AtomicInteger between_31_35_size = new AtomicInteger();

        AtomicInteger after_1_3_size = new AtomicInteger();
        AtomicInteger after_4_6_size = new AtomicInteger();
        AtomicInteger after_7_9_size = new AtomicInteger();
        AtomicInteger after_10_12_size = new AtomicInteger();
        allPrizeNum.forEach(s -> {
            String beforeNum = s.substring(0, s.lastIndexOf(" #"));
            Arrays.stream(beforeNum.split(" ")).forEach(b -> {
                if (between_1_10.contains(b)) {
                    between_1_10_size.set(between_1_10_size.get() + 1);
                }
                if (between_11_20.contains(b)) {
                    between_11_20_size.set(between_11_20_size.get() + 1);
                }
                if (between_21_30.contains(b)) {
                    between_21_30_size.set(between_21_30_size.get() + 1);
                }
                if (between_31_35.contains(b)) {
                    between_31_35_size.set(between_31_35_size.get() + 1);
                }
            });

            String afterNum = s.substring(s.lastIndexOf("# ") + 1);
            Arrays.stream(afterNum.split(" ")).forEach(b -> {
                if (after_1_3.contains(b)) {
                    after_1_3_size.set(after_1_3_size.get() + 1);
                }
                if (after_4_6.contains(b)) {
                    after_4_6_size.set(after_4_6_size.get() + 1);
                }
                if (after_7_9.contains(b)) {
                    after_7_9_size.set(after_7_9_size.get() + 1);
                }
                if (after_10_12.contains(b)) {
                    after_10_12_size.set(after_10_12_size.get() + 1);
                }
            });
        });
        int scale=5;
        int roundingMode=BigDecimal.ROUND_UP;
        BigDecimal between_1_10_proportion = new BigDecimal(between_1_10_size.get()).divide(new BigDecimal(beforeNumDatasTotalSize), scale, roundingMode);
        BigDecimal between_11_20_proportion = new BigDecimal(between_11_20_size.get()).divide(new BigDecimal(beforeNumDatasTotalSize), scale, roundingMode);
        BigDecimal between_21_30_proportion = new BigDecimal(between_21_30_size.get()).divide(new BigDecimal(beforeNumDatasTotalSize), scale, roundingMode);
        BigDecimal between_31_35_proportion = new BigDecimal(between_31_35_size.get()).divide(new BigDecimal(beforeNumDatasTotalSize), scale, roundingMode);

        BigDecimal after_1_3_proportion = new BigDecimal(after_1_3_size.get()).divide(new BigDecimal(afterNumDatasTotalSize), scale, roundingMode);
        BigDecimal after_4_6_proportion = new BigDecimal(after_4_6_size.get()).divide(new BigDecimal(afterNumDatasTotalSize), scale, roundingMode);
        BigDecimal after_7_9_proportion = new BigDecimal(after_7_9_size.get()).divide(new BigDecimal(afterNumDatasTotalSize), scale, roundingMode);
        BigDecimal after_10_12_proportion = new BigDecimal(after_10_12_size.get()).divide(new BigDecimal(afterNumDatasTotalSize), scale, roundingMode);

        String beforeNumAnalyRet = "前区分析： 1-10%:" + between_1_10_proportion.toString() + ";11-20%:" + between_11_20_proportion.toString() + ";21-30%:"
                + between_21_30_proportion.toString() + ";31-35%:" + between_31_35_proportion.toString();
        String afterNumAnalyRet = " 后区分析： 1-3%:" + after_1_3_proportion.toString() + ";4-6%:" + after_4_6_proportion.toString() + ";7_9%:" + after_7_9_proportion + ";10-12%：" + after_10_12_proportion;


        return "最近" + allPrizeNum.size() + "期; " + beforeNumAnalyRet + afterNumAnalyRet;
    }

    private String formatDatas(String prizeNum, String regex) {
        String[] strs = prizeNum.split(regex);
        String newPrizeNum = "";
        for (String str : strs) {
            if (!str.equals("#") && str.length() == 1) {
                str = "0" + str + regex;
            } else {
                str = str + regex;
            }
            newPrizeNum = newPrizeNum + str;
        }
        return newPrizeNum.substring(0, newPrizeNum.lastIndexOf(regex));
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
        } else if ("2".equals(sportsLotteryType)) {
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
