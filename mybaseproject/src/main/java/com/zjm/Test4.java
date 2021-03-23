package com.zjm;


import java.util.Arrays;
import java.util.List;

/**
 * @author zhujianming
 */
public class Test4 {
    public static void main(String[] args){

        Test4 test4 = new Test4();
        //从父几点递归子节点逐级汇总
        test4.test1();

    }

    public void test1() {
        VO v0 = new VO("v0", 1);
        VO v1 = new VO("v1", 1);
        VO v2 = new VO("v2", 1);
        List<VO> voList = Arrays.asList(v1, v2);
        v0.setVoList(voList);

        VO v3 = new VO("v3", 1);
        VO v4 = new VO("v4", 1);
        voList = Arrays.asList(v3, v4);
        v1.setVoList(voList);
        VO v5 = new VO("v5", 1);
        VO v6 = new VO("v6", 1);
        voList = Arrays.asList(v5, v6);
        v2.setVoList(voList);
        VO v7 = new VO("v7", 1);
        VO v8 = new VO("v8", 1);
        voList = Arrays.asList(v7, v8);
        v3.setVoList(voList);
        VO v9 = new VO("v9", 1);
        VO v10 = new VO("v10", 1);
        voList = Arrays.asList(v9, v10);
        v5.setVoList(voList);
        total(v0);
        System.out.println(v0);


    }

    int total(VO vo) {
        List<VO> voList = vo.getVoList();
        if (voList == null) {
            return vo.getNum();
        }
        int sum = vo.getNum();
        for (VO vo1 : voList) {
            sum = sum + total(vo1);
        }
        vo.setNum(sum);
        return sum;
    }
}

class VO {
    Integer num;
    String name;
    List<VO> voList;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VO> getVoList() {
        return voList;
    }

    public VO(String name, Integer num) {
        this.num = num;
        this.name = name;
    }

    public void setVoList(List<VO> voList) {
        this.voList = voList;
    }
}
