package com.dx.generator.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dx.generator.enums.ConfigTypeEnum;
import com.dx.generator.model.DataConfigModel;
import com.dx.generator.query.DataConfigQuery;

import java.util.List;

/**
 * @author dongy
 * @version 1.0
 * @date 2023/3/28 16:33:27
 * @since jdk1.8_202
 */
public interface DataConfigModelService {

    /**
     * 新增数据
     * @param configModel 要增加的数据
     * @return 数据
     */
    DataConfigModel save(DataConfigModel configModel);


    /**
     * 更新信息
     * @param configModel 配置信息
     * @return 更新后的配置信息
     */
    DataConfigModel update(DataConfigModel configModel);


    /**
     * 删除数据
     * @param id 数据主键
     * @return 已被删除的数据
     */
    DataConfigModel delete(Integer id, ConfigTypeEnum configType);

    /**
     * 根据主键查询信息
     * @param id 主键
     * @return 配置信息
     */
    DataConfigModel selectById(Integer id, ConfigTypeEnum configType);


    /**
     * 查询页面信息
     * @param pageIndex 页码
     * @param pageSize 页面大小
     * @param query 查询参数
     * @return 页面信息
     */
    Page<DataConfigModel> selectPage(Integer pageIndex, Integer pageSize, DataConfigQuery query);

    /**
     * 查询列表
     * @param query 查询参数
     * @return 列表信息
     */
    List<DataConfigModel> selectList(DataConfigQuery query);
}
