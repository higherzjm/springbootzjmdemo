package com.zjm.sportslottery.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjm.sportslottery.util.ComparatorList;
import com.zjm.sportslottery.vo.SportsLotteryHistoryPrizeVO;
import com.zjm.sportslottery.vo.SportsLotteryRandomGenerationRuleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
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
     * 35 https://www.lottery.gov.cn/kj/kjlb.html?dlt
     * 31  http://chart.fjtc.com.cn/fjchart_js/chart_tcfj/chart.shtml?LotID=3107&ChartID=0&_StatType=1&MinIssue=&MaxIssue=&IssueTop=100&ChartType=0&param=0&tab=0
     */
    @PostMapping("/saveSportsLotteryHistoryPrize")
    @ApiOperation(value = "保存历史开奖号码", notes = "保存历史开奖号码")
    public JSONObject saveSportsLotteryHistoryPrize(@RequestBody @Validated List<SportsLotteryHistoryPrizeVO> sportsLotteryHistoryPrizeVOList) throws Exception {
        String sportsLotteryType = "";
        List<SportsLotteryHistoryPrizeVO> sportsLotteryHistoryPrizeStoreDataVOList = null;
        if (sportsLotteryHistoryPrizeVOList == null || sportsLotteryHistoryPrizeVOList.size() == 0) {
            throw new Exception("数据不能为空!");
        }
        SportsLotteryHistoryPrizeVO sportsLotteryHistoryPrizeVO = sportsLotteryHistoryPrizeVOList.get(0);
        if (StringUtils.isEmpty(sportsLotteryHistoryPrizeVO.getPrizeDate()) || !sportsLotteryHistoryPrizeVO.getPrizeDate().contains("-")) {
            throw new Exception("日期不能为空，且年月日以'-'隔开，如 2020-01-01 或 2020-1-1 !");
        }
        if ("1".equals(sportsLotteryHistoryPrizeVO.getSportsLotteryType())) {
            if (StringUtils.isEmpty(sportsLotteryHistoryPrizeVO.getPrizeNum()) || !sportsLotteryHistoryPrizeVO.getPrizeNum().contains(" ")) {
                throw new Exception("号码不能为空，号码与号码之间用空格隔开，前后不留空格 如 05 06 17 18 21 24 30 07  或 5 6 17 18 21 24 30 7 ");
            }
            sportsLotteryType = "lotteryOf31Choose7List";
            String redisValue = redisTemplate.opsForValue().get(sportsLotteryType);
            if (redisValue != null) {
                sportsLotteryHistoryPrizeStoreDataVOList = JSON.parseArray(JSON.parseObject(redisValue).getString(sportsLotteryType), SportsLotteryHistoryPrizeVO.class);
            }
        } else if ("2".equals(sportsLotteryHistoryPrizeVO.getSportsLotteryType())) {
            if (StringUtils.isEmpty(sportsLotteryHistoryPrizeVO.getPrizeNum()) || !sportsLotteryHistoryPrizeVO.getPrizeNum().contains("#") || !sportsLotteryHistoryPrizeVO.getPrizeNum().contains(" ")) {
                throw new Exception("号码不能为空，前区与后区号码用' # '隔开，号码与号码之间用空格隔开，前后不留空格 如 04 12 14 21 35 # 02 11 或 4 12 14 21 35 # 2 11 ");
            }
            sportsLotteryType = "lotteryOf35Choose7List";
            String redisValue = redisTemplate.opsForValue().get(sportsLotteryType);
            if (redisValue != null) {
                sportsLotteryHistoryPrizeStoreDataVOList = JSON.parseArray(JSON.parseObject(redisValue).getString(sportsLotteryType), SportsLotteryHistoryPrizeVO.class);
            }
        }
        if (sportsLotteryHistoryPrizeStoreDataVOList == null) {
            sportsLotteryHistoryPrizeStoreDataVOList = sportsLotteryHistoryPrizeVOList;
        } else {
            sportsLotteryHistoryPrizeStoreDataVOList.addAll(sportsLotteryHistoryPrizeVOList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(sportsLotteryType, sportsLotteryHistoryPrizeStoreDataVOList);
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

        if ("1".equals(sportsLotteryType)) {
            sportsLotteryHistoryPrizeMapVO.put("2050彩票规则", "福建体彩31选7 每周一、三、六,每日开奖");
            sportsLotteryHistoryPrizeMapVO.put("2040中奖规则", "特等奖：单注投注号码与中奖号码中7个基本号码全部相符 总奖金的60%+加奖池资金；\n" +
                    "一等奖：单注投注号码与中奖号码中任6个基本号码和特别号码相符 总奖金的15%；\n" +
                    "二等奖：单注投注号码与中奖号码中任6个基本号码相符 总奖金的25%；\n" +
                    "三等奖：单注投注号码与中奖号码中任5个基本号码和特别号码相符 固定为500元；\n" +
                    "四等奖：单注投注号码与中奖号码中任5个基本号码相符 固定为50元；\n" +
                    "五等奖：单注投注号码与中奖号码中任4个基本号码和特别号码相符 固定为20元；\n" +
                    "六等奖：单注投注号码与中奖号码中任4个基本号码相符 固定为10元");
            sportsLotteryHistoryPrizeMapVO.put("2030概率分析", analysisPrizeRet);
        } else if ("2".equals(sportsLotteryType)) {
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
            if ((allPrizeNum.size() < lastIssues) && !k.contains("2050") && !k.contains("2040") && !k.contains("2030")) {
                allPrizeNum.add(v);
            }
        });
        analysisPrizeRet = analysisPrizeNumData(allPrizeNum, sportsLotteryType);
        sportsLotteryHistoryPrizeMapSortVO.replace("2030概率分析", analysisPrizeRet);

        return sportsLotteryHistoryPrizeMapSortVO;
    }

    private String analysisPrizeNumData(List<String> allPrizeNum, String sportsLotteryType) {
        int allPrizeNumSize = allPrizeNum.size();
        if (allPrizeNumSize == 0) {
            return "无数据";
        }
        int beforePrizeNumTotalSize = allPrizeNumSize * 5;
        if (sportsLotteryType.equals("1")) {
            beforePrizeNumTotalSize = allPrizeNumSize * 8;
        }
        int afterPrizeNumTotalSize = allPrizeNumSize * 2;

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
            String beforeNum = "";
            if (sportsLotteryType.equals("2")) {
                beforeNum = s.substring(0, s.lastIndexOf(" #"));
            } else {
                beforeNum = s;
            }
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

            if (sportsLotteryType.equals("2")) {
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
            }
        });
        String sportsLotteryAnalysisResult = "";
        if (sportsLotteryType.equals("2")) {
            String beforeNumAnalyRet = "前区分析： 1-10:(" + analysisProportionAndAverage(between_1_10_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ");" +
                    "11-20:(" + analysisProportionAndAverage(between_11_20_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ")" +
                    ";21-30:(" + analysisProportionAndAverage(between_21_30_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ");" +
                    "31-35:(" + analysisProportionAndAverage(between_31_35_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ")";
            String afterNumAnalyRet = "后区分析： 1-3:(" + analysisProportionAndAverage(after_1_3_size.get(), afterPrizeNumTotalSize, allPrizeNumSize) + ");" +
                    "4-6:(" + analysisProportionAndAverage(after_4_6_size.get(), afterPrizeNumTotalSize, allPrizeNumSize) + ")" +
                    ";7_9:(" + analysisProportionAndAverage(after_7_9_size.get(), afterPrizeNumTotalSize, allPrizeNumSize) + ")" +
                    ";10-12：(" + analysisProportionAndAverage(after_10_12_size.get(), afterPrizeNumTotalSize, allPrizeNumSize) + ")";
            sportsLotteryAnalysisResult = beforeNumAnalyRet + afterNumAnalyRet;
        } else if (sportsLotteryType.equals("1")) {
            sportsLotteryAnalysisResult = "1-10:(" + analysisProportionAndAverage(between_1_10_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ");" +
                    "11-20:(" + analysisProportionAndAverage(between_11_20_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ")" +
                    ";21-30:(" + analysisProportionAndAverage(between_21_30_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ");" +
                    "31:(" + analysisProportionAndAverage(between_31_35_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ")";
        }


        return "最近" + allPrizeNum.size() + "期; " + sportsLotteryAnalysisResult;
    }

    //比例和平均值计算
    private String analysisProportionAndAverage(int periodDataSize, int datasTotalSize, int prizeNumSize) {
        int scale = 5;
        int roundingMode = BigDecimal.ROUND_UP;
        //期间占百分比
        BigDecimal periodProportion = new BigDecimal(periodDataSize).divide(new BigDecimal(datasTotalSize), scale, roundingMode).multiply(new BigDecimal(100));
        //期间平均数量
        BigDecimal period0Average = new BigDecimal(periodDataSize).divide(new BigDecimal(prizeNumSize), scale, roundingMode);
        return periodProportion.toString() + "‰" + "," + period0Average.toString();
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


    private void addValue(Integer value, List<Integer> valueList, int m) {
        if (value != 0 && !valueList.contains(value)) {
            valueList.add(value);
        } else {
            Random random = new Random(System.currentTimeMillis() + m);
            Integer data = random.nextInt(32);
            m++;
            addValue(data, valueList, m);
        }
    }

    @PostMapping("/sportsLotteryRandomGeneration")
    @ApiOperation(value = "体彩随机生成", notes = "体彩随机生成")
    public String sportsLotteryRandomGeneration(@RequestBody @Validated SportsLotteryRandomGenerationRuleVO sportsLotteryRandomGenerationRuleVO) {
        List<String> executeResultList = new ArrayList<>();
        executeRandomGenerationRule(sportsLotteryRandomGenerationRuleVO, executeResultList);
        return executeResultList.toString();
    }

    /**
     * 随机生成处理程序
     */
    private void executeRandomGenerationRule(SportsLotteryRandomGenerationRuleVO sportsLotteryRandomGenerationRuleVO, List<String> executeResultList) {

        List<Integer> dataList = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        List<Integer> dataListBefore = new ArrayList<>();
        if (sportsLotteryRandomGenerationRuleVO.getSportsLotteryType().equals("2")) {
            /**
             * 福建体彩31选7 每周一、三、六,每日开奖
             * http://localhost:8081/sportsLottery/lotteryOf31Choose7
             */
            for (int i = 0; i < 5; i++) {
                Integer data = random.nextInt(36);
                addValue(data, dataListBefore, 0);
            }
        } else if (sportsLotteryRandomGenerationRuleVO.getSportsLotteryType().equals("1")) {
            /**
             * 大乐透（前区“35选5”＋后区“12选2”） 每周一、三、六
             * http://localhost:8081/sportsLottery/lotteryOf35Choose7
             */
            for (int i = 0; i < 7; i++) {
                Integer data = random.nextInt(32);
                addValue(data, dataListBefore, 0);
            }
        }
        ComparatorList cl = new ComparatorList();
        dataListBefore.sort(cl);
        dataList.addAll(dataListBefore);
        List<Integer> dataListAfter = new ArrayList<>();

        if (sportsLotteryRandomGenerationRuleVO.getSportsLotteryType().equals("2")) {

            for (int i = 0; i < 2; i++) {
                Integer data = random.nextInt(13);
                addValue(data, dataListAfter, 0);
            }
            dataListAfter.sort(cl);
            dataList.addAll(dataListAfter);
        }
        String executeResult = dataList.toString();
        log.info("executeResult:" + executeResult);
        Integer between_1_10_size = dataListBefore.stream().filter(s -> s >= 1 && s <= 10).collect(Collectors.toList()).size();
        Integer between_11_20_size = dataListBefore.stream().filter(s -> s >= 11 && s <= 20).collect(Collectors.toList()).size();
        Integer between_21_30_size = dataListBefore.stream().filter(s -> s >= 21 && s <= 30).collect(Collectors.toList()).size();
        Integer between_31_35_size = dataListBefore.stream().filter(s -> s >= 31 && s <= 35).collect(Collectors.toList()).size();

        if (sportsLotteryRandomGenerationRuleVO.getBetween_1_10_size() != between_1_10_size ||
                sportsLotteryRandomGenerationRuleVO.getBetween_11_20_size() != between_11_20_size ||
                sportsLotteryRandomGenerationRuleVO.getBetween_21_30_size() != between_21_30_size ||
                sportsLotteryRandomGenerationRuleVO.getBetween_31_35_size() != between_31_35_size) {

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executeRandomGenerationRule(sportsLotteryRandomGenerationRuleVO, executeResultList);

        } else {
            if (sportsLotteryRandomGenerationRuleVO.getSportsLotteryType().equals("2")) {
                Integer after_1_3_size = dataListAfter.stream().filter(s -> s >= 1 && s <= 3).collect(Collectors.toList()).size();
                Integer after_4_6_size = dataListAfter.stream().filter(s -> s >= 4 && s <= 6).collect(Collectors.toList()).size();
                Integer after_7_9_size = dataListAfter.stream().filter(s -> s >= 7 && s <= 9).collect(Collectors.toList()).size();
                Integer after_10_12_size = dataListAfter.stream().filter(s -> s >= 10 && s <= 12).collect(Collectors.toList()).size();
                if (sportsLotteryRandomGenerationRuleVO.getAfter_1_3_size() != after_1_3_size ||
                        sportsLotteryRandomGenerationRuleVO.getAfter_4_6_size() != after_4_6_size ||
                        sportsLotteryRandomGenerationRuleVO.getAfter_7_9_size() != after_7_9_size ||
                        sportsLotteryRandomGenerationRuleVO.getAfter_10_12_size() != after_10_12_size) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    executeRandomGenerationRule(sportsLotteryRandomGenerationRuleVO, executeResultList);
                } else {
                    executeResultList.add(executeResult);
                }

            } else if (sportsLotteryRandomGenerationRuleVO.getSportsLotteryType().equals("1")) {
                executeResultList.add(executeResult);
            }
        }

    }

}
