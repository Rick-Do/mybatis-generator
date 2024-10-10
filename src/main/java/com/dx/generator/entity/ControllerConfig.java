package com.dx.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author dongy
 * @version 1.0
 * @date 2023/3/29 17:14:43
 * @since jdk1.8_202
 */
@Data
@TableName("controller_config")
public class ControllerConfig {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;


    /**
     * 父类名称（全类名）
     */
    private String superClass;

    /**
     * 开启驼峰转连字符
     */
    private Boolean enableHyphenStyle = false;

    /**
     * 开启生成@RestController控制器
     */
    private Boolean enableRestStyle = false;

    /**
     * 格式化文件名称
     */
    private String formatFileName;


}
