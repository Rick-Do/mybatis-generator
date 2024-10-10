package com.dx.test;

import com.alibaba.fastjson2.JSON;

import java.sql.SQLException;

public class DataTest {
    public static void main(String[] args) throws SQLException {
        /*Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.150:3306/moltom_item", "root", "123456");
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet tables = metaData.getTables(conn.getCatalog(), "%", "%", new String[]{"TABLE"});
        while (tables.next()){
            System.out.println(tables.getString(3));
        }*/
        System.out.println(JSON.parseObject("StringTest", String.class));
    }
}
