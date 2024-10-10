package com.dx.generator.util;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.Controller;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.baomidou.mybatisplus.generator.config.builder.Service;
import com.dx.generator.entity.ControllerConfig;
import com.dx.generator.entity.EntityConfig;
import com.dx.generator.entity.MapperConfig;
import com.dx.generator.entity.ServiceConfig;

import java.util.Objects;

/**
 * @author dongy
 * @since jdk1.8_202
 * @date 2023/3/30 08:36:11
 * @version 1.0
 */
public class StrategyUtil {

    private StrategyConfig.Builder builder;

    private StrategyUtil() {
    }

    public static StrategyUtil instanceBuilder(StrategyConfig.Builder builder){
        StrategyUtil strategyUtil = new StrategyUtil();
        strategyUtil.builder = builder;
        return strategyUtil;
    }

    /**
     * 添加实体类配置
     * @param entityConfig 实体类配置信息
     * @return 当前
     */
    public StrategyUtil entityStrategy(EntityConfig entityConfig){
        if (Objects.isNull(entityConfig)){
            return this;
        }
        Entity.Builder entityBuilder = builder.entityBuilder();
        if (StrUtil.isNotEmpty(entityConfig.getSuperClass())){
            entityBuilder.superClass(entityConfig.getSuperClass());
        }
        if (entityConfig.getDisableSerialVersion()){
            entityBuilder.disableSerialVersionUID();
        }
        if (entityConfig.getEnableLombok()){
            entityBuilder.enableLombok();
        }
        if (entityConfig.getEnableLombok()){
            entityBuilder.enableColumnConstant();
        }
        if (entityConfig.getEnableChainModel()){
            entityBuilder.enableChainModel();
        }
        if (Objects.nonNull(entityConfig.getNaming())){
            entityBuilder.naming(entityConfig.getNaming());
        }
        if (Objects.nonNull(entityConfig.getColumnNaming())){
            entityBuilder.columnNaming(entityConfig.getColumnNaming());
        }
        if (Objects.nonNull(entityConfig.getIdType())){
            entityBuilder.idType(entityConfig.getIdType());
        }
        return this;
    }

    /**
     * 添加控制器配置
     * @param config 控制器配置
     * @return 当前
     */
    public StrategyUtil controllerStrategy(ControllerConfig config){
        if (Objects.isNull(config)){
            return this;
        }
        Controller.Builder controllerBuilder = builder.controllerBuilder();
        if (StrUtil.isNotEmpty(config.getSuperClass())){
            controllerBuilder.superClass(config.getSuperClass());
        }
        if (config.getEnableHyphenStyle()){
            controllerBuilder.enableHyphenStyle();
        }
        if (config.getEnableRestStyle()){
            controllerBuilder.enableRestStyle();
        }
        if (StrUtil.isNotEmpty(config.getFormatFileName())){
            controllerBuilder.formatFileName(config.getFormatFileName());
        }
        return this;
    }

    /**
     * mapper配置
     * @param config 配置信息
     * @return 当前
     */
    public StrategyUtil mapperStrategy(MapperConfig config){
        if (Objects.isNull(config)){
            return this;
        }
        Mapper.Builder mapperBuilder = builder.mapperBuilder();
        if (StrUtil.isNotEmpty(config.getSuperClass())){
            mapperBuilder.superClass(config.getSuperClass());
        }
        if (StrUtil.isNotEmpty(config.getFormatXmlFileName())){
            mapperBuilder.formatXmlFileName(config.getFormatXmlFileName());
        }
        if (StrUtil.isNotEmpty(config.getFormatMapperFileName())){
            mapperBuilder.formatMapperFileName(config.getFormatMapperFileName());
        }
        if (config.getEnableMapperAnnotation()){
            mapperBuilder.mapperAnnotation(org.apache.ibatis.annotations.Mapper.class);
        }
        if (config.getEnableBaseResultMap()){
            mapperBuilder.enableBaseResultMap();
        }
        if (config.getEnableBaseColumnList()){
            mapperBuilder.enableBaseColumnList();
        }
        return this;
    }

    /**
     * service的配置
     * @param config service配置信息
     * @return 当前
     */
    public StrategyUtil serviceConfig(ServiceConfig config){
        if (Objects.isNull(config)){
            return this;
        }
        Service.Builder serviceBuilder = builder.serviceBuilder();
        if (StrUtil.isNotEmpty(config.getSuperServiceClass())){
            serviceBuilder.superServiceClass(config.getSuperServiceClass());
        }
        if (StrUtil.isNotEmpty(config.getSuperServiceImplClass())){
            serviceBuilder.superServiceImplClass(config.getSuperServiceImplClass());
        }
        if (StrUtil.isNotEmpty(config.getFormatServiceFileName())){
            serviceBuilder.formatServiceFileName(config.getFormatServiceFileName());
        }
        if (StrUtil.isNotEmpty(config.getFormatServiceImplFileName())){
            serviceBuilder.formatServiceImplFileName(config.getFormatServiceImplFileName());
        }
        return this;
    }

    public  StrategyConfig.Builder build(){
        return  this.builder;
    }
}
