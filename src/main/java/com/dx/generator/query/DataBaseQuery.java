package com.dx.generator.query;


import lombok.Data;

@Data
public class DataBaseQuery {

    /**
     * 数据实例地址
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 表名
     */
    private String tableName;
}
