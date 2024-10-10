package com.dx.generator.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.dx.generator.entity.DataConfig;
import com.dx.generator.enums.ConfigTypeEnum;
import com.dx.generator.ex.DatasourceExpireException;
import com.dx.generator.model.DataBaseModel;
import com.dx.generator.model.DataConfigModel;
import com.dx.generator.model.RedisConstant;
import com.dx.generator.model.TableModel;
import com.dx.generator.query.DataBaseQuery;
import com.dx.generator.service.DataBaseService;
import com.dx.generator.service.DataConfigModelService;
import com.dx.generator.service.LocalMemoryService;
import com.dx.generator.util.StrategyUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DataBaseServiceImpl implements DataBaseService {

    @Resource
    private LocalMemoryService memoryService;

    @Resource
    private DataConfigModelService configService;

    @Value("${base.out-dir:/data/temp/generator/}")
    private String baseOutDir;

    private static final List<String> mysqlDefaultBase = List.of(
            "information_schema","mysql","performance_schema","sys"
    );





    @Override
    public List<DataBaseModel> getDataBaseList(DataBaseQuery query) {
        List<DataBaseModel> list = new ArrayList<>();
        String url = query.getUrl();
        try ( Connection conn = DriverManager.getConnection(url, query.getUsername(), query.getPassword())) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet catalogs = metaData.getCatalogs();
            AtomicInteger sort = new AtomicInteger(0);
            while (catalogs.next()){
                String schemaName = catalogs.getString("TABLE_CAT");
                String dataId = DigestUtils.md5DigestAsHex((url + "/"  +schemaName).getBytes(StandardCharsets.UTF_8));
                if (CollUtil.contains(mysqlDefaultBase, schemaName)){
                    continue;
                }
                DataBaseModel dataBaseModel = new DataBaseModel()
                        .setSchemaName(schemaName)
                        .setDatabaseId(dataId)
                        .setExpire(1440)
                        .setSort(sort.incrementAndGet());
                list.add(dataBaseModel);
                query.setUrl(url + "/" + schemaName);
                query.setTableName(schemaName);
                memoryService.setValue(RedisConstant.AUTH_PREFIX + dataId, JSON.toJSONString(query));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("数据库链接错误，请检查参数");
        }
        return list;
    }

    @Override
    public List<TableModel> getTableList(String dataId) {
        DruidDataSource dataSource = getDataSource(dataId);
        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
            String jo = memoryService.getValue(RedisConstant.AUTH_PREFIX + dataId);
            HashMap<String, Object> hashMap = new HashMap<>();
            DataBaseQuery dataBaseQuery = JSON.parseObject(jo, DataBaseQuery.class);
            if (Objects.isNull(dataBaseQuery)){
                throw new DatasourceExpireException("数据库链接失效");
            }
            hashMap.put("tableSchema", dataBaseQuery.getTableName());
            //查询语句
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList("SELECT * FROM information_schema.TABLES WHERE TABLE_SCHEMA = :tableSchema",
                    hashMap);
            return CollUtil.isEmpty(mapList) ? new ArrayList<>():  JSON.parseArray(JSON.toJSONString(mapList), TableModel.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DruidDataSource getDataSource(String catId) {
        String json = memoryService.getValue(RedisConstant.AUTH_PREFIX + catId);
        if (Objects.isNull(json)){
            throw new DatasourceExpireException("数据库已经过期，请重新指定");
        }
        DataBaseQuery query = JSON.parseObject(json, DataBaseQuery.class);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(query.getUrl());
        dataSource.setPassword(query.getPassword());
        dataSource.setUsername(query.getUsername());
        dataSource.setConnectionErrorRetryAttempts(3);
        dataSource.setBreakAfterAcquireFailure(true);
        dataSource.setMaxWait(3000);
        return dataSource;
    }

    @Override
    public void generateCode(String catId, String tableName, Integer configId, OutputStream outputStream) throws Exception {
        DruidDataSource dataSource = getDataSource(catId);
        DataConfigModel dataConfigModel = configService.selectById(configId, ConfigTypeEnum.ALL);
        DataConfig dataConfig = dataConfigModel.getDataConfig();
        DruidPooledConnection connection = dataSource.getConnection();
        Assert.notNull(dataConfig, "未获取到模板配置信息");
        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword())
                .build();
        AutoGenerator mpg = new AutoGenerator(dataSourceConfig);
        //包配置
        PackageConfig packageConfig = new PackageConfig.Builder()
                //此处注意: parent + moduleName 为对应的包名，在这个包下，创建对应的controller
                .parent(dataConfig.getParentPackage())
                .moduleName(dataConfig.getModelPackage())
                .controller(dataConfig.getControllerPackage())
                .service(dataConfig.getServicePackage())
                .serviceImpl(dataConfig.getServicePackage() + ".impl")
                .mapper(dataConfig.getMapperPackage())
                .entity(dataConfig.getEntityPackage())
                .xml(dataConfig.getXmlPackage())
                .build();
        mpg.packageInfo(packageConfig);
        //策略配置
        if (StrUtil.isEmpty(dataConfig.getInclude())){
            dataConfig.setInclude(tableName);
        }
        mpg.strategy(strategyConfig(dataConfigModel));
        String dirId = DigestUtils.md5DigestAsHex((dataSource.getUrl() + "-" + tableName).getBytes(StandardCharsets.UTF_8)) + System.nanoTime();
        GlobalConfig globalConfig = globalConfig(dataConfig, dirId);
        mpg.global(globalConfig);
        String dirPath = baseOutDir + dirId;
        File dirFile = new File(dirPath);
        if (!dirFile.exists()){
            boolean mkdirs = dirFile.mkdirs();
        }
        mpg.execute();
        ZipUtil.zip(outputStream, Charset.defaultCharset(), false, null, new File(dirPath));
        //删除
        FileUtil.del(Paths.get(dirPath));
    }



    /**
     * 策略配置
     * @param configModel 配置参数+
     *
     *
     *
     * @return 配置实例
     */
    private StrategyConfig strategyConfig(DataConfigModel configModel){
        DataConfig dataConfig = configModel.getDataConfig();
        StrategyConfig.Builder strategyBuilder = new StrategyConfig.Builder().enableCapitalMode();
        //表前缀
        if (StrUtil.isNotEmpty(dataConfig.getTablePrefix())){
            strategyBuilder.addTablePrefix(StrUtil.split(dataConfig.getTableSuffix(), ","));
        }

        //表后缀
        if (StrUtil.isNotEmpty(dataConfig.getTableSuffix())){
            strategyBuilder.addTableSuffix(StrUtil.split(dataConfig.getTableSuffix(), ","));
        }
        //包括表
        if (StrUtil.isNotEmpty(dataConfig.getInclude())){
            strategyBuilder.addInclude(StrUtil.split(dataConfig.getInclude(), ","));
        }
        //排除表
        if (StrUtil.isNotEmpty(dataConfig.getExclude())){
            strategyBuilder.addExclude(StrUtil.split(dataConfig.getExclude(), ","));
        }
        return StrategyUtil.instanceBuilder(strategyBuilder)
                .serviceConfig(configModel.getServiceConfig())
                .controllerStrategy(configModel.getControllerConfig())
                .entityStrategy(configModel.getEntityConfig())
                .mapperStrategy(configModel.getMapperConfig())
                .build().build();

    }

    private GlobalConfig globalConfig(DataConfig configModel, String dirId){
        GlobalConfig.Builder builder = new GlobalConfig.Builder()
                .author(configModel.getAuthor())
                .disableOpenDir()
                .commentDate(configModel.getCommentDate())
                .outputDir(baseOutDir + dirId)
                .dateType(DateType.TIME_PACK);
        if (configModel.getSpringDoc()){
            builder.enableSpringdoc();
        }
        if (configModel.getSwaggerDoc()){
            builder.enableSwagger();
        }
        return builder.build();
    }
}
