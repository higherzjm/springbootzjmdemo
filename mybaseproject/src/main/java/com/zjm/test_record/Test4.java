package com.zjm.test_record;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjm.util.BigDecimalUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhujianming
 */
@Slf4j
public class Test4 {
    public static void main(String[] args) {

        Integer a = 1;
        String b = "1";
        System.out.println(a.toString().equals(b));

    }

    @Test
    public void test8() {
        LocalDate now = LocalDate.now();
        log.info("000011111" + now.withDayOfYear(1));
        log.info("" + now.withDayOfMonth(1));
        log.info(""+LocalDateTime.now().withSecond(59).withNano(999999999).withMinute(37));
    }

    @Test
    public void test7() {
        int scale = 2;
        log.info("" + org.apache.commons.lang3.StringUtils.isBlank(null));
    }

    @Test
    public void test6() {
        SortedMap<Integer, String> treemap = new TreeMap<>();
        // populating tree map
        treemap.put(2, "two");
        treemap.put(1, "one");
        treemap.put(7, "six");
        treemap.put(3, "three");
        treemap.put(6, "six");
        treemap.put(5, "five");
        log.info("获取大于等于指定key的子map:" + treemap.tailMap(3));
    }

    @Test
    public void test5() {
        try {
            String str = "我是中国人，我的工作88888.88";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("info", str);


            // 生成key//返回生成指定算法密钥的KeyGenerator对象
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56);//初始化此密钥生成器,使其具有确定的密钥大小
            SecretKey secretKey = keyGenerator.generateKey();//生成一个密钥
            byte[] bs = secretKey.getEncoded();
            String encodeHexString = Hex.encodeHexString(bs);
            log.info("秘钥:" + encodeHexString);
            String jsonStr = jsonObject.toJSONString();
            encodeHexString = "5da7f76410efef58";
            String encryptionStr = encryptionStr(jsonStr, encodeHexString);
            log.info("加密结果:" + encryptionStr);

            String decrypStr = decrypStr(encryptionStr, encodeHexString);
            log.info("解密结果:" + decrypStr);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    //加密
    public String encryptionStr(String jsonStr, String encodeHexString) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(Hex.decodeHex(encodeHexString)); //实例化DES密钥规则
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES"); //实例化密钥工厂
        Key convertSecretKey = factory.generateSecret(desKeySpec); //生成密钥
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        // 加密
        cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
        byte[] result = cipher.doFinal(jsonStr.getBytes());
        return Hex.encodeHexString(result);
    }

    //解密
    public String decrypStr(String encryptionStr, String encodeHexString) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(Hex.decodeHex(encodeHexString)); //实例化DES密钥规则
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES"); //实例化密钥工厂
        Key convertSecretKey = factory.generateSecret(desKeySpec); //生成密钥
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        // 解密
        cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
        byte[] desResult = cipher.doFinal(Hex.decodeHex(encryptionStr));
        String desStr = new String(desResult);
        return desStr;
    }


    @Test
    public void test4() {
        JSONObject jsonObject = new JSONObject();
        List<Student> studentList = new ArrayList<>();
        Student student = new Student(1, "a", 10);
        studentList.add(student);
        studentList.add(new Student(2, "b", 20));
        jsonObject.put("xx", studentList);

        String aa = jsonObject.toJSONString();

        List<Student> studentList1 = JSON.parseArray(JSON.parseObject(aa).getString("xx"), Student.class);
        log.info("" + studentList1);

    }

    @Test
    public void test3() {
        Random random = new Random();
        List<Integer> dataList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            //log.info(""+random.nextInt(31));
            dataList.add(random.nextInt(31));
        }
        dataList = dataList.stream().sorted().collect(Collectors.toList());
        log.info(dataList.toString());

    }

    @Test
    public void test2() {
        List<String> list = new ArrayList<>();
        list.add("a");
        boolean aa = ObjectUtil.isEmpty(list);
        log.info(aa ? "a" : "b");

    }

    @Test
    public void test1() {
        SchoolVO v0 = new SchoolVO("v0", 1, 2);
        SchoolVO v1 = new SchoolVO("v1", 1, 2);
        SchoolVO v2 = new SchoolVO("v2", 1, 2);
        List<SchoolVO> SchoolVOList = Arrays.asList(v1, v2);
        v0.setSubSchoolVOList(SchoolVOList);

        SchoolVO v3 = new SchoolVO("v3", 1, 2);
        SchoolVO v4 = new SchoolVO("v4", 1, 2);
        SchoolVOList = Arrays.asList(v3, v4);
        v1.setSubSchoolVOList(SchoolVOList);
        SchoolVO v5 = new SchoolVO("v5", 1, 2);
        SchoolVO v6 = new SchoolVO("v6", 1, 2);
        SchoolVOList = Arrays.asList(v5, v6);
        v2.setSubSchoolVOList(SchoolVOList);
        SchoolVO v7 = new SchoolVO("v7", 1, 2);
        SchoolVO v8 = new SchoolVO("v8", 1, 2);
        SchoolVOList = Arrays.asList(v7, v8);
        v3.setSubSchoolVOList(SchoolVOList);
        SchoolVO v9 = new SchoolVO("v9", 1, 2);
        SchoolVO v10 = new SchoolVO("v10", 1, 2);
        SchoolVO v11 = new SchoolVO("v11", 1, 2);
        SchoolVO v12 = new SchoolVO("v12", 1, 2);
        SchoolVOList = Arrays.asList(v9, v10, v11, v12);
        v5.setSubSchoolVOList(SchoolVOList);
        SchoolVO v13 = new SchoolVO("v13", 1, 2);
        SchoolVO v14 = new SchoolVO("v14", 1, 2);
        SchoolVOList = Arrays.asList(v13, v14);
        v11.setSubSchoolVOList(SchoolVOList);

        //statisticslBySign(v0);//单一字段逐级累计
        statisticslByOverall(v0);//多字段同时逐级累加
        System.out.println(v0);


    }

    //单个字段逐级累加
    int statisticslBySign(SchoolVO SchoolVO) {
        List<SchoolVO> SchoolVOList = SchoolVO.getSubSchoolVOList();
        if (SchoolVOList == null) {
            return SchoolVO.getTeacherNum();
        }
        int sum = SchoolVO.getTeacherNum();
        for (SchoolVO SchoolVO1 : SchoolVOList) {
            sum = sum + statisticslBySign(SchoolVO1);
        }
        SchoolVO.setTeacherNum(sum);
        return sum;
    }

    //多字段同时逐级累加
    StatisticsVO statisticslByOverall(SchoolVO schoolVO) {
        List<SchoolVO> SchoolVOList = schoolVO.getSubSchoolVOList();
        if (SchoolVOList == null) {
            StatisticsVO statisticsVO = new StatisticsVO();
            statisticsVO.setStudentNum(schoolVO.getStudentNum() == null ? 0 : schoolVO.getStudentNum());
            statisticsVO.setTeacherNum(schoolVO.getTeacherNum() == null ? 0 : schoolVO.getTeacherNum());
            return statisticsVO;
        }
        Integer totalStudentNum = schoolVO.getStudentNum() == null ? 0 : schoolVO.getStudentNum();
        Integer totalTeacherNum = schoolVO.getTeacherNum() == null ? 0 : schoolVO.getTeacherNum();
        for (SchoolVO SchoolVO1 : SchoolVOList) {
            StatisticsVO statisticsVO = statisticslByOverall(SchoolVO1);
            totalStudentNum = totalStudentNum + statisticsVO.getStudentNum();
            totalTeacherNum = totalTeacherNum + statisticsVO.getTeacherNum();
        }
        schoolVO.setTeacherNum(totalTeacherNum);
        schoolVO.setStudentNum(totalStudentNum);
        StatisticsVO statisticsVO = new StatisticsVO();
        statisticsVO.setTeacherNum(totalTeacherNum);
        statisticsVO.setStudentNum(totalStudentNum);
        return statisticsVO;
    }

    //需要累加的属性
    @Data
    class StatisticsVO {
        private Integer teacherNum;
        private Integer studentNum;

    }
}

class SchoolVO {
    //教师人数
    private Integer teacherNum;
    //学生人数
    private Integer studentNum;
    //学校名称
    private String name;
    private List<SchoolVO> subSchoolVOList;

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public Integer getTeacherNum() {
        return teacherNum;
    }

    public void setTeacherNum(Integer teacherNum) {
        this.teacherNum = teacherNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SchoolVO> getSubSchoolVOList() {
        return subSchoolVOList;
    }

    public void setSubSchoolVOList(List<SchoolVO> subSchoolVOList) {
        this.subSchoolVOList = subSchoolVOList;
    }

    public SchoolVO(String name, Integer teacherNum, Integer studentNum) {
        this.teacherNum = teacherNum;
        this.studentNum = studentNum;
        this.name = name;
    }

    @Override
    public String toString() {
        return "SchoolVO{" +
                "teacherNum=" + teacherNum +
                ", name='" + name + '\'' +
                ", subSchoolVOList=" + subSchoolVOList +
                '}';
    }
}
