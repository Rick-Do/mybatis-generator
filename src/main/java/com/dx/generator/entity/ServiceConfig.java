package com.dx.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author dongy
 * @version 1.0
 * @date 2023/3/29 17:10:37
 * @since jdk1.8_202
 */
@Data
@TableName("service_config")
public class ServiceConfig {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;


    /**
     * 父类service,全类名
     */
    private String superServiceClass;

    /**
     * 父类服务实现类，全类名
     */
    private String superServiceImplClass;

    /**
     * 格式化接口文件名称
     */
    private String formatServiceFileName;

    /**
     * 格式化实现类文件名称
     */
    private String formatServiceImplFileName;
}
