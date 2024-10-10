package com.dx.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author dongy
 * @version 1.0
 * @date 2023/3/29 17:17:33
 * @since jdk1.8_202
 */
@Data
@TableName("mapper_config")
public class MapperConfig {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;


    /**
     * 父类名称
     */
    private String superClass;

    /**
     * 开启 @Mapper 注解
     */
    private Boolean enableMapperAnnotation = true;

    /**
     * baseResultMap
     */
    private Boolean enableBaseResultMap = false;

    /**
     * 开启baseColumnList
     */
    private Boolean enableBaseColumnList = false;

    /**
     * 格式化Mapper文件名称
     */
    private String formatMapperFileName;

    /**
     * 格式化Xml文件名称
     */
    private String formatXmlFileName;
}
