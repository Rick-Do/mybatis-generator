package com.dx.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.Data;

/**
 * @author dongy
 * @version 1.0
 * @date 2023/3/29 16:41:34
 * @since jdk1.8_202
 */
@Data
@TableName("entity_config")
public class EntityConfig {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 自定义继承的Entity类全类名
     */
    private String superClass;

    /**
     * 是否禁用自动生成的UUID
     */
    private Boolean disableSerialVersion = false;

    /**
     * 是否启用lombok模式
     */
    private Boolean enableLombok = false;

    /**
     * 是否开启生成字段常量
     */
    private Boolean enableColumnConstant = false;

    /**
     * 是否开启链式模型
     */
    private Boolean enableChainModel = false;

    /**
     * 数据库表映射到实体的命名策略
     */
    private NamingStrategy naming;

    /**
     * 数据库表字段映射到实体的命名策略
     */
    private NamingStrategy columnNaming;

    /**
     * 主键id生成类型
     */
    private IdType idType;




}
