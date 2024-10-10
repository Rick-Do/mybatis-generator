package com.dx.generator.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DataBaseModel {

    /**
     * 数据库名
     */
    private String schemaName;

    /**
     * 数据库id
     */
    private String databaseId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 过期时间
     */
    private Integer expire;

}
