package com.dx.generator.dto;

import com.dx.generator.entity.DataConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GlobalConfigDTO extends DataConfig {

    /**
     * 服务 名称
     */
    private String serviceConfigName;

    /**
     * 控制器配置名称
     */
    private String controllerConfigName;

    /**
     * 实体类配置名称
     */
    private String entityConfigName;

    /**
     * mapper配置名称
     */
    private String mapperConfigName;
}
