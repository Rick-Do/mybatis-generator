package com.dx.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dx.generator.domain.Order;
import com.dx.generator.dto.GlobalConfigDTO;
import com.dx.generator.entity.*;
import com.dx.generator.enums.ConfigTypeEnum;
import com.dx.generator.mapper.*;
import com.dx.generator.model.DataConfigModel;
import com.dx.generator.query.DataConfigQuery;
import com.dx.generator.service.DataConfigModelService;
import com.dx.generator.util.OrderUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author dongy
 * @version 1.0
 * @date 2023/3/28 16:33:45
 * @since jdk1.8_202
 */
@Service
public class DataConfigModelServiceImpl implements DataConfigModelService {

    @Resource
    private ControllerConfigMapper controllerConfigMapper;

    @Resource
    private EntityConfigMapper entityConfigMapper;

    @Resource
    private MapperConfigMapper mapperConfigMapper;

    @Resource
    private ServiceConfigMapper serviceConfigMapper;

    @Resource
    private DataConfigMapper dataConfigMapper;

    @Override
    @Transactional
    public DataConfigModel save(DataConfigModel configModel) {
        boolean isExistData = Objects.nonNull(configModel.getDataConfig());
        DataConfig dataConfig = isExistData ? configModel.getDataConfig(): new DataConfig();
        //新增全局配置信息
        DataConfigModel dataConfigModel = new DataConfigModel();
        //服务配置
        ServiceConfig serviceConfig = configModel.getServiceConfig();
        Assert.notNull(dataConfig, "保存失败，未知错误");
        if (Objects.nonNull(serviceConfig)){
            serviceConfigMapper.insert(serviceConfig);
            dataConfigModel.setServiceConfig(serviceConfig);
        }
        //控制器配置
        ControllerConfig controllerConfig = configModel.getControllerConfig();
        if (Objects.nonNull(controllerConfig)){
            controllerConfigMapper.insert(controllerConfig);
            dataConfigModel.setControllerConfig(controllerConfig);
        }
        //entity配置
        EntityConfig entityConfig = configModel.getEntityConfig();
        if (Objects.nonNull(entityConfig)){
            entityConfigMapper.insert(entityConfig);
            dataConfigModel.setEntityConfig(entityConfig);
        }
        //mapper配置
        MapperConfig mapperConfig = configModel.getMapperConfig();
        if (Objects.nonNull(mapperConfig)){
            mapperConfigMapper.insert(mapperConfig);
            dataConfigModel.setMapperConfig(mapperConfig);
        }
        if (isExistData){
            dataConfigMapper.insert(dataConfig);
            dataConfigModel.setDataConfig(dataConfig);
        }
        return dataConfigModel;

    }

    @Override
    @Transactional
    public DataConfigModel update(DataConfigModel configModel) {
        if (Objects.nonNull(configModel.getDataConfig())){
            dataConfigMapper.updateById(configModel.getDataConfig());
        }
        if (Objects.nonNull(configModel.getMapperConfig())){
            mapperConfigMapper.updateById(configModel.getMapperConfig());
        }
        if (Objects.nonNull(configModel.getControllerConfig())){
            controllerConfigMapper.updateById(configModel.getControllerConfig());
        }
        if (Objects.nonNull(configModel.getServiceConfig())){
            serviceConfigMapper.updateById(configModel.getServiceConfig());
        }
        if (Objects.nonNull(configModel.getEntityConfig())){
            entityConfigMapper.updateById(configModel.getEntityConfig());
        }
        return configModel;
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public DataConfigModel delete(Integer id, ConfigTypeEnum configType) {
        DataConfigModel dataConfigModel = selectById(id, configType);
        switch (configType){
            case SERVICE -> serviceConfigMapper.deleteById(id);
            case MAPPER -> mapperConfigMapper.deleteById(id);
            case ENTITY -> entityConfigMapper.deleteById(id);
            case CONTROLLER -> controllerConfigMapper.deleteById(id);
            case BASE, ALL -> dataConfigMapper.deleteById(id);
        }
        return dataConfigModel;
    }

    @Override
    public DataConfigModel selectById(Integer id, ConfigTypeEnum configType) {
        DataConfigModel dataConfigModel = new DataConfigModel();
        return switch (configType){

            case CONTROLLER -> dataConfigModel.setControllerConfig(controllerConfigMapper.selectById(id));
            case ENTITY -> dataConfigModel.setEntityConfig(entityConfigMapper.selectById(id));
            case SERVICE -> dataConfigModel.setServiceConfig(serviceConfigMapper.selectById(id));
            case MAPPER -> dataConfigModel.setMapperConfig(mapperConfigMapper.selectById(id));
            case BASE -> dataConfigModel.setDataConfig(dataConfigMapper.selectById(id));
            case ALL -> dataConfigModel.setDataConfig(dataConfigMapper.selectById(id))
                    .setServiceConfig(serviceConfigMapper.selectById(dataConfigModel.getDataConfig().getServiceId()))
                    .setMapperConfig(mapperConfigMapper.selectById(dataConfigModel.getDataConfig().getMapperId()))
                    .setEntityConfig(entityConfigMapper.selectById(dataConfigModel.getDataConfig().getEntityId()))
                    .setControllerConfig(controllerConfigMapper.selectById(dataConfigModel.getDataConfig().getControllerId()));
        };
    }

    @Override
    public Page<DataConfigModel> selectPage(Integer pageIndex, Integer pageSize, DataConfigQuery query) {
        Assert.notNull(query.getConfigType(), "查询类型不能为空");
        return switch (query.getConfigType()){
            case SERVICE -> toModelPage(serviceConfigMapper.
                    selectPage(generatePage(pageIndex, pageSize, query.getOrders()), tQueryWrapper(query.getNameLike())));
            case ENTITY -> toModelPage(entityConfigMapper.
                    selectPage(generatePage(pageIndex, pageSize, query.getOrders()), tQueryWrapper(query.getNameLike())));
            case MAPPER -> toModelPage(mapperConfigMapper.
                    selectPage(generatePage(pageIndex, pageSize, query.getOrders()), tQueryWrapper(query.getNameLike())));
            case CONTROLLER -> toModelPage(controllerConfigMapper.
                    selectPage(generatePage(pageIndex, pageSize, query.getOrders()), tQueryWrapper(query.getNameLike())));
            case BASE -> toModelPage(dataConfigMapper.
                    selectPage(generatePage(pageIndex, pageSize, query.getOrders()), tQueryWrapper(query.getNameLike())));
            case ALL -> toModelPage(dataConfigMapper.
                    selectPageDTO(generatePage(pageIndex, pageSize, query.getOrders()), query));
        };
    }

    @Override
    public List<DataConfigModel> selectList(DataConfigQuery query) {
        Page<DataConfigModel> modelPage = selectPage(1, Integer.MAX_VALUE, query);
        return Optional.ofNullable(modelPage).map(Page::getRecords).orElse(new ArrayList<>());
    }

    private <T> QueryWrapper<T> tQueryWrapper(String nameLike){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(nameLike)){
            queryWrapper.like("name", nameLike);
        }
        return queryWrapper;
    }

    private <T> Page<T> generatePage( Integer pageIndex, Integer pageSize, List<Order> orders){
        Page<T> page = new Page<>();
        page.setSize((long) pageSize)
                .setCurrent((long) pageIndex)
                .setOrders(OrderUtils.toOrderItems(orders));
        return page;
    }

    private  <T> Page<DataConfigModel> toModelPage(Page<T> page){
        List<DataConfigModel> configModels = page.getRecords().stream()
                .map(item -> {
                    DataConfigModel dataConfigModel = new DataConfigModel();
                    if (item instanceof ControllerConfig config) {
                        dataConfigModel.setControllerConfig(config);
                    } else if (item instanceof EntityConfig config) {
                        dataConfigModel.setEntityConfig(config);
                    } else if (item instanceof ServiceConfig config) {
                        dataConfigModel.setServiceConfig(config);
                    } else if (item instanceof MapperConfig config) {
                        dataConfigModel.setMapperConfig(config);
                    } else if (item instanceof GlobalConfigDTO dto) {
                        dataConfigModel.setGlobalConfig(dto);
                    }else if (item instanceof DataConfig config) {
                        dataConfigModel.setDataConfig(config);
                    }
                    return dataConfigModel;
                }).toList();
        Page<DataConfigModel> modelPage = new Page<>();
        BeanUtil.copyProperties(page, modelPage, CopyOptions.create().ignoreError());
        modelPage.setRecords(configModels);
        return modelPage;
    }



}
