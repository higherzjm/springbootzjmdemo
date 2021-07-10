package com.zjm.base.sportslottery.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjm.base.sportslottery.util.ComparatorList;
import com.zjm.base.sportslottery.vo.SportsLotteryHistoryPrizeVO;
import com.zjm.base.sportslottery.vo.SportsLotteryRandomGenerationRuleVO;
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
 * ������Ʊר��
 */
@RequestMapping("/sportsLottery")
@RestController
@Slf4j
@Api(tags = "���")
public class SportsLotteryController {
    @Autowired
    public StringRedisTemplate redisTemplate;


    /**
     * 35 https://www.lottery.gov.cn/kj/kjlb.html?dlt
     * 31  http://chart.fjtc.com.cn/fjchart_js/chart_tcfj/chart.shtml?LotID=3107&ChartID=0&_StatType=1&MinIssue=&MaxIssue=&IssueTop=100&ChartType=0&param=0&tab=0
     */
    @PostMapping("/saveSportsLotteryHistoryPrize")
    @ApiOperation(value = "������ʷ��������", notes = "������ʷ��������")
    public JSONObject saveSportsLotteryHistoryPrize(@RequestBody @Validated List<SportsLotteryHistoryPrizeVO> sportsLotteryHistoryPrizeVOList) throws Exception {
        String sportsLotteryType = "";
        List<SportsLotteryHistoryPrizeVO> sportsLotteryHistoryPrizeStoreDataVOList = null;
        if (sportsLotteryHistoryPrizeVOList == null || sportsLotteryHistoryPrizeVOList.size() == 0) {
            throw new Exception("���ݲ���Ϊ��!");
        }
        SportsLotteryHistoryPrizeVO sportsLotteryHistoryPrizeVO = sportsLotteryHistoryPrizeVOList.get(0);
        if (StringUtils.isEmpty(sportsLotteryHistoryPrizeVO.getPrizeDate()) || !sportsLotteryHistoryPrizeVO.getPrizeDate().contains("-")) {
            throw new Exception("���ڲ���Ϊ�գ�����������'-'�������� 2020-01-01 �� 2020-1-1 !");
        }
        if ("1".equals(sportsLotteryHistoryPrizeVO.getSportsLotteryType())) {
            if (StringUtils.isEmpty(sportsLotteryHistoryPrizeVO.getPrizeNum()) || !sportsLotteryHistoryPrizeVO.getPrizeNum().contains(" ")) {
                throw new Exception("���벻��Ϊ�գ����������֮���ÿո������ǰ�����ո� �� 05 06 17 18 21 24 30 07  �� 5 6 17 18 21 24 30 7 ");
            }
            sportsLotteryType = "lotteryOf31Choose7List";
            String redisValue = redisTemplate.opsForValue().get(sportsLotteryType);
            if (redisValue != null) {
                sportsLotteryHistoryPrizeStoreDataVOList = JSON.parseArray(JSON.parseObject(redisValue).getString(sportsLotteryType), SportsLotteryHistoryPrizeVO.class);
            }
        } else if ("2".equals(sportsLotteryHistoryPrizeVO.getSportsLotteryType())) {
            if (StringUtils.isEmpty(sportsLotteryHistoryPrizeVO.getPrizeNum()) || !sportsLotteryHistoryPrizeVO.getPrizeNum().contains("#") || !sportsLotteryHistoryPrizeVO.getPrizeNum().contains(" ")) {
                throw new Exception("���벻��Ϊ�գ�ǰ�������������' # '���������������֮���ÿո������ǰ�����ո� �� 04 12 14 21 35 # 02 11 �� 4 12 14 21 35 # 2 11 ");
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
    @ApiOperation(value = "��ѯ��ʷ��������", notes = "��ѯ��ʷ��������")
    public Map<String, String> querySportsLotteryHistoryPrize(
            @ApiParam(name = "sportsLotteryType", value = "���� 1��˫ɫ��31ѡ7, 2:����͸31ѡ5+12ѡ2", required = true) @PathVariable("sportsLotteryType") String sportsLotteryType,
            @ApiParam(name = "lastIssues", value = "�����������", required = true) @PathVariable("lastIssues") Integer lastIssues) {
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
            sportsLotteryHistoryPrizeMapVO.put("2050��Ʊ����", "�������31ѡ7 ÿ��һ��������,ÿ�տ���");
            sportsLotteryHistoryPrizeMapVO.put("2040�н�����", "�صȽ�����עͶע�������н�������7����������ȫ����� �ܽ����60%+�ӽ����ʽ�\n" +
                    "һ�Ƚ�����עͶע�������н���������6������������ر������� �ܽ����15%��\n" +
                    "���Ƚ�����עͶע�������н���������6������������� �ܽ����25%��\n" +
                    "���Ƚ�����עͶע�������н���������5������������ر������� �̶�Ϊ500Ԫ��\n" +
                    "�ĵȽ�����עͶע�������н���������5������������� �̶�Ϊ50Ԫ��\n" +
                    "��Ƚ�����עͶע�������н���������4������������ر������� �̶�Ϊ20Ԫ��\n" +
                    "���Ƚ�����עͶע�������н���������4������������� �̶�Ϊ10Ԫ");
            sportsLotteryHistoryPrizeMapVO.put("2030���ʷ���", analysisPrizeRet);
        } else if ("2".equals(sportsLotteryType)) {
            sportsLotteryHistoryPrizeMapVO.put("2050��Ʊ����", "����͸��ǰ����35ѡ5����������12ѡ2���� ÿ��һ��������21:10���� ");
            sportsLotteryHistoryPrizeMapVO.put("2040�н�����", "һ�Ƚ�:5����+2���� ����;\n" +
                    "���Ƚ�:5����+1���� ����;\n" +
                    "���Ƚ�:5���� �� 4����+2���� ������\n" +
                    "�ĵȽ�:4����+1���� �� 3����+2���� 200Ԫ;\n" +
                    "��Ƚ�:4���� �� 3 ����+1���� �� 2����+2����10Ԫ;\n" +
                    "���Ƚ�:3���� �� 2����+1���� �� 1����+2���� �� 2���� 5Ԫ");
            sportsLotteryHistoryPrizeMapVO.put("2030���ʷ���", analysisPrizeRet);
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
        sportsLotteryHistoryPrizeMapSortVO.replace("2030���ʷ���", analysisPrizeRet);

        return sportsLotteryHistoryPrizeMapSortVO;
    }

    private String analysisPrizeNumData(List<String> allPrizeNum, String sportsLotteryType) {
        int allPrizeNumSize = allPrizeNum.size();
        if (allPrizeNumSize == 0) {
            return "������";
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
            String beforeNumAnalyRet = "ǰ�������� 1-10:(" + analysisProportionAndAverage(between_1_10_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ");" +
                    "11-20:(" + analysisProportionAndAverage(between_11_20_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ")" +
                    ";21-30:(" + analysisProportionAndAverage(between_21_30_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ");" +
                    "31-35:(" + analysisProportionAndAverage(between_31_35_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ")";
            String afterNumAnalyRet = "���������� 1-3:(" + analysisProportionAndAverage(after_1_3_size.get(), afterPrizeNumTotalSize, allPrizeNumSize) + ");" +
                    "4-6:(" + analysisProportionAndAverage(after_4_6_size.get(), afterPrizeNumTotalSize, allPrizeNumSize) + ")" +
                    ";7_9:(" + analysisProportionAndAverage(after_7_9_size.get(), afterPrizeNumTotalSize, allPrizeNumSize) + ")" +
                    ";10-12��(" + analysisProportionAndAverage(after_10_12_size.get(), afterPrizeNumTotalSize, allPrizeNumSize) + ")";
            sportsLotteryAnalysisResult = beforeNumAnalyRet + afterNumAnalyRet;
        } else if (sportsLotteryType.equals("1")) {
            sportsLotteryAnalysisResult = "1-10:(" + analysisProportionAndAverage(between_1_10_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ");" +
                    "11-20:(" + analysisProportionAndAverage(between_11_20_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ")" +
                    ";21-30:(" + analysisProportionAndAverage(between_21_30_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ");" +
                    "31:(" + analysisProportionAndAverage(between_31_35_size.get(), beforePrizeNumTotalSize, allPrizeNumSize) + ")";
        }


        return "���" + allPrizeNum.size() + "��; " + sportsLotteryAnalysisResult;
    }

    //������ƽ��ֵ����
    private String analysisProportionAndAverage(int periodDataSize, int datasTotalSize, int prizeNumSize) {
        int scale = 5;
        int roundingMode = BigDecimal.ROUND_UP;
        //�ڼ�ռ�ٷֱ�
        BigDecimal periodProportion = new BigDecimal(periodDataSize).divide(new BigDecimal(datasTotalSize), scale, roundingMode).multiply(new BigDecimal(100));
        //�ڼ�ƽ������
        BigDecimal period0Average = new BigDecimal(periodDataSize).divide(new BigDecimal(prizeNumSize), scale, roundingMode);
        return periodProportion.toString() + "��" + "," + period0Average.toString();
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
    @ApiOperation(value = "���--ɾ������", notes = "notes:���--ɾ������")
    public boolean deleteLotteryOf31Choose7Cache(@ApiParam(name = "sportsLotteryType", value = "���� 1��˫ɫ��31ѡ7, 2:����͸31ѡ5+12ѡ2", required = true) @PathVariable("sportsLotteryType") String sportsLotteryType) {
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
    @ApiOperation(value = "����������", notes = "����������")
    public String sportsLotteryRandomGeneration(@RequestBody @Validated SportsLotteryRandomGenerationRuleVO sportsLotteryRandomGenerationRuleVO) {
        List<String> executeResultList = new ArrayList<>();
        executeRandomGenerationRule(sportsLotteryRandomGenerationRuleVO, executeResultList);
        return executeResultList.toString();
    }

    /**
     * ������ɴ������
     */
    private void executeRandomGenerationRule(SportsLotteryRandomGenerationRuleVO sportsLotteryRandomGenerationRuleVO, List<String> executeResultList) {

        List<Integer> dataList = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        List<Integer> dataListBefore = new ArrayList<>();
        if (sportsLotteryRandomGenerationRuleVO.getSportsLotteryType().equals("2")) {
            /**
             * �������31ѡ7 ÿ��һ��������,ÿ�տ���
             * http://localhost:8081/sportsLottery/lotteryOf31Choose7
             */
            for (int i = 0; i < 5; i++) {
                Integer data = random.nextInt(36);
                addValue(data, dataListBefore, 0);
            }
        } else if (sportsLotteryRandomGenerationRuleVO.getSportsLotteryType().equals("1")) {
            /**
             * ����͸��ǰ����35ѡ5����������12ѡ2���� ÿ��һ��������
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
