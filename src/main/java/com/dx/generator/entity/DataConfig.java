package com.dx.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import lombok.Data;

@Data
@TableName("data_config")
public class DataConfig {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 作者
     */
    private String author;

    /**
     * 父级包名
     */
    private String parentPackage;

    /**
     * 模块名称
     */
    private String modelPackage;

    /**
     * controller包名
     */
    private String controllerPackage;

    /**
     * service接口包名
     */
    private String servicePackage;

    /**
     * 实体类包名
     */
    private String entityPackage;

    /**
     * xml包名
     */
    private String xmlPackage;

    /**
     * mapper包名
     */
    private String mapperPackage;

    /**
     * 表前缀
     */
    private String tablePrefix;

    /**
     * 表后缀
     */
    private String tableSuffix;

    /**
     * 包含表
     */
    private String include;

    /**
     * 排除表
     */
    private String exclude;

    /**
     * 是否启用spring的文档模式
     * 默认启用
     */
    private Boolean springDoc = true;

    /**
     * 是否启用swagger的文档模式
     */
    private Boolean swaggerDoc = false;

    /**
     * 时间类型
     */
    private DateType dateType = DateType.TIME_PACK;

    /**
     * 日期格式化
     */
    private String commentDate;

    /**
     * controllerId
     */
    private Integer controllerId;

    /**
     * entityId
     */
    private Integer entityId;

    /**
     * mapperId
     */
    private Integer mapperId;

    /**
     * serviceId
     */
    private Integer serviceId;

}
