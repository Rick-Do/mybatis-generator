package com.dx.generator.domain;


import lombok.Data;

@Data
public class Order {

    /**
     * 排序
     */
    private Direction direction;

    /**
     * 排序字段
     */
    private String field;



}
