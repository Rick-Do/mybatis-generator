package com.dx.generator.mapper.provider;


import cn.hutool.core.util.StrUtil;
import com.dx.generator.query.DataConfigQuery;
import org.apache.ibatis.annotations.Param;

public class DataConfigProvider {

    public String selectPageDTO(@Param("query") DataConfigQuery query){
        StringBuilder queryStr = new StringBuilder();
        queryStr.append(" SELECT d.*, ")
                .append(" c.`name` AS controller_config_name,")
                .append(" e.`name` AS entity_config_name,")
                .append(" m.`name` AS mapper_config_name,")
                .append(" s.`name` AS service_config_name")
                .append(" FROM data_config d ")
                .append(" LEFT JOIN controller_config c ON d.controller_id = c.id")
                .append(" LEFT JOIN entity_config e ON d.entity_id = e.id")
                .append(" LEFT JOIN mapper_config m ON d.mapper_id = m.id")
                .append(" LEFT JOIN service_config s ON d.service_id = s.id")
                .append(" WHERE d.`name` IS NOT NULL ");
        if (StrUtil.isNotEmpty(query.getNameLike())){
            queryStr.append(" AND d.`name` LIKE #{query.nameLike}");
            query.setNameLike("%" + query.getNameLike() + "%");
        }
        return queryStr.toString();
    }
}
