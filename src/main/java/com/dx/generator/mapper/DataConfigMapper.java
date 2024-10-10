package com.dx.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dx.generator.dto.GlobalConfigDTO;
import com.dx.generator.entity.DataConfig;
import com.dx.generator.mapper.provider.DataConfigProvider;
import com.dx.generator.query.DataConfigQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @author dongy
 * @version 1.0
 * @date 2023/3/28 16:32:18
 * @since jdk1.8_202
 */
public interface DataConfigMapper extends BaseMapper<DataConfig> {

    @SelectProvider(type = DataConfigProvider.class, method = "selectPageDTO")
    Page<GlobalConfigDTO> selectPageDTO(Page<DataConfig> page, @Param("query") DataConfigQuery query);

}
