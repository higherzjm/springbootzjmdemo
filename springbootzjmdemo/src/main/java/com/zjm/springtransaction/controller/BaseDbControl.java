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
@Api(tags = "spring����-jdbc���ݿ�����")
public class BaseDbControl {
    /*
     * ִ��sql��ѯ���
     */
    @PostMapping("/queryStudents/{name}")
    @ApiOperation(value = "�������ݿ�����->��ѯѧ���б�", notes = "query student list")
    public Map<String, Object> executeQuery(@ApiParam(name = "name", value = "����",defaultValue = "����") @PathVariable("name") String name) {
        String sql="select name,age from students_info where name=?";
        Map<String, Object> mapMetaData = new HashMap<>();
        try {

            Connection  conn = BaseDbControl.getConnection();
            PreparedStatement  pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,name);

            ResultSet  rs = pstmt.executeQuery();
            // ��ȡԪ����
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                //��ȡ���ݱ�������Ҫ���һ�����ݣ�������Map��
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
     * ִ��sql��ѯ���
     */
    @PostMapping("/updateIdentity/{id}")
    @ApiOperation(value = "�������ݿ�����->������²���")
    public String updateIdentity(@ApiParam(name = "id", value = "����",defaultValue = "1") @PathVariable("id") String id) {
        String sql="update students_info s set s.identity='������' where s.id="+id;
        Connection  conn=null;
        try {
            conn = BaseDbControl.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement  pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return "����ʧ��";
        }

        return "���³ɹ�";
    }
    /*
     * ��ȡ���ݿ������
     */
    public static Connection getConnection() throws Exception {
        String driver ="com.mysql.cj.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://localhost:3306/myproject2021?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String username ="root";
        String password ="zjmcat";

        Class.forName(driver);

        return  DriverManager.getConnection(jdbcUrl, username, password);
    }
}
