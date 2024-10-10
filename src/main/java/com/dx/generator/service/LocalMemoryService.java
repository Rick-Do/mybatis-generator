package com.dx.generator.service;

import java.util.concurrent.TimeUnit;

/**
 * @author dongy
 * @version 1.0
 * @date 2023/4/3 08:16:23
 * @since jdk1.8_202
 */
public interface LocalMemoryService {

    /**
     * 存储值
     * @param key 键
     * @param value 值
     * @param expire 过期时间
     * @param timeUnit 过期时间单位
     */

    void setValue(String key, String value, Long expire, TimeUnit timeUnit);

    /**
     * 存储值
     * @param key 键
     * @param value 值
     */
    void setValue(String key, String value);

    /**
     * 获取值
     * @param key 键
     * @return 值
     */
    String getValue(String key);
}
