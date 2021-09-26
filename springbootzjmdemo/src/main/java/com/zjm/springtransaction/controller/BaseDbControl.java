package com.zjm.springtransaction.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
@RequestMapping("/baseDbConnect")
@RestController
@Slf4j
@Api(tags = "基础数据库连接")
public class BaseDbControl {
    /*
     * 执行sql查询语句
     */
    @PostMapping("/queryStudents/{name}")
    @ApiOperation(value = "查询学生列表", notes = "query student list")
    public Map<String, Object> executeQuery(@ApiParam(name = "name", value = "姓名",defaultValue = "张") @PathVariable("name") String name) {
        String sql=null;//eg  update stu set name=? where number=?
        Map<String, Object> mapMetaData = new HashMap<>();
        try {
            Connection  conn = BaseDbControl.getConnection();
            PreparedStatement  preparedstatement = conn.prepareStatement(sql);

            preparedstatement.setObject(0,name);

            ResultSet  rs = preparedstatement.executeQuery();
            // 获取元数据
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                //获取数据表中满足要求的一行数据，并放入Map中
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Object columnValue = rs.getObject(columnLabel);
                    mapMetaData.put(columnLabel, columnValue);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapMetaData;
    }
    /*
     * 获取数据库的连接
     */
    public static Connection getConnection() throws Exception {
        String driver ="com.mysql.cj.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://localhost:3306/test_demo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String username ="root";
        String password ="zjmcat";

        Class.forName(driver);
        Connection  conn = DriverManager.getConnection(jdbcUrl, username, password);

        return conn;
    }
}
