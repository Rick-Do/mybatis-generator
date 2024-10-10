package com.dx.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.concurrent.TimeUnit;

/**
 * @author dongy
 * @version 1.0
 * @date 2023/4/3 08:20:38
 * @since jdk1.8_202
 */
@Data
@TableName("memory")
@Accessors(chain = true)
public class Memory {

    @TableId
    private String id;

    /**
     * 值
     */
    private String memValue;

    /**
     * 键
     */
    private String memKey;

    /**
     * 备注
     */
    private String memType;

    /**
     * 过期时间
     */
    private Long expire;

    /**
     * 过期时间类型
     */
    private TimeUnit timeUnit;

    /**
     * 保存时间
     */
    private Long saveTime;

}
