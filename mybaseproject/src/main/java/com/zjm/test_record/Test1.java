package com.zjm.test_record;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class Test1 {
    public static void main(String[] args) throws Exception {

        Test1 test1 = new Test1();
        test1.test28();


    }

    public void test28() {
        Department department0 = new Department("a0", 1);
        Department department1 = new Department("a1", 1);
        department1.setParentDepartment(department0);
        Department department2 = new Department("a2", 1);
        department2.setParentDepartment(department0);
        Department department3 = new Department("a3", 1);
        department3.setParentDepartment(department1);
        Department department4 = new Department("a4", 1);
        department4.setParentDepartment(department1);
        Department department5 = new Department("a5", 1);
        department5.setParentDepartment(department2);
        Department department6 = new Department("a6", 1);
        department6.setParentDepartment(department2);
        Department department7 = new Department("a7", 1);
        department7.setParentDepartment(department3);
        Department department8 = new Department("a8", 1);
        department8.setParentDepartment(department3);
        Department department9 = new Department("a9", 1);
        department9.setParentDepartment(department5);
        Department department10 = new Department("a10", 1);
        department10.setParentDepartment(department5);
        List<Department> footDepartmentList = new ArrayList<>();
        footDepartmentList.add(department7);
        footDepartmentList.add(department8);
        footDepartmentList.add(department9);
        footDepartmentList.add(department10);
        footDepartmentList.add(department4);
        footDepartmentList.add(department6);
        List<Department> allDepartmentList = new ArrayList<>();
        allDepartmentList.addAll(footDepartmentList);
        allDepartmentList.add(department0);
        allDepartmentList.add(department1);
        allDepartmentList.add(department2);
        allDepartmentList.add(department3);
        allDepartmentList.add(department5);

        // statisticsDepartmentDatas(footDepartmentList, allDepartmentList);
        statisticsDepartmentDatas2(footDepartmentList);
        System.out.println(footDepartmentList);
    }

    public void statisticsDepartmentDatas2(List<Department> footDepartmentList) {
        for (Department department : footDepartmentList) {

            levelStatistics2(department.getParentDepartment(), department.getNum(), department.getNum());
        }
    }

    public void levelStatistics2(Department parentDepartment, Integer totalNum, Integer myNum) {
        int totalNum2=totalNum;
        if (!parentDepartment.isHasLevel()) {
            totalNum2 = totalNum2 + parentDepartment.getNum();
            parentDepartment.setHasLevel(true);
        } else {
            totalNum2 = totalNum2 + myNum;
        }
        parentDepartment.setNum(totalNum2);

        if (parentDepartment.getParentDepartment() != null) {
            levelStatistics2(parentDepartment.getParentDepartment(), totalNum, myNum);
        }
    }

    public void statisticsDepartmentDatas(List<Department> footDepartmentList, List<Department> allDepartmentList) {
        for (Department department : footDepartmentList) {
            List<Department> departmentsList = footDepartmentList.stream().filter(s -> s.getParentDepartment().getCode().equals(department.getParentDepartment().getCode()) && department.getIsStatistics() == 0).collect(Collectors.toList());
            if (departmentsList.size() > 0) {
                int myNum = 0;
                for (Department department1 : departmentsList) {
                    myNum = myNum + (department1.getNum() == null ? 0 : department1.getNum());
                    department1.setIsStatistics(1);
                }
                levelStatistics(department.getParentDepartment(), myNum, allDepartmentList);
            }
        }
    }

    public void levelStatistics(Department parentDepartment, Integer employeeTotalNum, List<Department> allDepartmentList) {
        Integer totalNum = employeeTotalNum + parentDepartment.getNum();
        parentDepartment.setNum(totalNum);
        List<Department> departmentsList = allDepartmentList.stream().filter(s -> s.getParentDepartment() != null && parentDepartment.getParentDepartment() != null && s.getParentDepartment().getCode().equals(parentDepartment.getParentDepartment().getCode())).collect(Collectors.toList());
        int myNum = 0;
        for (Department department1 : departmentsList) {
            myNum = myNum + (department1.getNum() == null ? 0 : department1.getNum());
        }
        if (parentDepartment.getParentDepartment() != null) {
            levelStatistics(parentDepartment.getParentDepartment(), myNum, allDepartmentList);
        }
    }



    public void test26() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        System.out.println(map.size());
        map.remove("a");
        System.out.println(map.size());
    }

    public void test25() {
        int a = 1;
        int b = 10;
        if (a != 2 && b != 10) {
            System.out.println("0000000000000");
        } else {
            System.out.println("11111111111111");
        }
    }



    public void test23() {
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateTimeFormatter.format(new Date()));
    }

    public void test22() {
        JSONArray JSONArrayParent = new JSONArray();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        Department department = new Department("0010", 2);
        jsonObject.put("department", department);
        jsonArray.add(jsonObject);
        jsonObject = new JSONObject();
        department = new Department("0011", 2);
        jsonObject.put("department", department);
        jsonArray.add(jsonObject);
        JSONArrayParent.add(jsonArray);

        JSONArray jsonArray2 = new JSONArray();
        jsonObject = new JSONObject();
        department = new Department("0020", 2);
        jsonObject.put("department", department);
        jsonArray2.add(jsonObject);
        jsonObject = new JSONObject();
        department = new Department("0021", 2);
        jsonObject.put("department", department);
        jsonArray2.add(jsonObject);
        JSONArrayParent.add(jsonArray2);
        System.out.println(JSONArrayParent);
        System.out.println(JSONArrayParent.toString());

    }

    public void test21() {
        Map<String, String> budget = new HashMap<>();
        budget.put("clothes", "f33dc48c65d411eb946b7a681268f754");
        budget.put("grocery", "f33e7c5e65d411eb946b7a681268f754");
        budget.put("transportation", "f33efb1c65d411eb946b7a681268f754");
        budget.put("utility", "akpi-f334c8e465d411eb946b7a681268f754");
        budget.put("rent", "akpi-f334cd2965d411eb946b7a681268f754");
        budget.put("miscellneous", "aoffer-f3426c5265d411eb946b7a681268f754");

        System.out.println("排序前: " + budget);

        Map<String, String> sorted = budget.entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

        System.out.println("正序: " + sorted);


        sorted = budget.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        System.out.println("倒序: " + sorted);
    }

    public void test20() {
        Map<String, Integer> map = new HashMap<>();
        map.put("d", 2);
        map.put("c", 1);
        map.put("b", 4);
        map.put("a", 3);
        List<Map.Entry<String, Integer>> infoIds = new ArrayList<>(map.entrySet());
        System.out.println("排序前");
        for (int i = 0; i < infoIds.size(); i++) {
            String id = infoIds.get(i).toString();
            System.out.println(id);
        }
        System.out.println("根据value排序");
        Collections.sort(infoIds, (o1, o2) -> (o2.getValue() - o1.getValue()));
        for (int i = 0; i < infoIds.size(); i++) {
            String id = infoIds.get(i).toString();
            System.out.println(id);
        }
    }

    public void test19() {
        Department department = this.test18();
        int total = isExistChild(department, department.getNum());
        System.out.println(total);
    }

    public int isExistChild(Department department1, int e) {
        int a = e;
        if (department1.getSubDeparment() != null) {
            for (Department department2 : department1.getSubDeparment()) {
                int b = isExistChild(department2, department2.getNum());
                a = a + b;
            }
        } else {
            a = department1.getNum();
        }
        return a;
    }


    public Department test18() {
        Department department = new Department("a", 10);
        List<Department> subDepartment = new ArrayList<>();
        Department departmenta1 = new Department("a1", 10);
        Department departmenta2 = new Department("a2", 10);
        subDepartment.add(departmenta1);
        subDepartment.add(departmenta2);
        department.setSubDeparment(subDepartment);

        List<Department> subDepartment1 = new ArrayList<>();
        Department departmenta11 = new Department("a11", 10);
        Department departmenta12 = new Department("a12", 10);
        subDepartment1.add(departmenta11);
        subDepartment1.add(departmenta12);
        departmenta1.setSubDeparment(subDepartment1);

        List<Department> subDepartment2 = new ArrayList<>();
        Department departmenta111 = new Department("a111", 10);
        Department departmenta112 = new Department("a112", 10);
        subDepartment2.add(departmenta111);
        subDepartment2.add(departmenta112);
        departmenta11.setSubDeparment(subDepartment2);

        List<Department> subDepartment3 = new ArrayList<>();
        Department departmenta1121 = new Department("a1121", 10);
        Department departmenta1122 = new Department("a1121", 10);
        subDepartment3.add(departmenta1121);
        subDepartment3.add(departmenta1122);
        departmenta112.setSubDeparment(subDepartment3);


        return department;
    }

    //前一个节点
    public void test17(String str) {
        String strs = "1,2,4,5,7,8,6";
        System.out.println(strs.substring(strs.indexOf(",") + 1).substring(0, strs.indexOf(",")));
        System.out.println(strs.substring(0, strs.indexOf(",")));
        System.out.println(str);
        System.out.println(strs);
        String strs1 = strs.substring(0, strs.indexOf(str) - 1);
        System.out.println(strs1);
        String strs2 = strs1.substring(strs1.lastIndexOf(",") + 1, strs1.length());
        System.out.println(strs2);

        String[] arrays = strs.split(",");

    }

    public void test16() {
        a:
        for (int i = 0; i < 100; i++) {
            if (i == 50) {
                break a;
            }
            System.out.println("i=" + i);
        }
        System.out.println("end");
    }

    public void test15() {
        LocalDate localDate = LocalDate.of(2012, 1, 1).minusMonths(1);
        System.out.println(localDate.getYear());
        System.out.println(localDate.getMonth().getValue());
    }

    public void test14() {
        Integer year = 2018;
        Integer month = 1;


        //根据指定年月获取上一年月
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        calendar.add(Calendar.MONTH, -1);
        Integer lastYear = calendar.get(Calendar.YEAR);
        Integer lastMonth = calendar.get(Calendar.MONTH) + 1;


        System.out.println(lastYear + " " + lastMonth);

    }

    public void test13() {
        List<Class1> list = new ArrayList<Class1>();

        list.add(new Class1(12, "张三12"));
        list.add(new Class1(8, "张三8"));
        list.add(new Class1(88, "张三88"));
        list.add(new Class1(10, "张三10"));
        Collections.sort(list, Comparator.comparing(Class1::getB3));//升序排序
        System.out.println(list);
        Collections.sort(list, Comparator.comparing(Class1::getB3).reversed());//降序排序
        System.out.println(list);
        list.add(new Class1(15, "张三15"));
        list.sort((ord1, ord2) -> ord2.getB3().compareTo(ord1.getB3()));//降序排序
        System.out.println(list);
        list.add(new Class1(100, "张三100"));
        list.sort((ord1, ord2) -> ord1.getB3().compareTo(ord2.getB3()));//升序排序
        System.out.println(list);
        list = new ArrayList<Class1>();
        list.add(new Class1(3, "11", LocalDate.of(2020, 12, 1)));
        list.add(new Class1(3, "11", LocalDate.of(2020, 11, 1)));
        list.add(new Class1(3, "11", LocalDate.of(2021, 5, 1)));
        list.add(new Class1(3, "11", LocalDate.of(2021, 4, 1)));
        list.add(new Class1(3, "11", LocalDate.of(2021, 3, 1)));
        list.add(new Class1(3, "11", LocalDate.of(2021, 2, 1)));
        list.add(new Class1(3, "11", LocalDate.of(2021, 1, 1)));
        list.add(new Class1(3, "11", LocalDate.of(2020, 10, 1)));
        list.add(new Class1(3, "11", LocalDate.of(2020, 9, 1)));
        list.add(new Class1(3, "11", LocalDate.of(2020, 8, 1)));
        Collections.sort(list, Comparator.comparing(Class1::getLocalDate));//升序排序
        System.out.println(list);


    }

    public void test12() {
        Integer a1 = new Integer(100);
        Integer a2 = new Integer(100);
        Integer b1 = 100;
        Integer b2 = 100;
        System.out.println((a1 == a2) + ":" + a1.equals(a2));
        System.out.println(b1 == b2);

        Class1 class1 = new Class1(100, "张三");
        System.out.println(b1 == class1.getB3());
        System.out.println(class1.getB3() == enum1.MY_ENUM.getB4());
    }

enum enum1 {
    MY_ENUM(100);

    enum1(Integer b4) {
        this.b4 = b4;
    }

    private Integer b4;

    public Integer getB4() {
        return b4;
    }
}

class Class1 {
    private Integer b3;
    private String name;
    private LocalDate localDate;

    public Class1(Integer b3, String name) {
        this.b3 = b3;
        this.name = name;
    }

    public Class1(Integer b3, String name, LocalDate localDate) {
        this.b3 = b3;
        this.name = name;
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return "Class1{" +
                "b3=" + b3 +
                ", name='" + name + '\'' +
                ", localDate=" + localDate +
                '}';
    }

    public Integer getB3() {
        return b3;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public String getName() {
        return name;
    }
}

    public void test11() {
        String a = "name";
        String b = "";
        String c = "";
        if (a.split("_").length > 0) {
            for (int i = 0; i < a.split("_").length; i++) {
                c = a.split("_")[i];
                if (i == 0) {
                    b = b + c;
                } else {
                    b = b + c.substring(0, 1).toUpperCase() + c.substring(1);
                }
            }
        } else {
            b = a;
        }
        Field field = null;
        try {
            field = new VO().getClass().getDeclaredField(b);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        System.out.println(field.getName());
    }

class VO {
    private String nameAgeStudent;
    private String nameAge;
    private String name;

    public String getNameAgeStudent() {
        return nameAgeStudent;
    }

    public void setNameAgeStudent(String nameAgeStudent) {
        this.nameAgeStudent = nameAgeStudent;
    }

    public String getNameAge() {
        return nameAge;
    }

    public void setNameAge(String nameAge) {
        this.nameAge = nameAge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

    public void test10() {
        ConcurrentHashMap<String, Object> subDataDetailMap = new ConcurrentHashMap<>();
        subDataDetailMap.put("name", "aa");
        subDataDetailMap.put("age", 11);
        System.out.println(subDataDetailMap);
        subDataDetailMap.forEach((K, V) -> {
            System.out.println(K + ";" + V);
            if (K.equals("name")) {
                subDataDetailMap.remove(K);
                K = K + "-KPI";
                subDataDetailMap.put(K, V);
            }
        });
        System.out.println(subDataDetailMap);


        Map<String, Object> subDataDetailMap2 = new HashMap<>();
        subDataDetailMap2.put("name", "aa");
        subDataDetailMap2.put("name2", "bb");
        subDataDetailMap2.put("age", 11);
        System.out.println(subDataDetailMap2);
        List<String> list = new ArrayList<>();
        subDataDetailMap2.forEach((K, V) -> {
            System.out.println(K + ";" + V);
            if (K.equals("name")) {
                list.add(K);
            }

            if (K.equals("name2")) {
                list.add(K);
            }
        });
        System.out.println(subDataDetailMap2);
        System.out.println(list);

    }

    public void test9() {
        Integer year = 2020;
        Integer month = 4;

        for (int i = 0; i < 12; i++) {
            System.out.println(year + "-" + month);
            month = month - 1;
            if (month == 0) {
                month = 12;
                year = year - 1;
            }
        }
    }

    public void test8() {
        System.out.println(String.format("%.2f", Double.valueOf("1000.9169")));
    }

    public void test7() {
        List<MyVO> myVOList = new ArrayList<>();
        MyVO myVO = new MyVO();
        myVO.setAge(30.999);
        myVO.setName("张三");
        myVOList.add(myVO);
        myVO = new MyVO();
        myVO.setAge(899.111);
        myVO.setName("李四");
        myVOList.add(myVO);
        System.out.println(myVOList);
        myVOList.stream().map(myVO2 -> {
            myVO2.setAge(1000.22222);
            return myVO2;
        }).collect(Collectors.toList());
        System.out.println(myVOList);

        Double sum = myVOList.stream().mapToDouble(n -> Double.valueOf(String.format("%.2f", n.getAge()))).summaryStatistics().getSum();
        System.out.println(sum);
    }

class MyVO {
    String name;
    Double age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

}

    public void test6() {
        StringJoiner stringJoiner = new StringJoiner(",");
        stringJoiner.add("张三(123)").add("李四(456)");
        System.out.println(stringJoiner.toString());
    }

    public void test5() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String thisYear = sdf.format(new Date());
        System.out.println(thisYear);
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.set(Calendar.MONTH, date.get(Calendar.MONTH) - 1);
        Date lastMonthD = date.getTime();
        String lastYear = sdf.format(lastMonthD);
        System.out.println(lastYear);
    }

    //字符串格式化
    public void test4() {
        String resultCode = "部分员工薪资未申请发放，不能%s！";
        String.format(resultCode, "操作");
        System.out.println(String.format(resultCode, "操作"));
    }

    //保留两位小数
    public void test3() {
        double test1 = 10.2234;
        double test2 = 10.3356;

        Double dtest1 = Double.valueOf(String.format("%.2f", test1));
        System.out.println(dtest1);
    }

    //获取上个月
    public void test2() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String thisMonthS = "2020-2";
        Date thisMonthD = sdf.parse(thisMonthS);
        Calendar date = Calendar.getInstance();
        date.setTime(thisMonthD);
        date.set(Calendar.MONTH, date.get(Calendar.MONTH) - 1);
        Date lastMonthD = date.getTime();
        String lastMonthS = sdf.format(lastMonthD);
        System.out.println(lastMonthS + "------" + lastMonthS.substring(0, 4) + ":" + Integer.parseInt(lastMonthS.substring(5, lastMonthS.length())));

    }

    public void test1() {
        List<String> names = new ArrayList<>();
        names.add("a");
        names.add("b");
        names.add("c");
        //排除不为a的值
        List<String> names2 = names.stream().filter(name -> !name.equals("a")).collect(Collectors.toList());
        System.out.println(names2);

        List<String> names3 = new ArrayList<>();
        names3.add("aa");
        names3.add("bb");
        names3.add("cc");
        List<String> names4 = new ArrayList<>();
        names4.add("aa");
        names4.add("b");
        names4.add("cc");
        //两个集合，值相等的排除不相等的留下
        List<String> names5 = names3.stream().filter(name3 -> {
            for (String name4 : names4) {
                if (name4.equals(name3)) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
        System.out.println(names5);

        List<String> names6 = new ArrayList<>();
        names6.add("aa");
        names6.add("b");
        names6.add("cc");
    }
}

class Department {
    private String code;
    private Integer num;
    private List<Department> subDeparment;
    private Department parentDepartment;
    private int isStatistics;
    private boolean hasLevel;

    public boolean isHasLevel() {
        return hasLevel;
    }

    public void setHasLevel(boolean hasLevel) {
        this.hasLevel = hasLevel;
    }

    public int getIsStatistics() {
        return isStatistics;
    }

    public void setIsStatistics(int isStatistics) {
        this.isStatistics = isStatistics;
    }

    public Department getParentDepartment() {
        return parentDepartment;
    }

    public void setParentDepartment(Department parentDepartment) {
        this.parentDepartment = parentDepartment;
    }

    public Department(String code, Integer num) {
        this.code = code;
        this.num = num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<Department> getSubDeparment() {
        return subDeparment;
    }

    public void setSubDeparment(List<Department> subDeparment) {
        this.subDeparment = subDeparment;
    }

    @Override
    public String toString() {
        return "Department{" +
                "code='" + code + '\'' +
                ", num=" + num +
                ", subDeparment=" + subDeparment +
                ", parentDepartment=" + parentDepartment +
                ", isStatistics=" + isStatistics +
                ", hasLevel=" + hasLevel +
                '}';
    }
}


@Data
@Builder
@NoArgsConstructor
class Student implements Serializable {
    private int id;
    private String name;
    private int age;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }


}
