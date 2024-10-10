package com.dx.generator.model;


import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class TableModel {

    /**
     * 表名
     */
    @JSONField(name = "TABLE_NAME")
    private String tableName;

    /**
     * 表类型
     */
    @JSONField(name = "TABLE_TYPE")
    private String tableType;

    /**
     * 引擎
     */
    @JSONField(name = "ENGINE")
    private String engine;

    /**
     * 版本号
     */
    @JSONField(name = "VERSION")
    private String version;

    @JSONField(name = "ROW_FORMAT")
    private String rowFormat;

    /**
     * 表的行数
     */
    @JSONField(name = "TABLE_ROWS")
    private String tableRows;

    /**
     * 是否自增 1 是，0或null不是
     */
    @JSONField(name = "AUTO_INCREMENT")
    private Integer autoIncrement;

    /**
     * 表创建时间
     */
    @JSONField(name = "CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 表更新时间
     */
    @JSONField(name = "UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 表字符集排序规则
     */
    @JSONField(name = "TABLE_COLLATION")
    private String tableCollation;

    /**
     * 表注释
     */
    @JSONField(name = "TABLE_COMMENT")
    private String tableComment;
}
