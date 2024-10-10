package com.dx.generator.model;

import com.dx.generator.dto.GlobalConfigDTO;
import com.dx.generator.entity.*;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author dongy
 * @version 1.0
 * @date 2023/3/30 08:22:22
 * @since jdk1.8_202
 */
@Data
@Accessors(chain = true)
public class DataConfigModel {

    /**
     * 全局配置信息
     */
    private DataConfig dataConfig;

    /**
     * 控制器配置
     */
    private ControllerConfig controllerConfig;

    /**
     * entity配置
     */
    private EntityConfig entityConfig;

    /**
     * mapper配置
     */
    private MapperConfig mapperConfig;

    /**
     * 服务类配置
     */
    private ServiceConfig serviceConfig;

    /**
     * 全局配置
     */
    private GlobalConfigDTO globalConfig;


}
