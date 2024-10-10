package com.dx.generator.util;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.dx.generator.domain.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderUtils {

    public static List<OrderItem> toOrderItems(List<Order> orders){
        if (CollUtil.isEmpty(orders)){
            return new ArrayList<>();
        }
        return orders.stream()
                .filter(Objects::nonNull)
                .filter(order -> Objects.nonNull(order.getDirection()))
                .map(order -> switch (order.getDirection()){
                    case ASC -> OrderItem.asc(order.getField());
                    case DESC -> OrderItem.desc(order.getField());
                }).toList();
    }
}
