package com.dx.generator.query;

import com.dx.generator.domain.Order;
import com.dx.generator.enums.ConfigTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * @author dongy
 * @version 1.0
 * @date 2023/3/29 14:18:40
 * @since jdk1.8_202
 */
@Data
public class DataConfigQuery {

    /**
     * 名字模糊查询
     */
    private String nameLike;

    /**
     * 排序
     */
    List<Order> orders;

    /**
     * 配置类型
     */
    private ConfigTypeEnum configType;
}
