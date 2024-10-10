package com.dx.generator.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.dx.generator.model.DataBaseModel;
import com.dx.generator.model.TableModel;
import com.dx.generator.query.DataBaseQuery;

import java.io.OutputStream;
import java.util.List;

public interface DataBaseService {

    /**
     * 获取数据实例中的表
     * @param query 数据链接参数
     * @return 数据库名称
     */
    List<DataBaseModel> getDataBaseList(DataBaseQuery query);

    /***
     * 获取数据库中的表
     * @param dataId 数据库链接
     * @return 表列表
     */
    List<TableModel> getTableList(String dataId);

    /**
     * 获取数据库链接
     * @param catId 主键
     * @return 数据库链接
     */
    DruidDataSource getDataSource(String catId);

    /**
     * 代码生成
     * @param catId 数据库id
     * @param tableName 数据表名称
     * @param configId 配置信息
     * @param outputStream 输出流
     * @throws Exception 异常
     */
    void generateCode(String catId, String tableName, Integer configId, OutputStream outputStream) throws Exception;
}
