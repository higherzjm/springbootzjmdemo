package com.zjm;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import reactor.core.Exceptions;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author zhujianming
 */
public class Test2 {
    public static void main(String[] args) throws Exception {

        Test2 test1 = new Test2();
        test1.test22(Test2.class,test1.getClass().getDeclaredMethod("test21",Integer.class,Integer.class));


    }


    public void test22(Class c, Method method) throws Exception {
        method.invoke(c.newInstance(),2021,3);

    }
    class ThreadCallable implements Callable<String> {
        private Integer year;
        private Integer month;

        public ThreadCallable(Integer year, Integer month) {
            this.year = year;
            this.month = month;
        }

        @Override
        public String call() {
            return "薪资年:"+year+";薪资月:"+month;
        }
    }
    public void test21(Integer year,Integer month){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1), new ThreadFactoryBuilder().setNameFormat("salary-calculation-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
        CompletionService<String> completionService= new ExecutorCompletionService<>(threadPoolExecutor);
        completionService.submit(new ThreadCallable(year,month));
        try {
            String callRetValue=completionService.take().get();
            System.out.println("线程池返回值:"+callRetValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void test20() {
        String content = "${year!}年${month!}月全部员工薪资被驳回烦请及时在WEB端进行确认。驳回原因：${reason!}";
        Map<String, String> map = new HashMap<>();
        map.put("year", "2021");
        map.put("month", "6");
        map.put("year", "2021");
        map.put("reason", "我要退款呗");
        try {
            StringWriter result = new StringWriter();
            Template t = new Template("default", new StringReader(content), new Configuration());
            t.process(map, result);
            String var = result.toString();
            System.out.println(var);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void test19() {
        MyDepartment department = this.test18();
        int total = isExistChild(department, department.getNum());
        department.setNum(total);
        if (department.getSubDeparment() != null) {
            aa(department.getSubDeparment());
        }

        System.out.println("汇总结果：" + total);
        System.out.println("汇总结果：" + department);
    }

    void aa(List<MyDepartment> departmentList) {
        for (MyDepartment department2 : departmentList) {
            int total = isExistChild(department2, department2.getNum());
            department2.setNum(total);
            if (department2.getSubDeparment() != null) {
                aa(department2.getSubDeparment());
            }
        }
    }


    public int isExistChild(MyDepartment department1, int e) {
        int a = e;
        if (department1.getSubDeparment() != null) {
            for (MyDepartment department2 : department1.getSubDeparment()) {
                System.out.println(department2.getCode() + ":--:" + a);
                int b = isExistChild(department2, department2.getNum());
                a = a + b;

            }
        } else {
            a = department1.getNum();
        }
        return a;
    }


    public MyDepartment test18() {
        MyDepartment department = new MyDepartment("a", 10);
        List<MyDepartment> subDepartment = new ArrayList<>();
        MyDepartment departmenta1 = new MyDepartment("a1", 10);
        MyDepartment departmenta2 = new MyDepartment("a2", 10);
        MyDepartment departmenta3 = new MyDepartment("a3", 10);
        MyDepartment departmenta4 = new MyDepartment("a4", 10);
        subDepartment.add(departmenta1);
        subDepartment.add(departmenta2);
        subDepartment.add(departmenta3);
        subDepartment.add(departmenta4);
        department.setSubDeparment(subDepartment);

        List<MyDepartment> subDepartment1 = new ArrayList<>();
        MyDepartment departmenta11 = new MyDepartment("a11", 10);
        MyDepartment departmenta12 = new MyDepartment("a12", 10);
        subDepartment1.add(departmenta11);
        subDepartment1.add(departmenta12);
        departmenta1.setSubDeparment(subDepartment1);

        List<MyDepartment> subDepartment2 = new ArrayList<>();
        MyDepartment departmenta111 = new MyDepartment("a111", 10);
        MyDepartment departmenta112 = new MyDepartment("a112", 10);
        subDepartment2.add(departmenta111);
        subDepartment2.add(departmenta112);
        departmenta11.setSubDeparment(subDepartment2);

        List<MyDepartment> subDepartment3 = new ArrayList<>();
        MyDepartment departmenta1121 = new MyDepartment("a1121", 10);
        MyDepartment departmenta1122 = new MyDepartment("a1122", 10);
        subDepartment3.add(departmenta1121);
        subDepartment3.add(departmenta1122);
        departmenta112.setSubDeparment(subDepartment3);

        List<MyDepartment> subDepartment4 = new ArrayList<>();
        MyDepartment departmenta21 = new MyDepartment("a21", 10);
        MyDepartment departmenta22 = new MyDepartment("a22", 10);
        subDepartment4.add(departmenta21);
        subDepartment4.add(departmenta22);
        departmenta2.setSubDeparment(subDepartment4);


        return department;
    }

}

class MyDepartment {
    private String code;
    private int num;
    private List<MyDepartment> subDeparment;

    public MyDepartment(String code, int num) {
        this.code = code;
        this.num = num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<MyDepartment> getSubDeparment() {
        return subDeparment;
    }

    public void setSubDeparment(List<MyDepartment> subDeparment) {
        this.subDeparment = subDeparment;
    }

    @Override
    public String toString() {
        return "MyDepartment{" +
                "code='" + code + '\'' +
                ", num=" + num +
                ", subDeparment=" + subDeparment +
                '}';
    }
}